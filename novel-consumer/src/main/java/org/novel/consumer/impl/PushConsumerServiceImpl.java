package org.novel.consumer.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.novel.consumer.facade.PushConsumerService;
import org.novel.store.facade.StoreToHadoopService;
import org.novel.store.vo.StoreResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class PushConsumerServiceImpl implements PushConsumerService {

	private Logger logger = LoggerFactory
			.getLogger(PushConsumerServiceImpl.class);

	@Autowired(required = false)
	private StoreToHadoopService storeToHadoopService;

	@Override
	public void consumer() {

		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("novelConsumerGroup");

		consumer.setNamesrvAddr("192.168.0.93:9876");

		try {
			consumer.subscribe("crawlNovel", "content");

			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

			consumer.registerMessageListener(// 注册
			new MessageListenerConcurrently() {// 匿名内部类，并发的消息处理器
				public ConsumeConcurrentlyStatus consumeMessage(
						// 处理器
						List<MessageExt> list,
						ConsumeConcurrentlyContext context) {
					/*try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}*/

					Message msg = list.get(0);

					String keys = msg.getKeys();
					String value = new String(msg.getBody());

					logger.info("获取到的消息->{}", keys + "--" + value);

					try {
						store(keys, value);
					} catch (Exception e) {
						logger.error("hadoop保存数据异常", e.getMessage());
					}

					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

				}
			}

			);
			consumer.start();
		} catch (MQClientException e) {
			logger.error("mq异常", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.error("hadoop保存数据异常", e.getMessage());
		}

	}

	/**
	 * 将小说内容保存到hadoop(hdfs)
	 * 
	 * @param fileName
	 *            小说ID
	 * @param content
	 *            小说内容
	 * @throws Exception
	 */
	public void store(String fileName, String content) throws Exception {
		StoreResult storeResult = storeToHadoopService.save(fileName, content);
		logger.info("消息保存结果->{}", storeResult.isStatus());
		if (!storeResult.isStatus()) {
			logger.error("System error,file can`t store!  fileName->{}", fileName);
		}
	}

}

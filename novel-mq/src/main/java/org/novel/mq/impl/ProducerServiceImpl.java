package org.novel.mq.impl;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.novel.mq.connet.Producer;
import org.novel.mq.facade.ProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerServiceImpl implements ProducerService {

	private static Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);
	
	private static final String ADDRESS = "192.168.0.93:9876";
	
	private static final String PRODUCER_GROUP = "novelProducer";

	@Override
	public SendStatus produce(String keys, String content) throws Exception {

		try {
			
			DefaultMQProducer producer = Producer.getInstance(ADDRESS, PRODUCER_GROUP);

			Message msg = new Message("crawlNovel", "content", keys, content.getBytes());

			SendResult result = producer.send(msg);

			SendStatus status = result.getSendStatus();

			logger.info("消息发送结果->{}", status);

			return status;

		} catch (Exception e) {
			logger.info("消息->{}发送异常", keys);
			logger.error("消息" + keys + "发生异常，异常信息->{}", e.getMessage());
			throw e;
		}
	}
	

}

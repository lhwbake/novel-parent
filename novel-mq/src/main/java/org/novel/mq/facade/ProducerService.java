package org.novel.mq.facade;

import org.apache.rocketmq.client.producer.SendStatus;

public interface ProducerService {

	public abstract SendStatus produce(String keys, String content) throws Exception; 
}

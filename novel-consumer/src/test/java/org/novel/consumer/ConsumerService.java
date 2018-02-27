package org.novel.consumer;

import org.novel.consumer.facade.PushConsumerService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConsumerService {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				"/spring/spring.xml");
		
		PushConsumerService consumer = ac.getBean(PushConsumerService.class);
		consumer.consumer();
		
		synchronized (ConsumerService.class) {
			while (true) {
				try {
					ConsumerService.class.wait();
				} catch (InterruptedException e) {
					ac.close();
				}
			}
		}
	}

}

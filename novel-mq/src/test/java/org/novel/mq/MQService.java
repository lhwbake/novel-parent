package org.novel.mq;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MQService {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				"/spring/spring.xml");
		
		synchronized (MQService.class) {
			while (true) {
				try {
					MQService.class.wait();
				} catch (InterruptedException e) {
					ac.close();
				}
			}
		}
	}

}

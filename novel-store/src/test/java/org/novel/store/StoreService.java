package org.novel.store;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StoreService {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				"/spring/spring.xml");
		
		synchronized (StoreService.class) {
			while (true) {
				try {
					StoreService.class.wait();
				} catch (InterruptedException e) {
					ac.close();
				}
			}
		}
	}
}

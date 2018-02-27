package org.novel.common;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CommonService {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				"/spring/spring.xml");		
		synchronized (CommonService.class) {
			while (true) {
				try {
					CommonService.class.wait();
				} catch (InterruptedException e) {
					ac.close();
				}
			}
		}
	}
}

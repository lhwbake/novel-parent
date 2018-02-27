package org.novel.update;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UpdateService {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				"/spring/spring.xml");
		synchronized (UpdateService.class) {
			while (true) {
				try {
					UpdateService.class.wait();
				} catch (InterruptedException e) {
					ac.close();
				}
			}
		}
	}
}

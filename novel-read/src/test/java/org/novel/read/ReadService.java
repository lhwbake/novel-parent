package org.novel.read;

import org.novel.read.facade.ReadPageService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ReadService {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				"/spring/spring.xml");
		ReadPageService readPageService = ac.getBean(ReadPageService.class);
		readPageService.run();
		synchronized (ReadService.class) {
			while (true) {
				try {
					ReadService.class.wait();
				} catch (InterruptedException e) {
					ac.close();
				}
			}
		}
	}
}

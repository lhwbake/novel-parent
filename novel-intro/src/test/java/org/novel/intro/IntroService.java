package org.novel.intro;

import org.novel.intro.facade.IntroPageService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class IntroService {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				"/spring/spring.xml");
		IntroPageService introPageService = ac.getBean(IntroPageService.class);
		introPageService.run();
		synchronized (IntroService.class) {
			while (true) {
				try {
					IntroService.class.wait();
				} catch (InterruptedException e) {
					ac.close();
				}
			}
		}
	}
}

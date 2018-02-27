package org.novel.crawl;

import org.novel.crawl.facade.CrawlStartService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CrawlService {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext(
				"/spring/spring.xml");
		CrawlStartService crawlStartService = ac.getBean(CrawlStartService.class);
		crawlStartService.startCrawlList();
		synchronized (CrawlService.class) {
			while (true) {
				try {
					CrawlService.class.wait();
				} catch (InterruptedException e) {
					ac.close();
				}
			}
		}
	}
}

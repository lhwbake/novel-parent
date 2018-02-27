 /**  
 *@Description:     
 */ 
package org.novel.crawl.impl;  

import java.util.List;

import org.novel.common.enumeration.NovelStatus;
import org.novel.crawl.db.CrawlDB;
import org.novel.crawl.facade.CrawlStartService;
import org.novel.update.facade.UpdateListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
  
public class CrawlStartServiceImpl implements CrawlStartService {
	
	Logger logger = LoggerFactory.getLogger(CrawlStartServiceImpl.class);
	
	@Autowired(required=false)
	private UpdateListService updateListService;
	
	@Override
	public void startCrawlList() {
				
		CrawlDB db = new CrawlDB();
		
		List<String> urlList = db.getCrawlUrlList(NovelStatus.UNDO.getCode());		
		String[] urls = new String[urlList.size()];
		updateListService.update(urlList.toArray(urls));
		
		
	}

}

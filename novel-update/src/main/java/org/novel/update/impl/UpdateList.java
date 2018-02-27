 /**  
 *@Description:     
 */ 
package org.novel.update.impl;  

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.novel.common.CrawlListPageBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  
public class UpdateList extends CrawlListPageBase{
	
	Logger logger = LoggerFactory.getLogger(UpdateList.class);
	
	//请求头信息
	private static HashMap<String, String> params;
	
	static {
		params = new HashMap<String, String>();
		params.put("Referer", "http://book.zongheng.com");
		params.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36");
		params.put("Host", "book.zongheng.com");
	}

	public UpdateList(String pageUrl) throws Exception {
		super(pageUrl, "utf-8", params);  
	}


	@Override
	public String getUrlRegexStr() {
		return "<a class=\"fs14\" href=\"(.*?)\"";
	}

	@Override
	public int getUrlRegexStrNum() {
		return 1;
	}
	
	/**
	 * @param exceptOther 是否需要排除纵横以外的网址
	 * @return
	 * @Author:lulei  
	 * @Description:
	 */
	public List<String> getPageUrl(boolean exceptOther) {
		
		List<String> urls = getPageUrl();
		if (exceptOther) {
			List<String> excepUrls = new ArrayList<String>();
		
			for (String url : urls) {											
				//url中是否包含"zongheng"
				if (url.indexOf("zongheng") > 0) {
					excepUrls.add(url);
				}
			}
			return excepUrls;
		}
		return urls;
	}
}

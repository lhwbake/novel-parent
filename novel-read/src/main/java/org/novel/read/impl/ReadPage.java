 /**  
 *@Description:     
 */ 
package org.novel.read.impl;  

import java.util.HashMap;

import org.novel.common.CrawlBase;
import org.novel.common.utils.RegexUtil;
import org.novel.read.entity.NovelReadModel;
  
public class ReadPage extends CrawlBase{
	
	//章节标题正则
	private static final String TITLE = "chapterName=\"(.*?)\"";
	
	//章节字数正则
	private static final String WORDCOUNT = "itemprop=\"wordCount\">(\\d*?)</span>";
	
	//章节正文正则
	//private static final String CONTENT = "<div id=\"chapterContent\" class=\"content\" itemprop=\"acticleBody\">(.*?)</div>";
	//private static final String CONTENT = "<div id=\"readerFt\" class=\"\" itemprop=\"acticleBody\">(.*?)</div>";
	private static final String CONTENT = "<div id=\"readerFs\" class=\"\">(.*?)</div>";
	
	private String url;
	
	//请求头信息
	private static HashMap<String, String> params;
	
	static {
		params = new HashMap<String, String>();
		params.put("Referer", "http://book.zongheng.com");
		params.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36");
		params.put("Host", "book.zongheng.com");
	}
	
	public ReadPage(String url) throws Exception {
		boolean result = false;
		do{
			result = readPageByGet(url, params, "utf-8");
		}while(!result);
		
		this.url = url;
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 返回小说正阅读页信息
	 */
	public NovelReadModel analyzer() {
		NovelReadModel bean = new NovelReadModel();
		bean.setUrl(this.url);
		bean.setTitle(getTitle());
		bean.setWordCount(getWordCount());
		bean.setContent(getContent());
	
		return bean;
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 获取章节标题
	 */
	private String getTitle() {
		return RegexUtil.getFirstString(getPageSourceCode(), TITLE, 1);
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 获取章节字数
	 */
	private int getWordCount() {
		String wordCountStr = RegexUtil.getFirstString(getPageSourceCode(), WORDCOUNT, 1);
		if(wordCountStr==" " || wordCountStr==""){
			return 0;
		}
		return Integer.parseInt(wordCountStr);
	}
	
	/**
	 * @return
	 * @Author:lulei  
	 * @Description: 获取章节正文
	 */
	private String getContent() {
		String content =  RegexUtil.getFirstString(getPageSourceCode(), CONTENT, 1);
		int index = content.indexOf("<p>");//去除内容中的js
		content = content.substring(index);
		return content;
	}
	
}

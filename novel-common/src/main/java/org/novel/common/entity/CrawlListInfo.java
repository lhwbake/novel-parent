 /**  
 *@Description:爬虫程序入口地址信息：更新列表页信息    
 */ 
package org.novel.common.entity;  

import java.io.Serializable;
  
public class CrawlListInfo implements Serializable {
	
	private static final long serialVersionUID = 1717788654074620644L;

	//url信息
	private String url;
	
	//入口描述
	private String info;
	
	//抓新频率
	private int frequency;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

}

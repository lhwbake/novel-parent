 /**  
 *@Description: 小说阅读页信息
 */ 
package org.novel.read.entity;  

import java.io.Serializable;
  
public class NovelReadModel implements Serializable {
	
	private static final long serialVersionUID = 2084837351071153632L;

	private String id;
	
	private String url;
	
	private String title;
	
	private int wordCount;
	
	private int chapterId;
	
	private long chapterTime;
	
	//小说正文
	private String content;
	
	private String storeType;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	public int getChapterId() {
		return chapterId;
	}
	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}
	public long getChapterTime() {
		return chapterTime;
	}
	public void setChapterTime(long chapterTime) {
		this.chapterTime = chapterTime;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	public String getStoreType() {
		return storeType;
	}
	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}
	@Override
	public String toString() {
		return "NovelReadModel [id=" + id + ", url=" + url + ", title=" + title
				+ ", wordCount=" + wordCount + ", chapterId=" + chapterId
				+ ", chapterTime=" + chapterTime + ", content=" + content + ", storeType=" + storeType + "]";
	}
		
}

 /**  
 *@Description:小说章节信息  
 */ 
package org.novel.common.entity;  

import java.io.Serializable;
  
public class NovelChapterModel implements Serializable{
	
	private static final long serialVersionUID = 627706869270526679L;

	private String id;
	
	private String url;
	
	private String title;
	
	private int wordCount;
	
	private int chapterId;
	
	private long chapterTime;
	
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
	@Override
	public String toString() {
		return "NovelChapterModel [id=" + id + ", url=" + url + ", title="
				+ title + ", wordCount=" + wordCount + ", chapterId="
				+ chapterId + ", chapterTime=" + chapterTime + "]";
	}
	
	
}

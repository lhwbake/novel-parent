 /**  
 *@Description: 小说简介信息
 */ 
package org.novel.common.entity;  

import java.io.Serializable;
  
  
public class NovelInfoModel implements Serializable{
	
	private static final long serialVersionUID = -6866107492634884099L;

	private String id;
	
	private String url;
	
	//小说名称
	private String name;
	
	//作者
	private String author;
	
	//描述
	private String desc;
	
	//小说分类
	private String type;
	
	//最新章节
	private String lastChapter;
	
	//章节列表页URL
	private String chapterListUrl;
	
	//小说字数
	private int wordCount;

	//小说章节个数
	private int chapterCount;
	
	//小说关键字
	private String keyWords;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLastChapter() {
		return lastChapter;
	}
	public void setLastChapter(String lastChapter) {
		this.lastChapter = lastChapter;
	}
	public String getChapterListUrl() {
		return chapterListUrl;
	}
	public void setChapterListUrl(String chapterListUrl) {
		this.chapterListUrl = chapterListUrl;
	}
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	public int getChapterCount() {
		return chapterCount;
	}
	public void setChapterCount(int chapterCount) {
		this.chapterCount = chapterCount;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	@Override
	public String toString() {
		return "NovelInfoModel [id=" + id + ", url=" + url + ", name=" + name
				+ ", author=" + author + ", desc=" + desc + ", type=" + type
				+ ", lastChapter=" + lastChapter + ", chapterListUrl="
				+ chapterListUrl + ", wordCount=" + wordCount
				+ ", chapterCount=" + chapterCount + ", keyWords=" + keyWords
				+ "]";
	}
	
	
}

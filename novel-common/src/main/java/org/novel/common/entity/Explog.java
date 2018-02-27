package org.novel.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录错误的信息
 * @author hadoop
 *
 */
public class Explog implements Serializable {
	
	private static final long serialVersionUID = -1251931699799709825L;

	private String id;
	
	private String model;
	
	private String type;
	
	private String content;
	
	private int level;
	
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}

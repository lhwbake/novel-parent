package org.novel.common.enumeration;

public enum NovelStatus {
	
	/**
	 * 未作处理
	 */
	UNDO("1"),
	
	/**
	 * 已做处理
	 */
	DONE("0"),
	
	/**
	 * 异常
	 */
	ERR("2");

	private String code;
	
	private NovelStatus(String code){
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}

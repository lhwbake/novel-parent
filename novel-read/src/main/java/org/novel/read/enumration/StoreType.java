package org.novel.read.enumration;

public enum StoreType {

	HADOOP("1"),DB("2"),MONGODB("3");
	
	private String code;
	
	private StoreType(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}

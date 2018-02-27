package org.novel.store.vo;

public class StoreResult {

	private boolean status;
	
	private String msg;
	
	public StoreResult(boolean status, String msg) {
		this.status = status;
		this.msg = msg;
	}
	
	public StoreResult(boolean status) {
		this.status = status;
	}
	
	public StoreResult() {}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}

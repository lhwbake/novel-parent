package org.novel.mq.vo;

import java.io.Serializable;

public class ResultConsumer implements Serializable{

	private static final long serialVersionUID = -3837037274242464980L;

	private boolean status;
	
	private String keys;
	
	private String value;
	
	public static ResultConsumer getInstance(boolean status, String keys, String value){
		return new ResultConsumer(status, keys, value);
	}

	public ResultConsumer(boolean status, String keys, String value) {
		this.status = status;
		this.keys = keys;
		this.value = value;
	}

	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keys == null) ? 0 : keys.hashCode());
		result = prime * result + (status ? 1231 : 1237);
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResultConsumer other = (ResultConsumer) obj;
		if (keys == null) {
			if (other.keys != null)
				return false;
		} else if (!keys.equals(other.keys))
			return false;
		if (status != other.status)
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	
	
	
}

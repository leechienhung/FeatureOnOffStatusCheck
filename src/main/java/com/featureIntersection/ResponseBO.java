package com.featureIntersection;

import lombok.Data;

@Data
public class ResponseBO {
	boolean success;
	String message;
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
}

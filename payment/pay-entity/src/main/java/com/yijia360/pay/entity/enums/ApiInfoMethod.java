package com.yijia360.pay.entity.enums;

public enum ApiInfoMethod {
	
	get(0,"get"),
	post(1,"post");

	private Integer code;
	private String Message;
	private ApiInfoMethod(Integer code, String message) {
		this.code = code;
		Message = message;
	}
	public Integer getCode() {
		return code;
	}
	public String getMessage() {
		return Message;
	}
	
}

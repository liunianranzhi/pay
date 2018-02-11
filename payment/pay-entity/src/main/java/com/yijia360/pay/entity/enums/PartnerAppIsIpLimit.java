package com.yijia360.pay.entity.enums;

public enum PartnerAppIsIpLimit {

	is(1,"是"),
	no(0,"否");
	
	private Integer code;
	private String Message;
	private PartnerAppIsIpLimit(Integer code, String message) {
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

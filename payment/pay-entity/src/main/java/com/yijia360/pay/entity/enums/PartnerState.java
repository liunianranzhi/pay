package com.yijia360.pay.entity.enums;

public enum PartnerState {
	
	disable(0,"禁用"),
	Enable(1,"启用");

	private Integer code;
	private String Message;
	private PartnerState(Integer code, String message) {
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

package com.yijia360.pay.entity.enums;

public enum OrderNotifyStatus {

	wait(0,"待回调"),
	success(1,"回调成功");
	
	private Integer code;
	private String Message;
	private OrderNotifyStatus(Integer code, String message) {
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

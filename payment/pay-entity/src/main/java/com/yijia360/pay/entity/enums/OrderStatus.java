package com.yijia360.pay.entity.enums;

public enum OrderStatus {
	
	payFail(-1,"支付失败"),
	inPay(0,"待支付"),
	onDuring(1,"处理中"),
	paySuccess(2,"支付成功");
	
	private Integer code;
	private String Message;
	private OrderStatus(Integer code, String message) {
		this.code = code;
		Message = message;
	}
	public Integer getCode() {
		return code;
	}
	public String getMessage() {
		return Message;
	}
	
	public static OrderStatus getInfo(Integer key){
		OrderStatus[] array = OrderStatus.values();
		for(OrderStatus pm:array){
			if(pm.getCode().equals(key)){
				return pm;
			}
		}
		return null;
	}
	
}

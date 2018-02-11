package com.yijia360.pay.entity.vo.order;

public class OrderLogVo {
	
	private Long orderId;
	private String opMsg;
	private String opName;
	private String cTime;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getOpMsg() {
		return opMsg;
	}
	public void setOpMsg(String opMsg) {
		this.opMsg = opMsg;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getcTime() {
		return cTime;
	}
	public void setcTime(String cTime) {
		this.cTime = cTime;
	}
	
}

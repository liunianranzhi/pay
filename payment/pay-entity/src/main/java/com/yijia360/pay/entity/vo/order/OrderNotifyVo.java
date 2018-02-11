package com.yijia360.pay.entity.vo.order;

public class OrderNotifyVo {

	private Long orderId;
	private Integer status;
	private Long notifyTime;
	private Integer notifyCount;
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getNotifyTime() {
		return notifyTime;
	}
	public void setNotifyTime(Long notifyTime) {
		this.notifyTime = notifyTime;
	}
	public Integer getNotifyCount() {
		return notifyCount;
	}
	public void setNotifyCount(Integer notifyCount) {
		this.notifyCount = notifyCount;
	}
	
}

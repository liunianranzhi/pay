package com.yijia360.pay.entity.po.order;

import org.springframework.stereotype.Component;

import com.yijia360.pay.entity.po.BaseEntity;

@Component
public class OrderNotify extends BaseEntity{
	
	private Long orderId;
	private Integer status;
	private String notifyTime;
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
	public String getNotifyTime() {
		return notifyTime;
	}
	public void setNotifyTime(String notifyTime) {
		this.notifyTime = notifyTime;
	}
	public Integer getNotifyCount() {
		return notifyCount;
	}
	public void setNotifyCount(Integer notifyCount) {
		this.notifyCount = notifyCount;
	}
	
}

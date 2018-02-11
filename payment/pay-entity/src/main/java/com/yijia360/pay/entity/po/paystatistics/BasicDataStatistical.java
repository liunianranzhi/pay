package com.yijia360.pay.entity.po.paystatistics;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class BasicDataStatistical {

	private Long id ;
	
	private Integer payMode ;
	
	private BigDecimal payTotalPrice ;
	
	private Long appId;
	
	private Integer partnerId;
	
	private String cTime;
	
	private Date  startTime ;
	
	private Date  endTime ;
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPayMode() {
		return payMode;
	}

	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}


	public BigDecimal getPayTotalPrice() {
		return payTotalPrice;
	}

	public void setPayTotalPrice(BigDecimal payTotalPrice) {
		this.payTotalPrice = payTotalPrice;
	}

	public String getcTime() {
		return cTime;
	}

	public void setcTime(String cTime) {
		this.cTime = cTime;
	}
	
	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	
}

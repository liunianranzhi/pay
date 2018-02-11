package com.yijia360.pay.entity.po.paystatistics;

import java.math.BigDecimal;
import java.util.Date;

import com.yijia360.pay.entity.po.BaseEntity;

public class EveryDayTotalStatistical extends BaseEntity{

	private Long id ;
	
	private Integer payMode ;
	
	private BigDecimal channelTotalPrice ;
	
	private Integer channelTotalCount ;
	
	private Date uTime;

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

	public BigDecimal getChannelTotalPrice() {
		return channelTotalPrice;
	}

	public void setChannelTotalPrice(BigDecimal channelTotalPrice) {
		this.channelTotalPrice = channelTotalPrice;
	}

	public Integer getChannelTotalCount() {
		return channelTotalCount;
	}

	public void setChannelTotalCount(Integer channelTotalCount) {
		this.channelTotalCount = channelTotalCount;
	}

	public Date getuTime() {
		return uTime;
	}

	public void setuTime(Date uTime) {
		this.uTime = uTime;
	}



	
}

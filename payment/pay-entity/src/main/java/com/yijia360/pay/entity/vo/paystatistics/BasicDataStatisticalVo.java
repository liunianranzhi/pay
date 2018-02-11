package com.yijia360.pay.entity.vo.paystatistics;

import java.math.BigDecimal;

public class BasicDataStatisticalVo {

	private Long id ;
	
	private Integer payMode ;
	
	private BigDecimal payTotalPrice ;
	
	private String cTime;

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

	
	
}

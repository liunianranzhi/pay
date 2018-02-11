package com.yijia360.pay.entity.vo.paystatistics;

import java.math.BigDecimal;

public class TotalStatisticalVo {

	private Integer payMode;
	private BigDecimal dailyTotalPrice;
	private Integer dailyTotalCount;
	private BigDecimal historyTotalPrice;
	private Integer historyTotalCount;
	private Integer cTime;
	
	public Integer getPayMode() {
		return payMode;
	}
	public void setPayMode(Integer payMode) {
		this.payMode = payMode;
	}
	public BigDecimal getDailyTotalPrice() {
		return dailyTotalPrice;
	}
	public void setDailyTotalPrice(BigDecimal dailyTotalPrice) {
		this.dailyTotalPrice = dailyTotalPrice;
	}
	public Integer getDailyTotalCount() {
		return dailyTotalCount;
	}
	public void setDailyTotalCount(Integer dailyTotalCount) {
		this.dailyTotalCount = dailyTotalCount;
	}
	public BigDecimal getHistoryTotalPrice() {
		return historyTotalPrice;
	}
	public void setHistoryTotalPrice(BigDecimal historyTotalPrice) {
		this.historyTotalPrice = historyTotalPrice;
	}
	public Integer getHistoryTotalCount() {
		return historyTotalCount;
	}
	public void setHistoryTotalCount(Integer historyTotalCount) {
		this.historyTotalCount = historyTotalCount;
	}
	public Integer getcTime() {
		return cTime;
	}
	public void setcTime(Integer cTime) {
		this.cTime = cTime;
	}
	
}

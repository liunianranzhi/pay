package com.yijia360.pay.entity.vo.api;

/**
 * 
 * @author yangjian
 * @date 2017-03-17
 */
public class ApiArgsVo {

	private Integer argsId;
	private Integer apiId;
	private String argName;
	private String argDesc;
	private Long uTime;
	public Integer getArgsId() {
		return argsId;
	}
	public void setArgsId(Integer argsId) {
		this.argsId = argsId;
	}
	public Integer getApiId() {
		return apiId;
	}
	public void setApiId(Integer apiId) {
		this.apiId = apiId;
	}
	public String getArgName() {
		return argName;
	}
	public void setArgName(String argName) {
		this.argName = argName;
	}
	public String getArgDesc() {
		return argDesc;
	}
	public void setArgDesc(String argDesc) {
		this.argDesc = argDesc;
	}
	public Long getuTime() {
		return uTime;
	}
	public void setuTime(Long uTime) {
		this.uTime = uTime;
	}
	
}

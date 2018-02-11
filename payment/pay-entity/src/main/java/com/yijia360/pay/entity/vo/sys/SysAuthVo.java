package com.yijia360.pay.entity.vo.sys;

import org.springframework.stereotype.Component;

import com.yijia360.pay.entity.po.CommonEnum;




/**
 * @author wangzhen
 * @date  2016-11-29
 */
@Component
public class SysAuthVo{
	
	private Integer sysAuthId;
	private String sysAuthName;
	private String sysAuthAction;
	private Integer sysAuthPid;
	private Integer state = CommonEnum.effective.getCode();
	private Integer level;
	
	public Integer getSysAuthId() {
		return sysAuthId;
	}
	public void setSysAuthId(Integer sysAuthId) {
		this.sysAuthId = sysAuthId;
	}
	public String getSysAuthName() {
		return sysAuthName;
	}
	public void setSysAuthName(String sysAuthName) {
		this.sysAuthName = sysAuthName;
	}
	public String getSysAuthAction() {
		return sysAuthAction;
	}
	public void setSysAuthAction(String sysAuthAction) {
		this.sysAuthAction = sysAuthAction;
	}
	public Integer getSysAuthPid() {
		return sysAuthPid;
	}
	public void setSysAuthPid(Integer sysAuthPid) {
		this.sysAuthPid = sysAuthPid;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}

}
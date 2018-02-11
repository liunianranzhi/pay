package com.yijia360.pay.entity.po.sys;

import org.springframework.stereotype.Component;

import com.yijia360.pay.entity.po.BaseEntity;


/**
 * @author wangzhen
 * @date  2016-11-29
 */
@Component
public class SysRoleAuth extends BaseEntity{
	
	private Integer sysAuthId;
	
	private Integer sysRoleId;
	
	private Integer level;
	
	private Integer sysAuthPid;
	
	
	public Integer getSysAuthPid() {
		return sysAuthPid;
	}

	public void setSysAuthPid(Integer sysAuthPid) {
		this.sysAuthPid = sysAuthPid;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getSysAuthId() {
		return sysAuthId;
	}

	public void setSysAuthId(Integer sysAuthId) {
		this.sysAuthId = sysAuthId;
	}

	public Integer getSysRoleId() {
		return sysRoleId;
	}

	public void setSysRoleId(Integer sysRoleId) {
		this.sysRoleId = sysRoleId;
	}
}
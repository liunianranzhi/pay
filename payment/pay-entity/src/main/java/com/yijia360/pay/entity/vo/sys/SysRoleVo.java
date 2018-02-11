package com.yijia360.pay.entity.vo.sys;

import org.springframework.stereotype.Component;

/**
 * @author wangzhen
 * @date  2016-11-29
 */
@Component
public class SysRoleVo{
	
  private Integer sysRoleId;
  private String sysRoleName;
  private Integer state;

  public Integer getSysRoleId() {
		return sysRoleId;
	}
	public void setSysRoleId(Integer sysRoleId) {
		this.sysRoleId = sysRoleId;
	}
	public String getSysRoleName() {
		return sysRoleName;
	}
	public void setSysRoleName(String sysRoleName) {
		this.sysRoleName = sysRoleName;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}
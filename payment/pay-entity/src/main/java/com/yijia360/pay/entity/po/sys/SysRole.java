package com.yijia360.pay.entity.po.sys;

import org.springframework.stereotype.Component;

import com.yijia360.pay.entity.po.BaseEntity;
import com.yijia360.pay.entity.po.CommonEnum;


/**
 * @author wangzhen
 * @date  2016-11-29
 */
@Component
public class SysRole extends BaseEntity{
	
  private Integer sysRoleId;
  private String sysRoleName;
  private Integer state=CommonEnum.effective.getCode();

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
package com.yijia360.pay.entity.vo.sys;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author wangzhen
 * @date  2016-11-29
 */
@Component
public class SysRoleAuthVo{
	
	private SysAuthVo sysAuth;
	
	private SysRoleVo sysRole;
	
	private List<SysRoleAuthVo> children;
	
	public List<SysRoleAuthVo> getChildren() {
		return children;
	}

	public void setChildren(List<SysRoleAuthVo> children) {
		this.children = children;
	}

	public SysAuthVo getSysAuth() {
		return sysAuth;
	}

	public void setSysAuth(SysAuthVo sysAuth) {
		this.sysAuth = sysAuth;
	}

	public SysRoleVo getSysRole() {
		return sysRole;
	}

	public void setSysRole(SysRoleVo sysRole) {
		this.sysRole = sysRole;
	}
}
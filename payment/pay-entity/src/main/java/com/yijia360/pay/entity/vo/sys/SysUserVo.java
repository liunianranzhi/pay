package com.yijia360.pay.entity.vo.sys;

/**
 * @author wangzhen
 * @date  2016-11-29
 */
public class SysUserVo {

	private Integer sysUserId;
	  private Integer sysRoleId;
	  private String sysUserName;
	  private String sysUserMail;
	  private String sysUserPwd;
	  private Integer state;
	  private String realName;
	  private String phone;
	  private String companyName;
	  
	public Integer getSysUserId() {
		return sysUserId;
	}
	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}
	public Integer getSysRoleId() {
		return sysRoleId;
	}
	public void setSysRoleId(Integer sysRoleId) {
		this.sysRoleId = sysRoleId;
	}
	public String getSysUserName() {
		return sysUserName;
	}
	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}
	public String getSysUserMail() {
		return sysUserMail;
	}
	public void setSysUserMail(String sysUserMail) {
		this.sysUserMail = sysUserMail;
	}
	public String getSysUserPwd() {
		return sysUserPwd;
	}
	public void setSysUserPwd(String sysUserPwd) {
		this.sysUserPwd = sysUserPwd;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	  
}

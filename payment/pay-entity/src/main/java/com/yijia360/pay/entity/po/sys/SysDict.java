package com.yijia360.pay.entity.po.sys;

import org.springframework.stereotype.Component;

import com.yijia360.pay.entity.po.BaseEntity;


/**
 * 
 * @author yangjian
 * @Date 2016-12-06
 */
@Component
public class SysDict extends BaseEntity{
	
	private Integer sysDictId;
	
	private String dictName;
	
	private String dictValue;
	
	private String dictDesc;

	public Integer getSysDictId() {
		return sysDictId;
	}

	public void setSysDictId(Integer sysDictId) {
		this.sysDictId = sysDictId;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getDictDesc() {
		return dictDesc;
	}

	public void setDictDesc(String dictDesc) {
		this.dictDesc = dictDesc;
	}

	
}

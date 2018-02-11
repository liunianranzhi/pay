package com.yijia360.pay.admin.dao.partner;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yijia360.pay.admin.dao.BaseDao;
import com.yijia360.pay.entity.po.partner.PartnerApp;
import com.yijia360.pay.entity.vo.partner.PartnerAppVo;

@Mapper
public interface PartnerAppDao extends BaseDao<PartnerApp, PartnerAppVo>{

	public List<PartnerAppVo> getList(PartnerApp partnerApp);
	
}

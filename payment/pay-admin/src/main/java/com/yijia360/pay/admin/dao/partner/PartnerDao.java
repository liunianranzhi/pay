package com.yijia360.pay.admin.dao.partner;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yijia360.pay.admin.dao.BaseDao;
import com.yijia360.pay.entity.po.partner.Partner;
import com.yijia360.pay.entity.vo.partner.PartnerVo;

@Mapper
public interface PartnerDao extends BaseDao<Partner, PartnerVo>{
	
	public List<PartnerVo> getList(Partner partner);
	
}

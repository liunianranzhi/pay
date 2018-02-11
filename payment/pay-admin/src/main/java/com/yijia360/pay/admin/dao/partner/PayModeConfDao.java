package com.yijia360.pay.admin.dao.partner;

import org.apache.ibatis.annotations.Mapper;

import com.yijia360.pay.admin.dao.BaseDao;
import com.yijia360.pay.entity.po.partner.PayModeConf;
import com.yijia360.pay.entity.vo.partner.PayModeConfVo;

@Mapper
public interface PayModeConfDao extends BaseDao<PayModeConf, PayModeConfVo>{

}

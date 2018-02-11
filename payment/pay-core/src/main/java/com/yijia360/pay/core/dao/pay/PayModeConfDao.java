package com.yijia360.pay.core.dao.pay;

import org.springframework.stereotype.Repository;

import com.yijia360.pay.core.dao.BaseDao;
import com.yijia360.pay.entity.po.partner.PayModeConf;
import com.yijia360.pay.entity.vo.partner.PayModeConfVo;

@Repository
public interface PayModeConfDao extends BaseDao<PayModeConf, PayModeConfVo>{

}

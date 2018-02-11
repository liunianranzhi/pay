package com.yijia360.pay.core.service.pay.payModeConf;

import org.springframework.stereotype.Service;

import com.yijia360.pay.entity.vo.partner.PayModeConfVo;

@Service
public interface PayModeConfService {

	PayModeConfVo InfoById(Integer partnerId,Long appId,Integer payModeCode);
}

package com.yijia360.pay.core.service.order;

import org.springframework.stereotype.Service;

import com.yijia360.pay.core.service.ServiceResult;

@Service
public interface OrderNotifyService {

	
	/**
	 * 通知合作方
	 * @param orderId
	 * @return
	 */
	ServiceResult notifyPartner(Long orderId);
	
}

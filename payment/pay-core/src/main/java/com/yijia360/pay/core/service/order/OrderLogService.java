package com.yijia360.pay.core.service.order;

import org.springframework.stereotype.Service;

@Service
public interface OrderLogService {

	boolean saveOrderLog(Long orderId, String opName, String opMsg);
	
}

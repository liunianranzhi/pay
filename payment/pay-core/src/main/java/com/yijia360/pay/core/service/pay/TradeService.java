package com.yijia360.pay.core.service.pay;

import com.yijia360.pay.core.service.ServiceResult;
import com.yijia360.pay.entity.po.order.Order;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月30日下午1:52:56
 */
public interface TradeService {

	/**
	 * 支付
	 * @param order
	 * @return
	 */
	public ServiceResult trade(Order order);

}

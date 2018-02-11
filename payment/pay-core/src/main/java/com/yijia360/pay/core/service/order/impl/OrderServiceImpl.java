package com.yijia360.pay.core.service.order.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.yijia360.pay.core.dao.order.OrderDao;
import com.yijia360.pay.core.service.ServiceResult;
import com.yijia360.pay.core.service.cache.CacheService;
import com.yijia360.pay.core.service.order.BaseService;
import com.yijia360.pay.core.service.order.OrderService;
import com.yijia360.pay.entity.cache.CacheKey;
import com.yijia360.pay.entity.cache.CacheName;
import com.yijia360.pay.entity.enums.OrderStatus;
import com.yijia360.pay.entity.po.order.Order;
import com.yijia360.pay.entity.vo.order.OrderVo;

@CacheConfig(cacheNames=CacheName.order)
@Service
public class OrderServiceImpl extends BaseService implements OrderService{

	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private CacheService cacheService;
	
	@Override
	public ServiceResult creatOrder(Order order) {
		OrderVo outOrder = info(order.getOutTradeNo(),order.getAppId(),order.getPartnerId());
		if(!ObjectUtils.isEmpty(outOrder)){
			return buildServiceResult(false, "请勿重复提交订单");
		}
		int count = orderDao.save(order);
		return buildServiceResult(count, "下单");
	}

	@Cacheable(unless="#result == null")
	@Override
	public OrderVo info(String outTradeNo,Long appId,Integer partnerId) {
		Order order = new Order();
		order.setOutTradeNo(outTradeNo);
		order.setAppId(appId);
		order.setPartnerId(partnerId);
		return orderDao.get(order);
	}

	//@Cacheable(unless="#result == null")
	@Override
	public OrderVo info(Long orderId) {
		Order order = new Order();
		order.setOrderId(orderId);
		return orderDao.get(order);
	}

	@Override
	public ServiceResult update(OrderStatus orderStatus, Long orderId,Integer payMode,String tradeNo) {
		OrderVo orderVo = info(orderId);
		
		Order order = new Order();
		order.setOrderId(orderId);
		order.setStatus(orderStatus.getCode());
		order.setStatusMsg(orderStatus.getMessage());
		order.setPayMode(payMode);
		order.setTradeNo(tradeNo);
		int count = orderDao.update(order);
		if(count > 0){
			//刷新订单
			//cacheService.refreshCache(String.format(CacheKey.service_OrderServiceImpl_Info, orderId));
			cacheService.refreshCache(String.format(CacheKey.service_OrderServiceImpl_Info_outTradeNo_appId_partnerId, orderVo.getOutTradeNo(),orderVo.getAppId(),orderVo.getPartnerId()));
		}
		return buildServiceResult(count, "订单更新");
	}


}
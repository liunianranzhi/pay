package com.yijia360.pay.admin.service.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yijia360.pay.admin.dao.order.OrderDao;
import com.yijia360.pay.entity.enums.PayMode;
import com.yijia360.pay.entity.po.order.Order;
import com.yijia360.pay.entity.vo.PageVo;
import com.yijia360.pay.entity.vo.order.OrderVo;

@Service
public class OrderService {
	
	@Autowired
	private OrderDao orderDao;
	
	public PageVo page(Order order){
		int count = orderDao.count(order);
		Object result = orderDao.list(order);
		PageVo page = new PageVo(order.getPageNo(), order.getPageSize(), count, result);
		return page;
	}
	
	public List<OrderVo> getList(Order order){
		return orderDao.getList(order);
	}
	
	public List<JSONObject> exportList(Order order){
		List<OrderVo> orders = orderDao.getList(order);
		List<JSONObject> exportList = new ArrayList<JSONObject>(orders.size());
		JSONObject parseObject;
	    PayMode[] payModes = PayMode.values();
	    for (OrderVo orderVo : orders) {
	    	parseObject = JSON.parseObject(JSONObject.toJSONString(orderVo));
	    	parseObject.put("_totalFee", orderVo.formatTotalFee());
	    	for (PayMode payMode : payModes) {
	    		if(payMode.getCode() == orderVo.getPayMode()){
	    			parseObject.put("payModeMsg", payMode.getMsg());
	    		}
			}
	    	exportList.add(parseObject);
		}
		return exportList;
	}
	
}

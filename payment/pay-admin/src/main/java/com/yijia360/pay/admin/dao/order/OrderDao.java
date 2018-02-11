package com.yijia360.pay.admin.dao.order;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yijia360.pay.admin.dao.BaseDao;
import com.yijia360.pay.entity.po.order.Order;
import com.yijia360.pay.entity.vo.order.OrderVo;

@Mapper
public interface OrderDao extends BaseDao<Order, OrderVo>{

	public List<OrderVo> getList(Order order);
	
}

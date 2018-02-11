package com.yijia360.pay.admin.dao.order;

import org.apache.ibatis.annotations.Mapper;

import com.yijia360.pay.admin.dao.BaseDao;
import com.yijia360.pay.entity.po.order.OrderNotify;
import com.yijia360.pay.entity.vo.order.OrderNotifyVo;

@Mapper
public interface OrderNotifyDao extends BaseDao<OrderNotify, OrderNotifyVo>{

}

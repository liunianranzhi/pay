package com.yijia360.pay.admin.dao.order;

import org.apache.ibatis.annotations.Mapper;

import com.yijia360.pay.admin.dao.BaseDao;
import com.yijia360.pay.entity.po.order.OrderLog;
import com.yijia360.pay.entity.vo.order.OrderVo;

@Mapper
public interface OrderLogDao extends BaseDao<OrderLog, OrderVo>{

}

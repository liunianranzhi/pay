package com.yijia360.pay.admin.dao.paybasicdata;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yijia360.pay.admin.dao.BaseDao;
import com.yijia360.pay.entity.po.paystatistics.EveryDayTotalStatistical;
import com.yijia360.pay.entity.vo.paystatistics.EveryDayTotalStatisticalVo;

@Mapper
public interface EveryDayTotalStatisticalDao extends BaseDao<EveryDayTotalStatistical ,EveryDayTotalStatisticalVo> {

	public List<EveryDayTotalStatisticalVo> getList(EveryDayTotalStatistical statistical);
	
	void updateAlluTime(EveryDayTotalStatistical statistical);
}

package com.yijia360.pay.admin.dao.paybasicdata;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yijia360.pay.admin.dao.BaseDao;
import com.yijia360.pay.entity.po.paystatistics.TotalStatistical;
import com.yijia360.pay.entity.vo.paystatistics.TotalStatisticalVo;

@Mapper
public interface TotalStatisticalDao extends BaseDao<TotalStatistical, TotalStatisticalVo>{

	public List<TotalStatisticalVo> getBeforeList(TotalStatistical totalStatistical);
	
}

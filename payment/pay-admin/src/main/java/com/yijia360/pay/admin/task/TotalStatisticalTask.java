package com.yijia360.pay.admin.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.yijia360.pay.admin.service.statistical.BasicStatisticalSeivice;
import com.yijia360.pay.admin.service.statistical.TotalStatisticalService;
import com.yijia360.pay.entity.po.paystatistics.EveryDayTotalStatistical;
import com.yijia360.pay.entity.po.paystatistics.TotalStatistical;
import com.yijia360.pay.entity.vo.paystatistics.EveryDayTotalStatisticalVo;
import com.yijia360.pay.entity.vo.paystatistics.TotalStatisticalVo;
import com.yijia360.pay.utils.DateUtils;

@Component
public class TotalStatisticalTask {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BasicStatisticalSeivice basicStatisticalSeivice;
	
	@Autowired
	private TotalStatisticalService totalStatisticalService;
	
	@Scheduled(cron="0 2 0 * * ? ")
	public void saveTotalData(){
		totalSave();
		logger.info("今日数据统计完成");
	}
	
	public void totalSave(){
		boolean result = false;
		EveryDayTotalStatistical statistical = new EveryDayTotalStatistical();
		List<EveryDayTotalStatisticalVo> basicDatas = basicStatisticalSeivice.getList(statistical);
		Date date = new Date();
		
		//获取当前日期  
		Integer tDate = DateUtils.getDateToInt(date);
		Integer bDate;
		//获取前一天日期
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.DAY_OF_MONTH, -1);
		bDate = DateUtils.getDateToInt(calender.getTime());
		
		TotalStatistical totalStatistical = new TotalStatistical();
		//查询前一天列表
		totalStatistical.setcTime(bDate);
		List<TotalStatisticalVo> beforeDatas = totalStatisticalService.getBeforeList(totalStatistical);
		
		//保存时设置日期为当前日期
		totalStatistical.setcTime(tDate);
		if(!CollectionUtils.isEmpty(basicDatas)){
			for (EveryDayTotalStatisticalVo statisticalVo : basicDatas) {
				totalStatistical.setPayMode(statisticalVo.getPayMode());
				totalStatistical.setDailyTotalCount(statisticalVo.getChannelTotalCount());
				totalStatistical.setDailyTotalPrice(statisticalVo.getChannelTotalPrice());
				statisticalVo.getuTime();
				if(!CollectionUtils.isEmpty(beforeDatas)){
					for (TotalStatisticalVo totalStatisticalVo : beforeDatas) {
						if(totalStatisticalVo.getPayMode() == statisticalVo.getPayMode()){
							totalStatistical.setHistoryTotalPrice(statisticalVo.getChannelTotalPrice().add(totalStatisticalVo.getHistoryTotalPrice()));
							totalStatistical.setHistoryTotalCount(statisticalVo.getChannelTotalCount() + totalStatisticalVo.getHistoryTotalCount());
							result = totalStatisticalService.save(totalStatistical);
							continue;
						}
							
					}
				}else{
					totalStatistical.setHistoryTotalCount(statisticalVo.getChannelTotalCount());
					totalStatistical.setHistoryTotalPrice(statisticalVo.getChannelTotalPrice());
					result = totalStatisticalService.save(totalStatistical);
				}
				logger.info(statisticalVo.getPayMode() + "   今日累计数据统计结果：{}",result);
			}
		}
	}
	
}

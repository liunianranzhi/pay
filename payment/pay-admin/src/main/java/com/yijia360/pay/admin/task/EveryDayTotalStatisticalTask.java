package com.yijia360.pay.admin.task;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.yijia360.pay.admin.service.statistical.BasicDataSeivice;
import com.yijia360.pay.admin.service.statistical.BasicStatisticalSeivice;
import com.yijia360.pay.entity.enums.PayMode;
import com.yijia360.pay.entity.po.paystatistics.BasicDataStatistical;
import com.yijia360.pay.entity.po.paystatistics.EveryDayTotalStatistical;
import com.yijia360.pay.entity.vo.paystatistics.BasicDataStatisticalVo;
import com.yijia360.pay.entity.vo.paystatistics.EveryDayTotalStatisticalVo;

@Component
public class EveryDayTotalStatisticalTask {

	@Autowired
	private  BasicStatisticalSeivice basicStatisticalSeivice;
	
	@Autowired
	private  BasicDataSeivice basicDataSeivice;
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());  
    
    @Scheduled(cron="0 0/5 * * * ? ") //5分钟0秒执行一次  
    public void statusCheck() {      
        logger.info("每5分钟执行一次。开始执行新增加统计数据");  
        updateEveryDayTotalStatistical();
        logger.info("每5分钟执行一次。结束执行新增加统计数据。");  
    }    
    
    @Scheduled(cron="0 2 0 * * ? ") //每天00:02:00触发
    public void init() {
        logger.info("每天00：02分初始化统计表每天的数据");  
        initData();
    } 
    
    //初始化
    public void initData(){
    	Date date = new Date();
    	EveryDayTotalStatistical everydata  = new EveryDayTotalStatistical();
    	PayMode [] payModeArry = PayMode.values();
		for(PayMode payMode : payModeArry){
			BigDecimal zero = new BigDecimal(0);
			everydata.setChannelTotalCount(0);
			everydata.setChannelTotalPrice(zero);
			everydata.setPayMode(payMode.getCode());
			everydata.setuTime(getSubDate(date));
			basicStatisticalSeivice.update(everydata);
		}
			
    }
    
	public void updateEveryDayTotalStatistical() {
		Date endTime = null;
		PayMode [] payModeArry = PayMode.values();
		for(PayMode payMode : payModeArry){
			BasicDataStatistical basic = new BasicDataStatistical();
			EveryDayTotalStatistical everyStat  = new EveryDayTotalStatistical();
			everyStat.setPayMode(payMode.getCode());
			//取出每天统计表数据
			List<EveryDayTotalStatisticalVo> everyDaylist = basicStatisticalSeivice.getList(everyStat);
			basic.setStartTime(everyDaylist.get(0).getuTime());
			basic.setEndTime(new Date());
			endTime=basic.getEndTime();
			//取出未处理基础表数据
			basic.setPayMode(payMode.getCode());
			List<BasicDataStatisticalVo> basicDatalist=basicDataSeivice.getList(basic);
			BigDecimal totalPrice = new BigDecimal(0);
			Integer totalCount = 0;
			if(!CollectionUtils.isEmpty(basicDatalist)){
				for (int i = 0; i < basicDatalist.size(); i++) {
					totalPrice=totalPrice.add(basicDatalist.get(i).getPayTotalPrice());
					totalCount=totalCount+1;
				}
				everyStat.setChannelTotalPrice(totalPrice.add(everyDaylist.get(0).getChannelTotalPrice()));
				everyStat.setChannelTotalCount(everyDaylist.get(0).getChannelTotalCount()+totalCount);
				basicStatisticalSeivice.update(everyStat);
			}
		}
		//更新时间
		EveryDayTotalStatistical stat  = new EveryDayTotalStatistical();
		stat.setuTime(getSubDate(endTime));
		basicStatisticalSeivice.updateAlluTime(stat);
	}


	//拼接末尾秒数为00
	public Date getSubDate(Date endTime) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
		String str = sdf.format(endTime);
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	//初始化时间
	public Date getInitDate(Date newDate){
		Date afterDate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String str1 = sdf.format(newDate);
		try {
			 afterDate = sdf.parse(str1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return afterDate;
	}
	
	public BigDecimal formatTotalFee(Integer price) {
		BigDecimal totalPrice = new BigDecimal(price);
		return totalPrice;
	}

}

package com.yijia360.pay.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	//获取当天开始时间
	public static Date getStartDate(){
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    
	    Date start = calendar.getTime();
	    calendar.add(Calendar.DAY_OF_MONTH, 1);
	    calendar.add(Calendar.SECOND, -1);
	    return start ;
	}
	
	//获取当天结束时间
	public static Date getEndDate(){
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    
	    calendar.add(Calendar.DAY_OF_MONTH, 1);
	    calendar.add(Calendar.SECOND, -1);
	    Date end = calendar.getTime();
	    return end ;
	}
	
	//时间转化成字符串
	public static String getDateToString(Date date){
	   SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
	   String str = sdf.format(date);
	   return str ;
	}
	
	public static Integer getDateToInt(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Integer time = Integer.parseInt(sdf.format(date));
		return time;
	}
	
	public static Long getDateToLong(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Long time = Long.parseLong(sdf.format(date));
		return time;
	}
	
	//字符串转化时间
	public static Date getStringToDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}

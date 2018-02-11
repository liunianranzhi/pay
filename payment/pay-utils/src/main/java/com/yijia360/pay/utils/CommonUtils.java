package com.yijia360.pay.utils;

import org.springframework.util.StringUtils;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月29日上午9:06:38
 */
public class CommonUtils {
	
	/**
	 * 判断字符串是否为数字
	 * @param str
	 * @return
	 */
	public static boolean isNumber(String str){
		return !StringUtils.isEmpty(str) && str.matches("^[0-9]*$")?true:false;
	}

	/**
	 *  数字或字母组成
	 * @param 
	 * @return
	 */
	public static boolean isNumberOrLetter(String str){
		return !StringUtils.isEmpty(str) && str.matches("^[A-Za-z0-9]*$") ?true:false;
	}
	
	public static boolean isNumberOrLetterOrChinaese(String str){
		return !StringUtils.isEmpty(str) && str.matches("^[A-Za-z0-9\u4e00-\u9fa5]+$") ?true:false;
	}
	
	/**
	 * 可以带参数  
	 * @param str  http://www.inongfeng.com/a/b/c?abc=123
	 * @return
	 */
	public static boolean isUri(String str){
		return !StringUtils.isEmpty(str) && str.matches("^(?:https?://)?[\\w]{1,}(?:\\.?[\\w]{1,})+[\\w-_/?&=#%:]*$") ?true:false;
	}

	/**
	 * 不能带参数
	 * @param str  http://www.inongfeng.com/a/b/c
	 * @return
	 */
	public static boolean isUriNotParas(String str){
		return !StringUtils.isEmpty(str) && str.matches("^(?:https?://)?[\\w]{1,}(?:\\.?[\\w]{1,})+[\\w-_/]*$") ?true:false;
	}

	/**
	 * sign规则  32位 数字或大写字母
	 * @param sign
	 * @return
	 */
	public static boolean isSign(String sign){
		return sign.matches("^[A-Z0-9]{32}")?true:false;
	}
	
	/**
	 * appId 16位数字
	 * @param appId
	 * @return
	 */
	public static boolean isAppId(String appId){
		return appId.matches("^[0-9]{16}")?true:false;
	}
	
	public static boolean isDecimal(String str){
		return !StringUtils.isEmpty(str) && (str.matches("^[-]{0,1}(\\d+)[\\.]+(\\d{2}+)$") || isNumber(str))?true:false;
	}
}
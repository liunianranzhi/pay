package com.yijia360.pay.utils;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月28日上午10:46:50
 */
public class PayUtils {
	
	/**
	 * sign参数拼接
	 * @param params
	 * @return
	 */
	public static String packageSign(Map<String, String> params){
		// 先将参数以其参数名的字典序升序进行排序
		TreeMap<String, String> sortedParams = new TreeMap<String, String>(params);
		// 遍历排序后的字典，将所有参数按"key=value"格式拼接在一起
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Entry<String, String> param : sortedParams.entrySet()) {
			String value = param.getValue();
			if(StringUtils.isEmpty(value)) {
				continue;
			}
			if (first) {
				first = false;
			} else {
				sb.append("&");
			}
			sb.append(param.getKey()).append("=");
			sb.append(value);
		}
		return sb.toString();
	}
	
	
	/**
	 * 生成签名
	 * @return
	 */
	public static String createSign(Map<String, String> params, String appSecret) {
		params.remove("sign");
		String stringA = packageSign(params);
		String stringSignTemp = stringA + "&appSecret=" + appSecret;
		return DigestUtils.md5DigestAsHex(stringSignTemp.getBytes()).toUpperCase();
	}
	
	/**
	 * 生成订单ID
	 * @return
	 */
	public static Long createOrderId(){
		return IdWorker.getFlowIdWorkerInstance().nextId();
	}
}
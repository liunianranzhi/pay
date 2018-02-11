package com.yijia360.pay.core.service.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yijia360.pay.core.service.ServiceResult;

/**
 * @author wangzhen@inongfeng.com
 * @ctime 2017年3月30日上午10:40:08
 */
public class BaseService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public ServiceResult buildServiceResult(int resultCount,String msg){
			ServiceResult result = new ServiceResult();
			boolean flag = resultCount==1?true:false;
			String resultMsg = flag?msg+"成功":msg+"失败";
			result.setMsg(resultMsg);
			result.setResult(flag);
			result.setStatus(flag);
			return result;
	}
	
	public ServiceResult buildServiceResult(boolean flag,String msg){
		ServiceResult result = new ServiceResult();
		result.setMsg(msg);
		result.setResult(flag);
		result.setStatus(flag);
		return result;
	}


	public ServiceResult buildServiceResult(boolean flag,Object result,String msg){
		ServiceResult serviceResult = new ServiceResult();
		serviceResult.setMsg(msg);
		serviceResult.setResult(result);
		serviceResult.setStatus(flag);
		return serviceResult;
	}
}

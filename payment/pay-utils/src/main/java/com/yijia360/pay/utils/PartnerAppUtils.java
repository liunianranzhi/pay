package com.yijia360.pay.utils;

import java.util.Random;

import org.springframework.util.DigestUtils;


public class PartnerAppUtils {
	
	public static String getRandom(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

	
	/**
	 * 获取秘钥盐
	 * @param length
	 * @return
	 */
	public static String getSalt() {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

	
	/**
	 * 获取16位appId
	 * @return
	 */
	public static Long getAppId(){
		Long uid = IdWorker.getFlowIdWorkerInstance().nextId();
		return uid;
	}
	
	/**
	 * 获取秘钥
	 * @param salt
	 * @param appId
	 * @return
	 */
	public static String getAppSecret(String salt,String appId){
		StringBuffer sb = new StringBuffer();
		sb.append(salt);
		sb.append(appId);
		return DigestUtils.md5DigestAsHex(sb.toString().getBytes());
	}
	
	/**
	 * 验证appId、appSecret
	 * @param salt
	 * @param appId
	 * @param appSecret
	 * @return
	 */
	public static boolean verify(String salt,String appId,String appSecret){
		
		String _appSecret =  getAppSecret(salt, appId);
		
		return _appSecret.equals(appSecret)?true:false;
	}
	
	
	public static void main(String[] args) {
		
		String salt = getSalt();
		String appId = getAppId().toString();
		
		System.out.println(salt);
		System.out.println(appId);
		
		String appSecret = getAppSecret(salt, appId);
		System.out.println(appSecret);
		
		System.out.println(verify(salt, appId, appSecret));
	}
	
}

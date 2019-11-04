package com.neusoft.mid.ec.api.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UUIDGenerator {

    private UUIDGenerator() {
    }

    /**
     * 获得一个UUID
     * @return String UUID
     */
    public static String getUUID() {
        String uuidString = UUID.randomUUID().toString();
        return uuidString;
    }

    /**
     * 获得一个32位UUID
     * @return String UUID
     */
    public static String getUUID32() {
        String uuidString = UUID.randomUUID().toString().replaceAll("-", "");
        return uuidString;
    }
    
    /**
     * 生成20位流水号
     * @param int len()
     * @return String (前面 14位是当前时间 字符串，后面是随机数，多少位随机数取决于len长度)
     */
    public static String getReqId(int len){
    	Date dat=new Date();
		SimpleDateFormat dfs = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String time=dfs.format(dat);
		
    	String[] randomValues = new String[]{"0","1","2","3","4","5","6","7","8","9",
    			   "a","b","c","d","e","f","g","h","i","j","k","l","m","n","u",
    			   "t","s","o","x","v","p","q","r","w","y","z"};
    	StringBuffer str = new StringBuffer();
    		for(int i = 0;i < len; i++){
    			Double number=Math.random()*(randomValues.length-1);
    			str.append(randomValues[number.intValue()]);
    		}
    		
    	return time+str.toString();
    }
    
    /**
     * 获得一个6位UUID
     * @return String UUID
     */
    public static String getUUID6() {
        String uuidString = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 6);
        return uuidString;
    }
    
    /**
     * 生成16位流水号
     * @param int len()
     * @return String (随机数，多少位随机数取决于len长度)
     */
    public static String getOrderId(int len){
    	String[] randomValues = new String[]{"0","1","2","3","4","5","6","7","8","9"};
    	StringBuffer str = new StringBuffer();
    		for(int i = 0;i < len; i++){
    			Double number=Math.random()*(randomValues.length-1);
    			str.append(randomValues[number.intValue()]);
    		}
    		
    	return str.toString();
    }
}

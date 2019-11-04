package com.neusoft.mid.ec.api.util;

import java.util.HashMap;

public class DataUtil {
	public static HashMap<String,String> createAccountMap = new HashMap<String,String>();
    static {
    	createAccountMap.put("0", "成功");
    	createAccountMap.put("1010001", "系统错误");
    	createAccountMap.put("1010002", "非法请求");
    	createAccountMap.put("1010036", "Content-Type参数非法");
    	createAccountMap.put("1020002", "参数%s非法");
    	createAccountMap.put("1020003", "参数%s不能为空");
    	createAccountMap.put("1011006", "SP账号已经存在");
    	createAccountMap.put("1016016", "密码强度不符合安全要求");
    	createAccountMap.put("1010012", "用户名或密码错误");
    }
    
    public static HashMap<String,String> updateAccountMap = new HashMap<String,String>();
    static {
    	updateAccountMap.put("0", "成功");
    	updateAccountMap.put("1010001", "系统错误");
    	updateAccountMap.put("1010002", "非法请求");
    	updateAccountMap.put("1010036", "Content-Type参数非法");
    	updateAccountMap.put("1020002", "参数%s非法");
    	updateAccountMap.put("1020003", "参数%s不能为空");
    	updateAccountMap.put("1011001", "SP账号不存在");
    	updateAccountMap.put("1016016", "密码强度不符合安全要求");
    	updateAccountMap.put("1010012", "用户名或密码错误");
    	updateAccountMap.put("1011010", "开发者被冻结");
    }
    
    public static HashMap<String,String> createSeverMap = new HashMap<String,String>();
    static {
    	createSeverMap.put("0", "成功");
    	createSeverMap.put("1010001", "系统错误");
    	createSeverMap.put("1010002", "非法请求");
    	createSeverMap.put("1010036", "Content-Type参数非法");
    	createSeverMap.put("1020002", "参数%s非法");
    	createSeverMap.put("1020003", "参数%s不能为空");
    	createSeverMap.put("1011001", "SP账号不存在");
    	createSeverMap.put("0500006", "APP名称已经被使用了");
    	createSeverMap.put("1011010", "开发者被冻结");
    	createSeverMap.put("1010054", "应用类型和附属能力不能同时为空");
    	createSeverMap.put("1010052", "应用不支持此能力集");
    	createSeverMap.put("1010055", "所选参数%s与应用能力不匹配");
    	createSeverMap.put("1010012", "用户名或密码错误");
    }
    
    public static HashMap<String,String> updateSeverMap = new HashMap<String,String>();
    static {
    	updateSeverMap.put("0", "成功");
    	updateSeverMap.put("1010001", "系统错误");
    	updateSeverMap.put("1010002", "非法请求");
    	updateSeverMap.put("1010036", "Content-Type参数非法");
    	updateSeverMap.put("1020002", "参数%s非法");
    	updateSeverMap.put("1020003", "参数%s不能为空");
    	updateSeverMap.put("1011001", "SP账号不存在");
    	updateSeverMap.put("1011008", "应用不存在");
    	updateSeverMap.put("1011010", "开发者被冻结");
    	updateSeverMap.put("1010052", "应用不支持此能力集");
    	updateSeverMap.put("1010055", "所选参数%s与应用能力不匹配");
    	updateSeverMap.put("1010012", "用户名或密码错误");
    }

}

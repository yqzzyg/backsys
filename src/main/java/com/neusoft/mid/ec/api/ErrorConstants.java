package com.neusoft.mid.ec.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by puras on 10/9/16.
 */
public class ErrorConstants {
    // 短信验证码错误
    public static final int MSG_CODE_ERROR = 30001;
    
    // 管理系统验证码错误
    public static final int Mng_CODE_ERROR = 30002;
    
    // 不符合录入产品要求，请确认SI企业资质审核状态
    public static final int DONT_HAVE_EC_INSPECTION = 40001;
    
    //用户名或密码错误
    public static final int USERNAME_PASSWORD_ERROR = 50001;
    
    //热门产品不能超过四个
    public static final int HOT_PRODUCT_MAX = 60001;
    
    //企业名称不能重复
    public static final int NAME_REPEAT = 70001;
    /**
     * 创建caas失败
     */
    public static final int ERROR_CREATE = 70002;
    
    //手机号不能重复
    public static final int PHONE_REPEAT = 80001;
    
    //用户被禁用
    public static final int USER_FORBIDDEN = 90001;
    
    //原密码错误
    public static final int OLDPASSWORD_ERROR = 20001;
    
    //用户已存在
    public static final int USER_EXIST = 10001;
    
    //该用户已评论过改产品
    public static final int REPEAT_COMMENT_ERR = 11001;
    
    //用户不存在
    public static final int USER_NOT_EXIST = 12001;
    
    //该产品已被Si部署
    public static final int PRODUCT_ALREADY_DEPLOY = 13001;
    
    //该产品已被其他Si部署不能更新
    public static final int PRODUCT_DEPLOY_UPDATE = 14001;
    
    //产品名称不能重复
    public static final int PRODUCT_NAME_REPEAT = 15001;

    //caass
 // 成功
    public static final int SUCCESS_CODE = 200;
    
    // 系统错误
    public static final int INTERNAL_SERVER_ERROR = 1010001;
    
    //非法请求
    public static final int INVALID_REQUEST  = 1010002;
    
    //用户名或密码错误
    public static final int USERNAME_PASSWORD_ERROR_CAASS = 1010012;
    
    //邮箱已被绑定
    public static final int EMAIL_BINDING = 1011011;
    
    //记录已经存在。
    public static final int DATA_EXISTS = 1016002;
    
    //密码强度不符合安全要求
    public static final int SECURITY_REQUIREMENTS = 1016016;
    
    //-------------
    // 账号不存在。
    public static final int  ACCOUNT_UNEXISTED = 1011001;
    
    //应用不存在。
    public static final int  APP_DOES_NOT_EXIST = 1011008;

    // 应用已经上线
    public static final int  APP_HAS_BEEN_ONLINE = 1011009;

    //开发者被冻结。
    public static final int  USER_HAS_BEEN_FROZEN = 1011010;
    
  //编码不能重复
    public static final int CODE_REPEAT = 80002;
    
  //产品编码不能重复
    public static final int PRODUCT_CODE_REPEAT = 15002;
    
    /**
     * 登录错误次数超过三次返回码
     */
    public static final int LOG_LIMIT_CODE = 30003;
    
    /**
     * 用户不存在
     */
    public static final int LOG_ERROR_USER = -9999;
    
    /**
     * 用户多次登录、异地登录校验
     */
    public static final int LOG_DOUBLE_CHECK = -9998;
    
    
    
    public static Map<String,String> SendMsgRslt=new HashMap();
    
    static{
    	SendMsgRslt.put("00", "发送成功");
    	SendMsgRslt.put("01", "根据身份证姓名查不到本省手机号码");
    	SendMsgRslt.put("02", "匹配到手机号大于5个");
    	SendMsgRslt.put("03", "提供身份证或姓名与手机号不匹配");
    	SendMsgRslt.put("04", "匹配手机号码失败");
    	SendMsgRslt.put("99", "发送失败");
    }
    
    
    
    
}

package com.neusoft.mid.ec.api.serviceInterface.socialsecurity;

import com.neusoft.mid.ec.api.exception.GeneralException;
import org.jdom.JDOMException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * 社保卡查询业务
 */
public interface SocialSecurityCardQueryService {

     String getCard(String IDNo,String name) throws GeneralException, IOException, JDOMException;
     /**
      * 获取社保卡基础信息
      * @param IDNo
      * @param name
      * @return
      * @throws Exception
      */
     String getCardBasicInfo(String IDNo,String name) throws Exception;


     String getinuredInfo(Map params,Map<String,Object> mapHeaders) throws Exception;

     String getaccountInfo(Map params,Map<String,Object> mapHeaders) throws Exception;
     String getlostInfo(Map params,Map<String,Object> mapHeaders) throws Exception;
     String getpayInfo(Map params,Map<String,Object> mapHeaders) throws Exception;
     String getoldInfo(Map params,Map<String,Object> mapHeaders) throws Exception;


     String getPeron(Map params,Map<String,Object> mapHeaders) throws Exception;
}

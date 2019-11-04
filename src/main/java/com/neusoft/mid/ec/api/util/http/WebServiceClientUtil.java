package com.neusoft.mid.ec.api.util.http;

import com.alibaba.fastjson.JSONObject;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.net.URL;

/**
 * @author Administrator
 * webservice client
 * 包含就业接口的调用
 */
@Component
public class WebServiceClientUtil {

    private static  Logger LOG  = LoggerFactory.getLogger(WebServiceClientUtil.class);
    @Autowired
    private Environment environment;
    public static String employmentWsUrl;
    public static String batWsUrl;

    @PostConstruct
    private void init() {
    	employmentWsUrl = environment.getProperty("employment.ws.url");
        batWsUrl = environment.getProperty("rs.bat.saveFaceVfyResult");
    }


    /**
     * 调用就业接口
     * @param username
     * @param password
     * @param employmentServiceCode
     * @param jsonStr
     * @return
     */
    public  static JSON callEmploymentService(String username,String password,String employmentServiceCode,String jsonStr){
        String rst = null;
        LOG.info("callEmploymentService params: username={},password={},employmentServiceCode={},jsonStr={}",username,password,employmentServiceCode,jsonStr);
        final Service service = new Service();
        try {
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(employmentWsUrl));
            call.setOperationName(new QName("http://service.server.employment.neusoft.com", "query"));
            call.setReturnType(XMLType.XSD_STRING);
            call.addParameter(new QName("http://service.server.employment.neusoft.com", "account"), XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter(new QName("http://service.server.employment.neusoft.com", "password"), XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter(new QName("http://service.server.employment.neusoft.com", "function"), XMLType.XSD_STRING, ParameterMode.IN);
            call.addParameter(new QName("http://service.server.employment.neusoft.com", "param"), XMLType.XSD_STRING, ParameterMode.IN);
            call.setUseSOAPAction(false);
            call.setSOAPActionURI("http://service.server.employment.neusoft.com/services/employmentService/query");
            call.setTimeout(18000000);
            rst = (String) call.invoke(new Object[]{username,password,employmentServiceCode,jsonStr});
            XMLSerializer xml = new XMLSerializer();
            JSON json = xml.read(rst);
            LOG.info("callEmploymentService result={}",json.toString());
            return  json;
        } catch (Exception e) {
            LOG.error("接口调用错误",e);
        }
        return null;
    }

    /*public static void main(String[] args) {
        String idCard="411081199210226865";
        String userid="";//"dce6320b-e333-47b3-8e23-164b5b856222";
        String state="0";
        String Index="1";
        String pageSize="5";
        String count=null;
        System.out.println(getApasInfoListByIdCardAndUser("http://222.143.21.80/luyi/lwservices/service",idCard,userid,state,Index,pageSize,count));
    }*/

    /**
     * 调用人社生物认证接口
     * @param key
     * @param jsonStr
     * @return
     */
    public  static String callBiometricAuthenticationService(String key,String jsonStr){
        String rst = null;
        LOG.info("callBiometricAuthenticationService params: key={},jsonStr={}",key,jsonStr);
        final Service service = new Service();
        try {
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(batWsUrl));
            call.setOperationName(new QName("http://webService.eway.com/", "saveFaceVfyResult"));
            call.addParameter( "key", XMLType.XSD_DATE, ParameterMode.IN);// 接口的参数
            call.addParameter( "data", XMLType.XSD_DATE, ParameterMode.IN);// 接口的参数
            call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
            call.setUseSOAPAction(false);
            //call.setSOAPActionURI("http://webService.eway.com/saveFaceVfyResult");
            call.setReturnType(XMLType.XSD_STRING); 	// 返回值类型：String
            String result = (String) call.invoke(new Object[] {key,jsonStr});
            // 给方法传递参数，并且调用方法
            System.out.println("result is " + result);
            LOG.info("callEmploymentService result={}",result);
            return  result;
        } catch (Exception e) {
            LOG.error("接口调用错误",e);
        }
        return null;
    }


    /**
     * 获取办理事项列表
     * @param idCard
     * @param userid
     * @param state
     * @param index
     * @param pageSize
     * @param count
     * @return
     */
    public  static String getApasInfoListByIdCardAndUser(String url,String idCard,String userid,String state,String index,String pageSize,String count){
        LOG.info("getApasInfoListByIdCardAndUser params: idCard={},userid={},state={},index={},pageSize={},count={}",idCard,userid,state,index,pageSize,count);
        final Service service = new Service();
        try {
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(url));
            // getApasInfoListByIdCardAndUse
            call.setOperationName(new QName("http://server.webservice.com/", "getApasInfoListByIdCard"));
            //idCard,userid,state,index,pageSize,count
            call.addParameter( "in0", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
            call.addParameter( "in1", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
            call.addParameter( "in2", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
            call.addParameter( "in3", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
            call.addParameter( "in4", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数
            //call.addParameter( "in5", XMLType.XSD_STRING, ParameterMode.IN);// 接口的参数

            call.setReturnType(XMLType.XSD_STRING);// 设置返回类型
            call.setUseSOAPAction(false);
            String result = (String) call.invoke(new Object[] {idCard,state,index,pageSize,count});
            // 给方法传递参数，并且调用方法
            LOG.info("getApasInfoListByIdCardAndUser result={}",result);
            return  result;
        } catch (Exception e) {
            LOG.error("接口调用错误",e);
        }
        return null;
    }


}

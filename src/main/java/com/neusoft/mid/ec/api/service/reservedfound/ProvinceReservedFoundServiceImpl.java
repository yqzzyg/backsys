package com.neusoft.mid.ec.api.service.reservedfound;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.neusoft.mid.ec.api.serviceInterface.reservedfound.ProvinceReservedFoundService;


/**
 * 省直公积金查询
 * @author dev
 *
 */
@Service
public class ProvinceReservedFoundServiceImpl implements ProvinceReservedFoundService {

    private Logger LOG = LoggerFactory.getLogger(ProvinceReservedFoundServiceImpl.class);

    @Value("${province.query.url}")
    private String url;

    
    /**
     * 省直公积金查询
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String getBasicInfo(String IDNo, String name, String biz_type) throws Exception {
		LOG.info("省直公积金查询getBasicInfo request >>IDNo={},name={},biz_type={}",IDNo,name,biz_type);

        Map<String,String> paraMap = new HashMap(3);
        paraMap.put("zjhm", IDNo);
        paraMap.put("xingming", name);
        paraMap.put("biz_type", biz_type);
        paraMap.put("url", url);
        String result = this.doQuery(paraMap);
        LOG.info("获取省直公积金查询信息返回值getBasicInfo result = {}",result);

        return  result;
	}
	/**
	 * 调用webservice
	 * @return
	 * @throws Exception 
	 */
	public String doQuery( Map<String,String> map) throws Exception{
		String result=null;
		String xingming=map.get("xingming");
	    String zjhm=map.get("zjhm");
	    String bizType=map.get("biz_type");
	    String url=map.get("url");
	    try {
			
	  //字符集
		String encodingStyle = "utf-8";
		//WSDL的地址
        String endpoint = url;  
        String targetNamespace = "http://commonwebservice.monitor.app.hafmis.neusoft.com";
        String method = "psnInfoQry";
        //调用接口的参数的名字zjhm xingming bizType
        String[] paramNames = {"zjhm","xingming","bizType"};
        //调用接口的参数的值
        String[] paramValues = {zjhm,xingming,bizType};
       
        org.apache.axis.client.Service service = new org.apache.axis.client.Service();  
        Call call = (Call) service.createCall(); 
//        call.setTimeout(new Integer(20000));  //设置超时时间
        call.setTargetEndpointAddress(new java.net.URL(endpoint));  //设置目标接口的地址
        call.setEncodingStyle(encodingStyle);//设置传入服务端的字符集格式如utf-8等
        call.setOperationName(new QName(targetNamespace,method));// 具体调用的方法名，可以由接口提供方告诉你，也可以自己从WSDL中找  
        call.setUseSOAPAction(true); 
        call.addParameter(new QName(targetNamespace,paramNames[0]),  
                org.apache.axis.encoding.XMLType.XSD_STRING,  
                javax.xml.rpc.ParameterMode.IN);// 接口的参数  
        call.addParameter(new QName(targetNamespace,paramNames[1]),  
                org.apache.axis.encoding.XMLType.XSD_STRING,  
                javax.xml.rpc.ParameterMode.IN);// 接口的参数  
        call.addParameter(new QName(targetNamespace,paramNames[2]),  
                org.apache.axis.encoding.XMLType.XSD_STRING,  
                javax.xml.rpc.ParameterMode.IN);// 接口的参数  
       call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型  ，如String
       // call.setReturnClass(java.lang.String[].class); //返回字符串数组类型
        // 给方法传递参数，并且调用方法 ，如果无参，则new Obe 
         result = (String) call.invoke(new Object[] {paramValues[0],paramValues[1],paramValues[2]}); 
        // 打印返回值
        System.out.println("result is " + result.toString());  
	    } catch (Exception e) {  
	       e.printStackTrace();
	    }  

		return result;
    }

}

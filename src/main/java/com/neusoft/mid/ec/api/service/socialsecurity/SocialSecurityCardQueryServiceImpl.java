package com.neusoft.mid.ec.api.service.socialsecurity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.neusoft.mid.ec.api.domain.AdressDict;
import com.neusoft.mid.ec.api.exception.GeneralException;
import com.neusoft.mid.ec.api.serviceInterface.socialsecurity.SocialSecurityCardQueryService;
import com.neusoft.mid.ec.api.shareplatform.ServiceInvocation;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;
import me.puras.common.json.Response;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

import static com.neusoft.mid.ec.api.shareplatform.ServiceInvocation.buildrsBaseHeader;

/**
 * 社保卡
 */
@Service
public class SocialSecurityCardQueryServiceImpl implements SocialSecurityCardQueryService {

    @Value("${ssc.username}")
    private String username;

    @Value("${ssc.password}")
    private String password;

    @Value("${ssc.url}")
    private String url;
    @Value("${person.url}")
    private String personurl;
    @Value("${inured.url}")
    private String inuredurl;
    @Value("${account.url}")
    private String accounturl;
    @Value("${pay.url}")
    private String payurl;
    @Value("${treatment.url}")
    private String treatmenturl;
    @Value("${lost.url}")
    private String losturl;
    @Value("${rs.url}")
    private String rsurl;

    @Value("${ssc.token}")
    private String token;
    /*@Autowired
    private AdressDictRepository adressDictRepository;*/

    private Logger LOG = LoggerFactory.getLogger(SocialSecurityCardQueryServiceImpl.class);

    /**
     *   <ser:user>$username</ser:user>
     <ser:pass>$password</ser:pass>
     <ser:aaz500>$cardNo</ser:aaz500>
     <ser:aac002>$IDNo</ser:aac002>
     <ser:aac003>$name</ser:aac003>
     <ser:aab301>$cityCode</ser:aab301>
     * @return
     */
    private String getCardPackage(String IDNo,String name){
        try {
            String packageXml = IOUtils.toString(SocialSecurityCardQueryServiceImpl.class.getClassLoader().getResourceAsStream("config/ssc/getCard.xml"),"utf-8");
            packageXml = packageXml.replaceAll("\\$username",username).replaceAll("\\$password",password)
                    .replaceAll("\\$IDNo",IDNo).replaceAll("\\$name",name);
            LOG.info("package xml={}",packageXml);
            return packageXml;
        } catch (IOException e) {
            LOG.warn("读取错误",e);
        }
        return "";
    }
    /**
     *   <ser:user>$username</ser:user>
     <ser:pass>$password</ser:pass>
     <ser:aaz500>$cardNo</ser:aaz500>
     <ser:aac002>$IDNo</ser:aac002>
     <ser:aac003>$name</ser:aac003>
     <ser:aab301>$cityCode</ser:aab301>
     * @return
     */
    private String getCardBasicInfoPackage(String IDNo,String name){
        try {
            String packageXml = IOUtils.toString(SocialSecurityCardQueryServiceImpl.class.getClassLoader().getResourceAsStream("config/ssc/getCardBasicInfo.xml"),"utf-8");
            packageXml = packageXml.replaceAll("\\$username",username).replaceAll("\\$password",password)
                    .replaceAll("\\$IDNo",IDNo).replaceAll("\\$name",name);
            LOG.info("package xml={}",packageXml);
            return packageXml;
        } catch (IOException e) {
            LOG.warn("读取错误",e);
        }
        return "";
    }
    /**
     * 卡状态查询
     * @param
     * @param IDNo
     * @param name
     */
    @Override
    public String getCard(String IDNo,String name) throws GeneralException, IOException, JDOMException {

        LOG.info("getCard request >>IDNo={},name={}",IDNo,name);

        Map headerParams = new HashMap(1);
        headerParams.put("Authorization","bearer " + token);

        String result = HttpRequestUtil.URLPostXmlParams(url, getCardPackage(IDNo, name), headerParams);
        LOG.info("getCard result = {}",result);

        if(result == null){
            throw new GeneralException("查询接口出错");
        }

        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(new ByteArrayInputStream(result.getBytes("utf-8")));

        //4.获取根节点
        Element rootElement = document.getRootElement();

        System.out.println(rootElement.getName());
        //5.获取子节点
        List<Element> children = rootElement.getChildren();
        for (Element child : children) {
            List<Element> childrenList = child.getChildren();
            for (Element o : childrenList) {
                if("getCard01Response".equals(o.getName())){
                    return o.getValue();
                }
            }
        }
        return  "";
    }


    /**
     * 人员基本信息查询
     * @param
     * @param
     * @param
     */
    @Override
    public String getPeron(Map params,Map<String, Object> mapHeaders) throws Exception{

        LOG.info("人员基本信息查询 request >>params={}",params);
        String name = params.get("name").toString();
        String account = params.get("account").toString();
        //获取accessToken
        String accessToken =  ServiceInvocation.getrToken(rsurl,name,account);
        mapHeaders.put("Authorization","bearer " + accessToken);
        String result =  HttpRequestUtil.URLGet(personurl,params,"utf-8",mapHeaders);
        LOG.info("getPeron result = {}",result);
        return  result;
    }


    /**
     * 人员参保信息查询
     * @param
     * @param
     * @param
     */
    @Override
    public String getinuredInfo(Map params,Map<String, Object> mapHeaders) throws Exception {
        LOG.info("人员参保信息查询 request >>params={}",params);
        String name = params.get("name").toString();
        String account = params.get("account").toString();
        //获取accessToken
        String accessToken =  ServiceInvocation.getrToken(rsurl,name,account);
       LOG.info("获取token信息："+accessToken);
        mapHeaders.put("Authorization","Bearer " + accessToken);
        String result =  HttpRequestUtil.URLGet(inuredurl,params,"utf-8",mapHeaders);
        LOG.info("getinuredInfo result = {}",result);
        return  result;
    }

    /**
     * 养老账户信息查询
     * @param
     * @param
     * @param
     */
    @Override
    public String getaccountInfo (Map params,Map<String, Object> mapHeaders) throws Exception {

        LOG.info("养老账户信息查询 request >>params={}",params);
        String name = params.get("name").toString();
        String account = params.get("account").toString();
        //获取accessToken
        String accessToken =  ServiceInvocation.getrToken(rsurl,name,account);
        mapHeaders.put("Authorization","bearer " + accessToken);
        String result =  HttpRequestUtil.URLGet(accounturl,params,"utf-8",mapHeaders);
        LOG.info("getaccountInfo result = {}",result);

        return  result;
    }

   /* private String getresult(String dictcode,String dictvalue) {
        AdressDict adressDict=new AdressDict();
        adressDict.setDictcode(dictcode);
        adressDict.setDictvalue(dictvalue);
        AdressDict selectvalue = adressDictRepository.selectvalue(adressDict);
        String dictname = selectvalue.getDictname();
        return dictname;
    }*/


    /**
     * 个人缴费明细查询
     * @param
     * @param
     * @param
     */
    @Override
    public String getpayInfo (Map params,Map<String, Object> mapHeaders) throws Exception {

        LOG.info("个人缴费明细查询 request >>params={}",params);
        String name = params.get("name").toString();
        String account = params.get("account").toString();
        //获取accessToken
        String accessToken =  ServiceInvocation.getrToken(rsurl,name,account);
        mapHeaders.put("Authorization","bearer " + accessToken);
        String result =  HttpRequestUtil.URLGet(payurl,params,"utf-8",mapHeaders);
        LOG.info("getpayInfo result = {}",result);
        List<JSONObject> resultList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray list = JSONObject.parseArray(jsonObject.get("list")==null?"[]":jsonObject.get("list").toString());
        if (CollectionUtils.isNotEmpty(list)) {
            for (Object obj:list) {
            JSONObject dataObj = JSONObject.parseObject(obj.toString());
                dataObj.put("payRate",String.format("%.3f",dataObj.get("payRate")));
                dataObj.put("payAmount",String.format("%.2f",dataObj.get("payAmount")));
                resultList.add(dataObj);
            }
            jsonObject.put("list",resultList);
        }
        String results = jsonObject.toJSONString();
        return  results;
    }


    /**
     * 养老待遇发放查询
     * @param
     * @param
     * @param
     */
    @Override
    public String getoldInfo (Map params,Map<String, Object> mapHeaders) throws Exception {

        LOG.info("养老待遇发放查询 request >>params={}",params);
        String name = params.get("name").toString();
        String account = params.get("account").toString();
        //获取accessToken
        String accessToken =  ServiceInvocation.getrToken(rsurl,name,account);
        mapHeaders.put("Authorization","bearer " + accessToken);
        String result =  HttpRequestUtil.URLGet(treatmenturl,params,"utf-8",mapHeaders);
        LOG.info("getoldInfo result = {}",result);

        return  result;
    }

    /**
     * 失业待遇发放查询
     * @param
     * @param
     * @param
     */
    @Override
    public String getlostInfo (Map params,Map<String, Object> mapHeaders) throws Exception {

        LOG.info("失业待遇发放查询 request >>params={}",params);
        String name = params.get("name").toString();
        String account = params.get("account").toString();
        //获取accessToken
        String accessToken =  ServiceInvocation.getrToken(rsurl,name,account);
        mapHeaders.put("Authorization","bearer " + accessToken);
        String result =  HttpRequestUtil.URLGet(losturl,params,"utf-8",mapHeaders);
        LOG.info("getlostInfo result = {}",result);
        return  result;
    }
    /**
     * 获取社保卡基础信息
     */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getCardBasicInfo(String IDNo, String name) throws Exception {
		 LOG.info("getCardBasicInfo request >>IDNo={},name={}",IDNo,name);

	        Map headerParams = new HashMap(1);
	        headerParams.put("Authorization","bearer " + token);

	        String result = HttpRequestUtil.URLPostXmlParams(url, getCardBasicInfoPackage(IDNo, name), headerParams);
	        LOG.info("获取社保卡基本信息返回值getCardBasicInfo result = {}",result);

	        if(result == null){
	            throw new GeneralException("查询接口出错");
	        }

	        SAXBuilder saxBuilder = new SAXBuilder();
	        Document document = saxBuilder.build(new ByteArrayInputStream(result.getBytes("utf-8")));

	        //4.获取根节点
	        Element rootElement = document.getRootElement();

	        LOG.info("获取社保卡基本信息返回值rootElement：",rootElement.getName());
	        //5.获取子节点
	        List<Element> children = rootElement.getChildren();
	        for (Element child : children) {
	            List<Element> childrenList = child.getChildren();
	            for (Element o : childrenList) {
	                if("getRysjResponse".equals(o.getName())){
	                    return o.getValue();
	                    
	                }
	            }
	        }
	        return  "";
	}



}

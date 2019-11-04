package com.neusoft.mid.ec.api.controller.country.civiladministration;


import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.service.civiladministration.QueryBook.QueryBookMarryService;
import com.neusoft.mid.ec.api.service.civiladministration.QueryBook.QueryBookMarryServicePortType;
import com.neusoft.mid.ec.api.util.MD5Util;

import me.puras.common.json.Response;
import net.sf.json.util.JSONUtils;


/**
 * 民政业务
 */
@RestController
@RequestMapping("/country/civiladministration")
public class CountryCivilAdministrationController extends BaseController {


    @Autowired
    private Environment environment;
    private Map<String, Object> areaMap = new HashMap<String, Object>();



    /**
     *预约信息查询(预结、 预离)
     * @param params
     * @return
     */
    @RequestMapping(value = "/queryBookMarryInfo", method = RequestMethod.POST)
    public Response queryBookMarryInfo(@RequestParam Map<String, Object> params, HttpServletRequest request) {
    	logger.info("预约信息查询接口[queryBookMarryInfo]入参" + params);
        Response<Object> object = new Response<>();

        try {
            if(null==params.get("certNoMan")||null==params.get("certNoWoman")){
                object.setCode(1);
                object.setDescription("证件号码不能为空!");
                return object;
            }
            String applyKey = getApplyKeyStr(params);
            String urlStr = environment.getProperty("mz.apply.url");
            URL url = new URL(urlStr+"queryBookMarryInfo?wsdl");
            QueryBookMarryService bookMarryService = new QueryBookMarryService(url);
            QueryBookMarryServicePortType qbmspt
                    = bookMarryService.getQueryBookMarryServicePort();
            String queryBookMarryInfo = qbmspt.queryBookMarryInfo(applyKey , params.get("opType").toString(),
                    params.get("certNoMan").toString(), params.get("certNoWoman").toString());
            logger.info("调用预约信息查询到的数据：" + queryBookMarryInfo);
            if (StringUtils.isNotBlank(queryBookMarryInfo)) {
                JSONObject json = JSONObject.parseObject(queryBookMarryInfo);
                //如果回传数据成功取出data
                if ("0".equals(json.getString("returnCode"))) {
                    if(json.get("returnMessage") != null){
                        if(JSONUtils.isString(json.get("returnMessage"))){
                            object.setDescription(json.get("returnMessage").toString());
                        }else{
                            object.setPayload(json.get("returnMessage"));
                            object.setDescription("获取预约信息查询成功!");
                        }
                    }else{
                        object.setDescription("获取预约信息查询成功!");
                    }
                    object.setCode(0);
                }else if("01".equals(json.getString("returnCode"))){
                    object.setCode(-1);
                    object.setDescription("没有查到相关信息!");
                }else{
                    if(JSONUtils.isString(json.get("returnMessage"))){
                        object.setDescription(json.get("returnMessage").toString());
                    }else{
                        object.setDescription("获取预约信息查询失败!");
                        object.setPayload(json.get("returnMessage"));
                    }
                    object.setCode(-1);
                }

            } else {
                logger.info("调用预约信息查询接口异常返回值为空,入参为：[{}]", params);
                object.setCode(500);
                object.setDescription("内部服务错误");
            }
            object.setLastUpdateTime(System.currentTimeMillis());
        } catch (Exception e) {
            logger.info("调用预约信息查询接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }

        return object;
    }



    //获取公钥生成私钥方法
    private String getApplyKeyStr(Map paramMap) {
        String privateCode = "";

        if(paramMap != null && paramMap.containsKey("opType") ){
            String opType = paramMap.get("opType").toString();
            String key = environment.getProperty("mz.apply.key");
            privateCode = MD5Util.string2MD5(key+opType);
        }else if(paramMap != null && paramMap.containsKey("jsonStr")){
            String opType = paramMap.get("jsonStr").toString();
            String key = environment.getProperty("mz.apply.key");
            privateCode = MD5Util.string2MD5(key+opType);
        }

        return privateCode;
    }
}

package com.neusoft.mid.ec.api.controller.householdadministration;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.domain.RequestInfo;
import com.neusoft.mid.ec.api.serviceInterface.householdadministration.HouseholdAdministrationApplyService;
import com.neusoft.mid.ec.api.util.*;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;
import com.neusoft.mid.ec.api.util.http.WebServiceClientUtil;
import me.puras.common.json.Response;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 户政服务查询
 */
@RestController
@RequestMapping("/hz/query")
public class HouseholdAdministrationQueryController extends BaseController {

    @Autowired
    private Environment environment;
    @Autowired
    private HouseholdAdministrationApplyService service;

    @Autowired
    private WebServiceClientUtil webServiceClientUtil;
    @Autowired
    private JedisClusterUtil util;

    /**
     * 新生儿查询
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/xsexx", method = RequestMethod.POST)
    public Response hzXsexx(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("新生儿查询入参数据：" + params);
            RequestInfo requestInfo = getRequestInfo(request);
            //redis缓存获取
            String userId = util.get("gonganUserId", requestInfo.getIdno());
            logger.info("redis取出的用户id==============" + userId);
            params.put("userguid", userId);
            if (null == params.get("csdq") || StringUtils.isBlank(String.valueOf(params.get("csdq")))) {
                logger.info("新生儿查询接口，csdq为空,");
                params.put("csdq", "hn");
            }
            //入参校验
            if (null == params.get("userguid") || StringUtils.isBlank(String.valueOf(params.get("userguid")))) {
                logger.info("新生儿查询接口入参userguid为空");
                return errorRsponse(object, 500701, "申请人id不能为空！");
            }
            if (null == params.get("csdq") || StringUtils.isBlank(String.valueOf(params.get("csdq")))) {
                logger.info("新生儿查询接口，csdq为空,");
                return errorRsponse(object, 500701, "新生儿查询接口，csdq不能为空！");
            }
            if ("zj".equals(String.valueOf(params.get("csdq"))) && (null == params.get("skey") || StringUtils.isBlank(String.valueOf(params.get("skey"))))) {
                logger.info("新生儿查询接口新生儿地区为浙江省(zj)时，skey为空,");
                return errorRsponse(object, 500701, "新生儿地区为浙江省(zj)时，skey不能为空！");
            }
            //参数封装，调用服务查询数据
            Map<String, Object> mapHeaders = getMapHeaders(request);
            //初始化token
            mapHeaders.put("token", service.initToken(request));
            String urlPath = environment.getProperty("hz.query.url.xsexx");
            logger.info("调用公安接口地址：" + urlPath);
            String resultStr = HttpRequestUtil.URLPost(urlPath, params, mapHeaders);
            logger.info("调用公安接口查询到的数据：" + resultStr);
            if (StringUtils.isNotBlank(resultStr)) {
                JSONObject json = JSONObject.parseObject(resultStr);
                //如果回传数据成功取出data
                if ("true".equals(json.getString("succ"))) {
                    //模拟数据 start，生产一定要修改
                    JSONArray arr = json.getJSONArray("data");
                    for (int i = 0; i < arr.size(); i++) {
                        //校验是否为目前关系

                        //上线需要修改
                        if (requestInfo.getIdno().equals(arr.getJSONObject(i).get("mqsfzh"))) {
                            arr.getJSONObject(i).put("ysqrgx_dm", "52");
                        } else {
                            arr.getJSONObject(i).put("ysqrgx_dm", "51");
                        }
                    }
                    //模拟数据 end，生产一定要修改
                    object.setPayload(json.get("data"));
                } else {
                    //获取code值，如果msg为空，根据code值匹配对应的msg值
                    object.setCode(json.getInteger("code"));
                }
                String s = json.getString("msg");
                if (StringUtils.isBlank(s)) {
                    object.setDescription(HouseholdAdministrationApplyController.codeMap().get(json.getInteger("code")));
                } else {
                    object.setDescription(s);
                }
            } else {
                logger.info("调用户政接口异常返回值为空,入参为：[{}]", params);
                object.setCode(500);
                object.setDescription("内部服务错误");
            }
            object.setLastUpdateTime(System.currentTimeMillis());
            //object = xserTest();
        } catch (Exception e) {
            logger.info("调用户政接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        logger.info("新生儿查询出参：" + object);
        return object;
    }

    public Response errorRsponse(Response obj, int code, String msg) {
        obj.setCode(code);
        obj.setLastUpdateTime(System.currentTimeMillis());
        obj.setDescription(msg);
        logger.info("户政查询参数检验为空打印：" + obj);
        return obj;
    }


    /**
     * 保安员成绩查询
     */
    @RequestMapping("/getSecurityScores")
    public Response getSecurityScores(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("调用保安员成绩查询入参" + params);
            //入参校验
            if (null == params || params.size() == 0) {
                logger.info("调用保安员成绩查询接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if (null == params.get("idCard") || StringUtils.isBlank(String.valueOf(params.get("idCard")))) {
                logger.info("调用保安员成绩查询接口入参idCard为空");
                return errorRsponse(object, 1, "身份证号不能为空！");
            }
            //获取headers请求头数据
            Map<String, Object> mapHeaders = getMapHeaders(request);
            //初始化token
            mapHeaders.put("token", service.initToken(request));
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.getSecurityScores");
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            if (null != params.get("idCard") && 18 >= params.get("idCard").toString().length()) {
                params.put("idCard", SM4Utils.encryptSm4Cbc(params.get("idCard").toString()));
            }
            if (null == params.get("timeStamp")) {
                params.put("timeStamp", Date2TampsUtil.dateFormat());
            }
            //调用公安厅接口
            httpHzPost(urlPath, params, request, object, "1");
            //敏感信息加密
            changXM(object);
        } catch (Exception e) {
            logger.error("调用保安员成绩查询接口调用异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        logger.info("调用保安员成绩查询接口出参：" + object);
        return object;
    }

    /**
     * 保安员进度查询
     */
    @RequestMapping("/getSecurityProgress")
    public Response getSecurityProgress(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("调用保安员进度查询入参" + params);
            //入参校验
            if (null == params || params.size() == 0) {
                logger.info("调用保安员进度接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if (null == params.get("idCard") || StringUtils.isBlank(String.valueOf(params.get("idCard")))) {
                logger.info("调用保安员进度接口入参idCard为空");
                return errorRsponse(object, 1, "身份证号不能为空！");
            }
            String urlPath = environment.getProperty("hz.query.url.getSecurityProgress");
            if (null != params.get("idCard") && 18 >= params.get("idCard").toString().length()) {
                params.put("idCard", SM4Utils.encryptSm4Cbc(params.get("idCard").toString()));
            }
            if (null == params.get("timeStamp")) {
                params.put("timeStamp", Date2TampsUtil.dateFormat());
            }
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            httpHzPost(urlPath, params, request, object, "1");
            //敏感信息加密
            changXM(object);
        } catch (Exception e) {
            logger.error("调用保安员进度查询接口调用异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        logger.info("调用保安员进度查询出参" + object);
        return object;
    }

    //查询敏感信息脱敏
    private void changXM(Response<Object> object) {
        if (null != object.getPayload()) {
            List<Map<String, Object>> payload = (List<Map<String, Object>>) object.getPayload();
            for (int i = 0; i < payload.size(); i++) {
                String xm = String.valueOf(payload.get(i).get("xm"));
                xm = xm.substring(1);
                StringBuffer SB = new StringBuffer("*");
                SB.append(xm);
                payload.get(i).put("xm", SB.toString());
            }
        }
    }


    /**
     * 身份证办理进度查询
     */
    @RequestMapping("/getIdCardProgress")
    public Response getIdCardProgress(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("调用身份证办理进度查询入参" + params);
            //入参校验
            if (null == params || params.size() == 0) {
                logger.info("调用身份证办理进度接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if (null == params.get("idCard") || StringUtils.isBlank(String.valueOf(params.get("idCard")))) {
                logger.info("调用身份证办理进度接口入参idCard为空");
                return errorRsponse(object, 1, "身份证号不能为空！");
            }
            if (null != params.get("idCard") && 18 >= params.get("idCard").toString().length()) {
                params.put("idCard", SM4Utils.encryptSm4Cbc(params.get("idCard").toString()));
            }
            if (null == params.get("timeStamp")) {
                params.put("timeStamp", Date2TampsUtil.dateFormat());
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.getIdCardProgress");
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            httpHzPost(urlPath, params, request, object, "1");
        } catch (Exception e) {
            logger.error("调用身份证办理进度查询接口调用异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        logger.info("调用身份证办理进度查询出参" + object);
        return object;
    }

    /**
     * 获取市机构代码
     */
    @RequestMapping("/getResidentsCityDept")
    public Response getResidentsCityDept(HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            Map params = new HashMap();
            logger.info("调用获取市机构代码入参" + params);
            if (null == params.get("timeStamp")) {
                params.put("timeStamp", Date2TampsUtil.dateFormat());
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.getResidentsCityDept");
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            httpHzPost(urlPath, params, request, object, "2");
        } catch (Exception e) {
            logger.error("调用获取市机构代码接口调用异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        logger.info("调用获取市机构代码出参" + object);
        return object;
    }

    /**
     * 获取下级机构代码
     */
    @RequestMapping("/getResidentsBureauDept")
    public Response getResidentsBureauDept(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("调用获取下级机构代码入参" + params);
            //入参校验
            if (null == params || params.size() == 0) {
                logger.info("调用获取下级机构代码入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if (null == params.get("cityId") || StringUtils.isBlank(String.valueOf(params.get("cityId")))) {
                logger.info("调用获取下级机构代码入参cityId为空");
                return errorRsponse(object, 1, "上级机构代码不能为空！");
            }
            if (null == params.get("timeStamp")) {
                params.put("timeStamp", Date2TampsUtil.dateFormat());
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.getResidentsBureauDept");
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            httpHzPost(urlPath, params, request, object, "2");
        } catch (Exception e) {
            logger.error("调用获取下级机构代码接口调用异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        logger.info("调用获取下级机构代码出参" + object);
        return object;
    }

    /**
     * 同名查询接口
     */
    @RequestMapping("/getSameName")
    public Response getSameName(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("调用同名查询接口入参" + params);
            //入参校验
            if (null == params || params.size() == 0) {
                logger.info("调用同名查询接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if (null == params.get("deptId") || StringUtils.isBlank(String.valueOf(params.get("deptId")))) {
                logger.info("调用同名查询接口入参deptId为空");
                return errorRsponse(object, 1, "机构代码不能为空！");
            }
            if (null == params.get("name") || StringUtils.isBlank(String.valueOf(params.get("name")))) {
                logger.info("调用同名查询接口入参name为空");
                return errorRsponse(object, 1, "姓名不能为空！");
            }
            if (null == params.get("timeStamp")) {
                params.put("timeStamp", Date2TampsUtil.dateFormat());
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.getSameName");
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            httpHzPost(urlPath, params, request, object, "2");
        } catch (Exception e) {
            logger.error("调用同名查询接口接口调用异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        logger.info("调用同名查询接口出参" + object);
        return object;
    }

    /**
     * 事项列表_户政
     */
    @RequestMapping("/mattersList")
    public Response mattersList(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("事项列表接口[mattersList]入参" + params);
            //入参校验
            if (null == params || params.size() == 0) {
                logger.info("调用事项列表接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if (null == params.get("caseKindType") || StringUtils.isBlank(String.valueOf(params.get("caseKindType")))) {
                logger.info("调用事项列表接口入参caseKindType为空");
                return errorRsponse(object, 250303, "事项类型不能为空！");
            }
            if (null == params.get("casePoliceCategory") || StringUtils.isBlank(String.valueOf(params.get("casePoliceCategory")))) {
                logger.info("调用事项列表接口入参casePoliceCategory为空");
                return errorRsponse(object, 250304, "警种不能为空！");
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.mattersList");
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            httpHzGet(urlPath, params, request, object);
        } catch (Exception e) {
            logger.error("[mattersList]事项列表接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        logger.info("事项列表接口[mattersList]出参" + object);
        return object;
    }

    /**
     * 办事指南_户政
     */
    @RequestMapping("/handlingGuide")
    public Response handlingGuide(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("办事指南接口[mattersList]入参" + params);
            //入参校验
            if (null == params || params.size() == 0) {
                logger.info("调用办事指南接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if (null == params.get("case_guid") || StringUtils.isBlank(String.valueOf(params.get("case_guid")))) {
                logger.info("调用办事指南接口入参case_guid为空");
                return errorRsponse(object, 1, "事项编号不能为空！");
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.mattersList");
            //url地址请求拼接l
            StringBuffer stb = new StringBuffer(urlPath);
            stb.append("/");
            stb.append(String.valueOf(params.get("case_guid")));
            urlPath = stb.toString();
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            httpHzGet(urlPath, params, request, object);
        } catch (Exception e) {
            logger.error("[mattersList]办事指南接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        logger.info("办事指南接口[mattersList]出参" + object);
        return object;
    }

    /**
     * 查看附件
     */
    @RequestMapping(value = "/selectEnclosure", method = RequestMethod.POST)
    public Response selectEnclosure(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("查看附件接口[mattersList]入参" + params);
            //入参校验
            if (null == params || params.size() == 0) {
                logger.info("调用查看附件接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if (null == params.get("att_guid") || StringUtils.isBlank(String.valueOf(params.get("att_guid")))) {
                logger.info("调用查看附件接口入参att_guid为空");
                return errorRsponse(object, 1, "附件编号不能为空！");
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.selectEnclosure");
            //url地址请求拼接
            StringBuffer stb = new StringBuffer(urlPath);
            stb.append("/");
            stb.append(String.valueOf(params.get("att_guid")));
            urlPath = stb.toString();
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            httpHzGet(urlPath, params, request, object);
        } catch (Exception e) {
            logger.error("[mattersList]查看附件接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }


    /**
     * 查询统一封装
     * post
     *
     * @param params
     * @param request
     * @param object
     */
    public void httpHzPost(String urlPath, Map params, HttpServletRequest request, Response<Object> object, String flag) {
        //获取headers请求头数据
        Map<String, Object> mapHeaders = getMapHeaders(request);
        //初始化token
        mapHeaders.put("token", service.initToken(request));
        //调用公安厅接口
        String result = HttpRequestUtil.URLPost(urlPath, params, mapHeaders);
        logger.info("调用公安接口出参" + result);
        if (StringUtils.isNotBlank(result)) {
            JSONObject json = JSONObject.parseObject(result);
            //如果回传数据成功取出data
            if ("成功".equals(json.getString("msg")) && "1".equals(flag)) {
                try {
                    JSONArray resultData = JSON.parseArray(SM4Utils.decryptSm4Cbc(json.get("resultData").toString()));
                    object.setPayload(resultData);
                } catch (Exception e) {
                    logger.error("解密失败", e);
                }
            } else if ("成功".equals(json.getString("msg")) && "2".equals(flag)) {
                object.setPayload(json.get("resultData"));
            }
            //获取code值，如果msg为空，根据code值匹配对应的msg值
            object.setCode(json.getInteger("errCode"));
            String s = json.getString("msg");
            if ("".equals(s) || StringUtils.isEmpty(s)) {
                object.setDescription(HouseholdAdministrationApplyController.codeMap().get(json.getInteger("errCode")));
            } else {
                object.setDescription(s);
            }

        } else {
            logger.info("调用户政接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        object.setLastUpdateTime(System.currentTimeMillis());
    }

    /**
     * 查询统一封装
     * get
     *
     * @param params
     * @param request
     * @param object
     */
    public void httpHzGet(String urlPath, Map params, HttpServletRequest request, Response<Object> object) {
        //获取headers请求头数据
        Map<String, Object> mapHeaders = getMapHeaders(request);
        //初始化token
        mapHeaders.put("token", service.initToken(request));
        //调用公安厅接口
        String result = HttpRequestUtil.URLGet(urlPath, params, "utf-8", mapHeaders);
        logger.info("调用公安厅接口返回" + result);
        if (StringUtils.isNotBlank(result)) {
            JSONObject json = JSONObject.parseObject(result);
            //如果回传数据成功取出data
            if ("true".equals(json.getString("succ"))) {
                try {
                    object.setPayload(json.get("data"));
                } catch (Exception e) {
                    logger.error("解密失败", e);
                }
            } else {
                //获取code值，如果msg为空，根据code值匹配对应的msg值
                object.setCode(json.getInteger("code"));
            }
            String s = json.getString("msg");
            if ("".equals(s) || StringUtils.isEmpty(s)) {
                if (StringUtils.isBlank(json.getString("code"))) {

                    object.setDescription(HouseholdAdministrationApplyController.codeMap().get(json.getInteger("errCode")));
                } else {
                    object.setDescription(HouseholdAdministrationApplyController.codeMap().get(json.getInteger("code")));

                }
            } else {
                object.setDescription(s);
            }
        } else {
            logger.info("调用户政接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        object.setLastUpdateTime(System.currentTimeMillis());
    }

    /**
     * 类别字典
     */
    @RequestMapping(value = "/dict", method = RequestMethod.POST)
    public Response dict(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("类别字典接口[dict]入参" + params);
            //入参校验
            if (null == params || params.size() == 0) {
                logger.info("调用类别字典接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if (null == params.get("dict_key") || StringUtils.isBlank(String.valueOf(params.get("dict_key")))) {
                logger.info("调用类别字典接口入参dict_key为空");
                return errorRsponse(object, 1, "字典类别不能为空！");
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.dict");
            //url地址请求拼接
            StringBuffer stb = new StringBuffer(urlPath);
            stb.append("/");
            stb.append(String.valueOf(params.get("dict_key")));
            urlPath = stb.toString();
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            httpHzGet(urlPath, params, request, object);
        } catch (Exception e) {
            logger.error("[dict]类别字典接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

    /**
     * 字典值获取
     */
    @RequestMapping(value = "/dictMsg", method = RequestMethod.POST)
    public Response dictMsg(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("字典值获取[dictMsg]入参" + params);
            //入参校验
            if (null == params || params.size() == 0) {
                logger.info("调用字典值获取接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if (null == params.get("dict_key") || StringUtils.isBlank(String.valueOf(params.get("dict_key")))) {
                logger.info("调用字典值获取接口入参dict_key为空");
                return errorRsponse(object, 1, "字典类别不能为空！");
            }
            if (null == params.get("key") || StringUtils.isBlank(String.valueOf(params.get("key")))) {
                logger.info("调用字典值获取接口入参key为空");
                return errorRsponse(object, 1, "字典key不能为空！");
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.dict");
            //url地址请求拼接
            StringBuffer stb = new StringBuffer(urlPath);
            stb.append("/");
            stb.append(String.valueOf(params.get("dict_key")));
            stb.append("/");
            if ("0".equals(String.valueOf(params.get("key"))) && "MZ".equals(String.valueOf(params.get("dict_key")))) {
                stb.append("01");//模拟汉族，生产一定要修改。
            } else {
                stb.append(String.valueOf(params.get("key")));
            }
            urlPath = stb.toString();
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            httpHzGet(urlPath, params, request, object);
        } catch (Exception e) {
            logger.error("[dictMsg]字典值获取接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        logger.info("字典值获取[dictMsg]出参" + object);
        return object;
    }


    /**
     * 用户获取
     */
    @RequestMapping(value = "/uerMsg", method = RequestMethod.POST)
    public Response uerMsg(HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            Map params = new HashMap();
            logger.info("用户获取[uerMsg]入参" + params);
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.user");
            //url地址请求拼接
            StringBuffer stb = new StringBuffer(urlPath);
            stb.append("/");
            RequestInfo requestInfo = getRequestInfo(request);
            if (StringUtils.isBlank(requestInfo.getIdno())) {
                requestInfo.setIdno("412727198901170879");
            }
            if (StringUtils.isBlank(requestInfo.getName())) {
                requestInfo.setName("陈龙飞");
            }
            stb.append(requestInfo.getIdno());
            urlPath = stb.toString();
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            httpHzGet(urlPath, params, request, object);
            if (0 != object.getCode()) {
                return object;
            }
            //添加测试数据
            //object = PressureTest();
            String payload = String.valueOf(object.getPayload());
            JSONObject jsonObject = JSONObject.parseObject(payload);
            String guid = jsonObject.getString("guid");
            logger.info("用户获取接口获取的用户id===========" + guid);
            //放入redis缓存
            util.setWithExpireTime("gonganUserId", requestInfo.getIdno(), guid, 60 * 5);
            //敏感信息处理
            //姓名脱敏
            String name = requestInfo.getName().substring(1);
            StringBuffer SB = new StringBuffer("*");
            SB.append(name);
            jsonObject.put("name", SB.toString());
            //身份证信息
            if (requestInfo.getIdno().length() == 18) {
                String s = requestInfo.getIdno().substring(16, 17);
                String[] split = {"1", "3", "5", "7", "9"};
                jsonObject.put("sex", "2");
                for (int i = 0; i < split.length; i++) {
                    if (s.equals(split[i])) {
                        jsonObject.put("sex", "1");
                    }
                }
                jsonObject.put("idno", ValidateUtils.idMask(requestInfo.getIdno(), 4, 4));
            } else {
                errorRsponse(object, 1, "身份证格式错误");
            }

            object.setPayload(jsonObject);
        } catch (Exception e) {
            logger.error("[uerMsg]用户获取接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setPayload(null);
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        logger.info("用户获取[uerMsg]出参" + object);
        return object;
    }

    /**
     * 市局列表
     */
    @RequestMapping(value = "/cityMsg", method = RequestMethod.POST)
    public Response cityMsg(HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            Map params = new HashMap();
            logger.info("市局列表[cityMsg]入参" + params);
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.city");
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            httpHzGet(urlPath, params, request, object);
        } catch (Exception e) {
            logger.error("[cityMsg]市局列表接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        logger.info("市局列表[cityMsg]出参" + object);
        return object;
    }


    /**
     * 分局列表
     */
    @RequestMapping(value = "/downMsg", method = RequestMethod.POST)
    public Response downMsg(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("分局列表[downMsg]入参" + params);
            //入参校验
            if (null == params || params.size() == 0) {
                logger.info("调用分局列表接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if (null == params.get("city_guid") || StringUtils.isBlank(String.valueOf(params.get("city_guid")))) {
                logger.info("调用分局列表接口入参city_guid为空");
                return errorRsponse(object, 1, "市局代码不能为空！");
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.cityguid");
            //url地址请求拼接
            StringBuffer stb = new StringBuffer(urlPath);
            stb.append("/");
            stb.append(params.get("city_guid"));
            urlPath = stb.toString();
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            httpHzGet(urlPath, null, request, object);
        } catch (Exception e) {
            logger.error("[downMsg]户政列表接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        logger.info("分局列表[downMsg]出参" + object);
        return object;
    }


    /**
     * 派出所列表
     */
    @RequestMapping(value = "/pliceMsg", method = RequestMethod.POST)
    public Response pliceMsg(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("派出所列表查询[pliceMsg]入参" + params);
            //入参校验
            if (null == params || params.size() == 0) {
                logger.info("调用派出所列表查询入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if (null == params.get("bureau_guid") || StringUtils.isBlank(String.valueOf(params.get("bureau_guid")))) {
                logger.info("调用派出所列表查询入参bureau_guid为空");
                return errorRsponse(object, 1, "分局代码不能为空！");
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.pliceguid");
            //url地址请求拼接
            StringBuffer stb = new StringBuffer(urlPath);
            stb.append("/");
            stb.append(params.get("bureau_guid"));
            urlPath = stb.toString();
            logger.info("调用公安接口地址：" + urlPath);
            //调用公安厅接口
            httpHzGet(urlPath, params, request, object);
        } catch (Exception e) {
            logger.error("[pliceMsg]派出所列表接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        logger.info("派出所列表查询[pliceMsg]出参" + object);
        return object;
    }


    /**
     * 办理事项查询
     */
    @RequestMapping(value = "/eventQuery", method = RequestMethod.POST)
    public Response eventQuery(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("办理事项查询[eventQuery]入参" + params);
            String idCard = request.getHeader("idno");
            String userid = request.getHeader("yunzheng");
            String state = null == params.get("state") ? "0" : params.get("state").toString();
            String index = null == params.get("index") ? "1" : params.get("index").toString();
            String pageSize = null == params.get("pageSize") ? "5" : params.get("pageSize").toString();
            String count = null == params.get("count") ? null : params.get("count").toString();
            //入参校验
            if (null == params || params.size() == 0) {
                logger.info("办理事项查询查询入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }

            if (StringUtils.isBlank(userid)) {
                logger.info("办理事项查询入参idCard和userid为空");
                return errorRsponse(object, 1, "用户ID不能为空！");
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("hz.query.url.eventQuery");
            logger.info("云政办理事项查询接口地址：" + urlPath);
            logger.info("云政办理事项{getApasInfoListByIdCardAndUse}查询入参 idCard={},userid={},state={},index={},pageSize={},count={}", idCard, userid, state, index, pageSize, count);
            String xmlList = webServiceClientUtil.getApasInfoListByIdCardAndUser(urlPath, idCard, userid, state, index, pageSize, count);
            object.setLastUpdateTime(System.currentTimeMillis());
            if ("Null".equals(xmlList)) {
                return errorRsponse(object, 1, "未查询到数据");
            }
            if ("所传参数不为整数!".equals(xmlList)) {
                return errorRsponse(object, 1, xmlList);
            }
            object.setCode(0);
            object.setDescription("success");
            object.setPayload(XmlJsonUtil.xml2json(xmlList));
        } catch (Exception e) {
            logger.error("办理事项查询[eventQuery]接口异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        logger.info("办理事项查询[eventQuery]出参" + object);
        return object;
    }

    //压力测试接口
    private Response PressureTest() {
        Date startDate = new Date();
        Response<Object> response = new Response<>();
        response.setCode(0);
        response.setDescription("调用成功");
        response.setLastUpdateTime(new Date().getTime());
        JSONObject object = new JSONObject();
        object.put("pcsBelongCityID", "411400");
        object.put("orgName", "商丘市公安局");
        object.put("isConfirm", "0");
        object.put("pcsBelongAreaID", "411403");
        object.put("pcsOrgId", "411403110000");
        object.put("guid", "2684112d-e0d1-4a6e-a6a0-3c466c887ba0");
        object.put("pcsOrgName", "李庄乡派出所");
        object.put("mz", "01");
        object.put("userName", "17788122077");
        object.put("orgId", "411400");
        object.put("pcsBelongCity", "商丘市");
        object.put("pcsBelongArea", "河南省商丘市睢阳区");
        response.setPayload(object);
        Date endDate = new Date();
        Long time = (endDate.getTime() - startDate.getTime());
        logger.info("测试耗费时间是 ：" + time + "毫秒");
        return response;
    }

    //新生儿测试接口
    private Response xserTest() {
        Date startDate = new Date();
        Response<Object> response = new Response<>();
        response.setCode(0);
        response.setDescription("调用成功");
        response.setLastUpdateTime(new Date().getTime());
        JSONObject object = new JSONObject();
        object.put("qfrq", "20161106");
        object.put("mqmz", 1);
        object.put("xsecsd", "浙江省金华市东阳市");
        object.put("xsexb", 2);
        object.put("jtlx", 0);
        object.put("xsemz", 0);
        object.put("fqmz", 1);
        object.put("blzt", -1);
        object.put("mqxm", "孙玉敏");
        object.put("mqsfzh", "412728199004181843");
        object.put("xsexm", "陈桐瑶");
        object.put("fqsfzh", "412727198901170879");
        object.put("fqxm", "陈龙飞");
        object.put("ywbs", "98156744-2ffe-45d4-8b6c-d5f657651d3c");
        object.put("ysqrgx_dm", "51");
        object.put("yljgmc", "东阳市横店医院");
        object.put("cszbh", "P330646318");
        object.put("xsecsrq", "20161101");
        response.setPayload(object);
        Date endDate = new Date();
        Long time = (endDate.getTime() - startDate.getTime());
        logger.info("测试耗费时间是 ：" + time + "毫秒");
        return response;
    }
}



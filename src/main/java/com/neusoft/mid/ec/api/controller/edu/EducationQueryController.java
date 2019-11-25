package com.neusoft.mid.ec.api.controller.edu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.domain.UserLog;
import com.neusoft.mid.ec.api.exception.GeneralException;
import com.neusoft.mid.ec.api.util.*;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;
import me.puras.common.json.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 教育
 */
@RestController
@RequestMapping("/edu/query")
public class EducationQueryController extends BaseController {

    @Autowired
    private Environment environment;
    //签名key
    private static final String SIGN = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCzkWazkpkoRxo7buRLbWTzLc";
    
    /**
     * 普通话成绩查询接口
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPthcj", method = RequestMethod.POST)
    public Response getPthcj(@RequestBody Map params, HttpServletRequest request, HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        verifyCode(request,response, String.valueOf(params.get("validateCode")),null);
        try {
        	//新增支付宝姓名和身份证号Start
        	//获取header数据
        	Map<String, Object> mapHeaders = getMapHeaders(request);
        	//支付宝身份证信息
            String idno = (String) mapHeaders.get("idno");
            //支付宝姓名信息
            String name = (String) mapHeaders.get("name");
            //验证支付宝信息是否为空
            if(StringUtils.isBlank(idno)||StringUtils.isBlank(name)) {
            	object.setCode(2);
                object.setDescription("内部错误:支付宝信息为空");
                return object;
            }
            //将支付宝信息放入参数
            params.put("idno",idno);
            params.put("name",name);
            //新增支付宝姓名和身份证号end
            logger.info("普通话成绩查询接口[getPthcj]入参" + params);
            object.setLastUpdateTime(System.currentTimeMillis());
            //姓名参数由name变更为username
            if (StringUtils.isBlank(params.get("username")==null?null:params.get("username").toString())) {
                object.setCode(2);
                object.setDescription("姓名不能为空");
                return object;
            }
          //身份证号参数由idno变更为idCard
            if (StringUtils.isBlank(params.get("idCard")==null?null:params.get("idCard").toString()) &&
                    StringUtils.isBlank(params.get("zkzh")==null?null:params.get("zkzh").toString())) {
                object.setCode(2);
                object.setDescription("身份证或准考证号二选一不能为空");
                return object;
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("edu.query.url.getPthcj");
            httpEduPost(urlPath,params,request,object,1);
        } catch (Exception e) {
            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }

    /**
     * 学位证书认证查询接口
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDegreeCertificate", method = RequestMethod.POST)
    public Response getDegreeCertificate(@RequestBody Map params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        verifyCode(request,response,String.valueOf(params.get("validateCode")),null);
        try {
            logger.info("学位证书认证查询接口[getDegreeCertificate]入参" + params);
            if (StringUtils.isBlank(params.get("appNum")==null?null:params.get("appNum").toString())) {
                object.setCode(2);
                object.setDescription("认证编号不能为空");
                return object;
            }
            if (StringUtils.isBlank(params.get("repNum")==null?null:params.get("repNum").toString())) {
                object.setCode(2);
                object.setDescription("报告编号不能为空");
                return object;
            }
            if (StringUtils.isBlank(params.get("idCard")==null?null:params.get("idCard").toString())) {
                object.setCode(2);
                object.setDescription("身份证号不能为空");
                return object;
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("edu.query.url.getDegreeCertificate");
            httpEduPost(urlPath,params,request,object,1);
        } catch (Exception e) {
            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }

    /**
     * 小学学校名查询接口
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/xxshCheck", method = RequestMethod.POST)
    public Response xxshCheck(@RequestBody Map params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        try {
            logger.info("小学学校名查询接口[xxshCheck]入参" + params);
            if (StringUtils.isBlank(params.get("schoolName")==null?null:params.get("schoolName").toString())) {
                object.setCode(2);
                object.setDescription("小学学校名称不能为空");
                return object;
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("edu.query.url.xxshCheck");
            httpEduPost(urlPath,params,request,object,2);
        } catch (Exception e) {
            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }

    /**
     * 普通高中学校名单查询接口
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/ptgzCheck", method = RequestMethod.POST)
    public Response ptgzCheck(@RequestBody Map params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        try {
            logger.info("普通高中学校名单查询接口[ptgzCheck]入参" + params);
            if (StringUtils.isBlank(params.get("schoolName")==null?null:params.get("schoolName").toString())) {
                object.setCode(2);
                object.setDescription("学校名称不能为空");
                return object;
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("edu.query.url.ptgzCheck");
            httpEduPost(urlPath,params,request,object,2);
        } catch (Exception e) {
            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }

    /**
     * 幼儿园学校名单查询接口
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/yeyshCheck", method = RequestMethod.POST)
    public Response yeyshCheck(@RequestBody Map params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        try {
            logger.info("幼儿园学校名单查询接口[yeyshCheck]入参" + params);
            if (StringUtils.isBlank(params.get("schoolName")==null?null:params.get("schoolName").toString())) {
                object.setCode(2);
                object.setDescription("幼儿园名称不能为空");
                return object;
            }
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("edu.query.url.yeyshCheck");
            httpEduPost(urlPath,params,request,object,2);
        } catch (Exception e) {
            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }

    /**
     * 查询统一封装
     * get
     *
     * @param params
     * @param request
     * @param object
     */
    public void httpEduPost(String urlPath, Map params, HttpServletRequest request, Response<Object> object,Integer flag) {
    	try {
	        //获取headers请求头数据
	        Map<String, Object> mapHeaders = getMapHeaders(request);
	        List list = new ArrayList();
	        if(StringUtils.isBlank(params.get("sname") == null?null:params.get("sname").toString())&&
	        		StringUtils.isBlank(params.get("rcode") == null?null:params.get("rcode").toString())) {
		        if (StringUtils.isBlank(params.get("name")==null?null:params.get("name").toString())) {
		        	params.put("name",URLDecoder.decode((String)mapHeaders.get("name"), "UTF-8"));
		        }
		        if (StringUtils.isBlank(params.get("idno")==null?null:params.get("idno").toString())) {
		            params.put("idno",mapHeaders.get("idno"));
		        }
		        if (StringUtils.isBlank(params.get("name")==null?null:params.get("name").toString())) {
		            params.put("name","ceshi");
		        }
		        if (StringUtils.isBlank(params.get("idno")==null?null:params.get("idno").toString())) {
		            params.put("idno","410000000000000000");
		        }
	        }
	        if(StringUtils.isNotBlank(params.get("ysbCheckFlag")==null?null:params.get("ysbCheckFlag").toString())) {
	        	params.remove("ysbCheckFlag");
	        	list.add(params);
	        	mapHeaders.put("sign",MD5Util.MD5(JSON.toJSONString(list)+"CB8E078B9175C81DEBC985DDEB708E9B54F586A536E6397876F2D9B018C533C3"));
	        }else {
	        	list.add(params);
	        	mapHeaders.put("sign",MD5Util.MD5(JSON.toJSONString(list)+SIGN));
	        }
	        logger.info("读取到教育接口地址：" + urlPath);
	        String resultStr = HttpRequestUtil.URLPostJSONParams(urlPath, JSON.toJSONString(list), mapHeaders);
	        logger.info("调用教育接口查询到的数据：" + resultStr);
	        if (StringUtils.isNotBlank(resultStr)) {
	            JSONObject json = JSONObject.parseObject(resultStr);
	            //获取code值，如果msg为空，根据code值匹配对应的msg值
	            if (1==json.getInteger("code")) {
	                object.setDescription("请求失败");
	            }else if (0==json.getInteger("code") && "失败".equals(json.getString("msg"))) {
	                object.setDescription("查询成功");
	            }else{
	                object.setDescription(json.getString("msg"));
	            }
	            object.setCode(json.getInteger("code"));
	            json.remove("code");
	            json.remove("msg");
	            //如果回传数据成功取出data
	            object.setPayload(flag==1?json.get("content"):json);
	        } else {
	            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
	            object.setCode(500);
	            object.setDescription("内部服务错误");
	        }
	        object.setLastUpdateTime(System.currentTimeMillis());
    	} catch (Exception e) {
			object.setCode(-1);
			object.setDescription("请求失败");
			object.setLastUpdateTime(System.currentTimeMillis());
		}
    }
    
    /**
     * get查询content-type = application/json
     *
     * @param params
     * @param request
     * @param object
     */
    public void httpEduGet(String urlPath, Map params, HttpServletRequest request, Response<Object> object) {
    	try {
	        //获取headers请求头数据
	        Map<String, Object> mapHeaders = getMapHeaders(request);
	        if (StringUtils.isBlank(params.get("name")==null?null:params.get("name").toString())) {
	        	params.put("name",URLDecoder.decode((String)mapHeaders.get("name"), "UTF-8"));
	        }
	        if (StringUtils.isBlank(params.get("idno")==null?null:params.get("idno").toString())) {
	            params.put("idno",mapHeaders.get("idno"));
	        }
	        mapHeaders.put("sign",MD5Util.MD5(JSON.toJSONString(params)+SIGN));
	        logger.info("读取到教育接口地址：" + urlPath);
	        String resultStr = HttpRequestUtil.URLGet2(urlPath, params, "UTF-8", mapHeaders);
	        logger.info("调用教育接口查询到的数据：" + resultStr);
	        if (StringUtils.isNotBlank(resultStr)) {
	            JSONObject json = JSONObject.parseObject(resultStr);
	            //获取code值，如果msg为空，根据code值匹配对应的msg值
	            if (1==json.getInteger("code")) {
	                object.setDescription("请求失败");
	            }else if (0==json.getInteger("code") && "失败".equals(json.getString("msg"))) {
	                object.setDescription("查询成功");
	            }else{
	                object.setDescription(json.getString("msg"));
	            }
	            object.setCode(json.getInteger("code"));
	            json.remove("code");
	            json.remove("msg");
	            //如果回传数据成功取出data
	            object.setPayload(json);
	        } else {
	            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
	            object.setCode(500);
	            object.setDescription("内部服务错误");
	        }
	        object.setLastUpdateTime(System.currentTimeMillis());
    	} catch (Exception e) {
			object.setCode(-1);
			object.setDescription("请求失败");
			object.setLastUpdateTime(System.currentTimeMillis());
		}
    }

    /**
     * 招收国际留学生资格高等学校名单查询
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/zswglxscheck/getInfo")
    public Response zswglxscheckGetInfo(@RequestBody Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        if (StringUtils.isBlank(params.get("schoolName")==null?null:params.get("schoolName").toString())) {
            object.setCode(2);
            object.setDescription("学校名称不能为空");
            return object;
        }
        String urlPath = environment.getProperty("edu.query.url.zswglxscheck.getInfo");
        return doExecute("招收国际留学生资格高等学校名单查询", urlPath, params, request);
    }

    /**
     * 中外及港澳台合作办学机构名单查询
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/zwhzbxjgxx/getInfo")
    public Response zwhzbxjgxxGetInfo(@RequestBody Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        if (StringUtils.isBlank(params.get("dwmc")==null?null:params.get("dwmc").toString())) {
            object.setCode(2);
            object.setDescription("单位名称不能为空");
            return object;
        }
        if (StringUtils.isBlank(params.get("wfzhzz")==null?null:params.get("wfzhzz").toString())) {
            object.setCode(1);
            object.setDescription("外方合作者不能为空");
            return object;
        }
        String urlPath = environment.getProperty("edu.query.url.zwhzbxjgxx.getInfo");
        return doExecute("中外及港澳台合作办学机构名单查询", urlPath, params, request);
    }

    /**
     * 海外孔子学院名单查询
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/hwkzxyxx/getInfo")
    public Response hwkzxyxxGetInfo(@RequestBody Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        if (StringUtils.isBlank(params.get("kymc")==null?null:params.get("kymc").toString())) {
            object.setCode(2);
            object.setDescription("孔子学院名称不能为空");
            return object;
        }
        if (StringUtils.isBlank(params.get("gnhzjg")==null?null:params.get("gnhzjg").toString())) {
            object.setCode(1);
            object.setDescription("国内合作机构不能为空");
            return object;
        }
        String urlPath = environment.getProperty("edu.query.url.hwkzxyxx.getInfo");
        return doExecute("海外孔子学院名单查询", urlPath, params, request);
    }

    /**
     * 初级中学学校名单查询
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/czmd/getInfo")
    public Response czmdGetInfo(@RequestBody Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        if (StringUtils.isBlank(params.get("schoolName")==null?null:params.get("schoolName").toString())) {
            object.setCode(2);
            object.setDescription("学校名称不能为空");
            return object;
        }
        String urlPath = environment.getProperty("edu.query.url.czmd.getInfo");
        return doExecute("初级中学学校名单查询", urlPath, params, request);
    }

    /**
     * 中等专业学校名单查询
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/zzmd/getInfo")
    public Response zzmdGetInfo(@RequestBody Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        if (StringUtils.isBlank(params.get("schoolName")==null?null:params.get("schoolName").toString())) {
            object.setCode(2);
            object.setDescription("学校名称不能为空");
            return object;
        }
        String urlPath = environment.getProperty("edu.query.url.zzmd.getInfo");
        return doExecute("中等专业学校名单查询", urlPath, params, request);
    }

    /**
     * 国家公派出国留学录取信息查询
     *
     * @return
     */
    @PostMapping("/gjgpcglxmd/getInfo")
    public Response  gjgpcglxmdGetInfo(@RequestBody Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        if (StringUtils.isBlank(params.get("name")==null?null:params.get("name").toString())) {
            object.setCode(2);
            object.setDescription("姓名不能为空");
            return object;
        }
        if (StringUtils.isBlank(params.get("workUnit")==null?null:params.get("workUnit").toString())) {
            object.setCode(1);
            object.setDescription("工作单位不能为空");
            return object;
        }
        String urlPath = environment.getProperty("edu.query.url.gjgpcglxmd.getInfo");
        return doExecute("国家公派出国留学录取信息查询", urlPath, params, request);
    }

    /**
     * 特岗教师服务期满证书查询
     *
     * @return
     */
    @PostMapping("/teacherZigeCheck/getInfo")
    public Response  teacherZigeCheckGetInfo(@RequestBody Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        verifyCode(request,response,String.valueOf(params.get("validateCode")),null);
        if (StringUtils.isBlank(params.get("name")==null?null:params.get("name").toString())) {
            object.setCode(2);
            object.setDescription("姓名不能为空");
            return object;
        }
        if (StringUtils.isBlank(params.get("idno")==null?null:params.get("idno").toString())) {
            object.setCode(1);
            object.setDescription("身份证号不能为空");
            return object;
        }
        String urlPath = environment.getProperty("edu.query.url.teacherZigeCheck.getInfo");
        return doExecute("特岗教师服务期满证书查询", urlPath, params, request);
    }

    /**
     * 民办普通高校名单信息查询
     *
     * @return
     */
    @PostMapping("/mbptgxCheck/getInfo")
    public Response  mbptgxCheckGetInfo(@RequestBody Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        if (StringUtils.isBlank(params.get("schoolName")==null?null:params.get("schoolName").toString())) {
            object.setCode(2);
            object.setDescription("学校名称不能为空");
            return object;
        }
        String urlPath = environment.getProperty("edu.query.url.mbptgxCheck.getInfo");
        return doExecute("民办普通高校名单信息查询", urlPath, params, request);
    }

    /**
     * 中外及与港澳台合作办学项目名单信息查询
     *
     * @return
     */
    @PostMapping("/zwjygathzbxxmCheck/getInfo")
    public Response  zwjygathzbxxmGetInfo(@RequestBody Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        if (StringUtils.isBlank(params.get("dwmc")==null?null:params.get("dwmc").toString())) {
            object.setCode(2);
            object.setDescription("单位名称不能为空");
            return object;
        }
        if (StringUtils.isBlank(params.get("wfhzz")==null?null:params.get("wfhzz").toString())) {
            object.setCode(2);
            object.setDescription("外方合作者不能为空");
            return object;
        }
        String urlPath = environment.getProperty("edu.query.url.zwjygathzbxxmCheck.getInfo");
        return doExecute("中外及与港澳台合作办学项目名单信息查询", urlPath, params, request);
    }


    /**
     * 普通高招成绩查询
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/queryPZCJ")
    public Response queryPZCJ(@RequestBody Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        verifyCode(request,response,String.valueOf(params.get("validateCode")),null);
        String urlPath = environment.getProperty("edu.query.url.queryPZCJ");
        Map<String, Object> headMap = getMapHeaders(request);
        String refererUrlPath = environment.getProperty("edu.query.url.queryPZCJ.referer");
        headMap.put("Referer", refererUrlPath);
        Response<Object> o = doExecute2("普通高招成绩查询", urlPath, params, headMap);
        return doExecute2("普通高招成绩查询", urlPath, params, headMap);
    }

    /**
     * 普通高招录取查询
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/queryPZL")
    public Response queryPZL(@RequestBody Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        verifyCode(request,response,String.valueOf(params.get("validateCode")),null);
        String urlPath = environment.getProperty("edu.query.url.queryPZL");
        Map<String, Object> headMap = getMapHeaders(request);
        String refererUrlPath = environment.getProperty("edu.query.url.queryPZL.referer");
        headMap.put("Referer", refererUrlPath);
        return doExecute2("普通高招录取查询", urlPath, params, headMap);
    }

    /**
     * @param params
     * @param
     * @param
     */
    @SuppressWarnings({ "rawtypes" })
    public Response doExecute2(String functionName, String urlPath, Map<String, Object> params, Map<String, Object> headMap) {
        Response<Object> object = new Response<>();
        logger.info(functionName + "入参：" + params);
        if (StringUtils.isBlank(params.get("KSH")==null?null:params.get("KSH").toString())) {
            object.setCode(2);
            object.setDescription("考生号不能为空");
            return object;
        }
        if((StringUtils.isBlank(params.get("BMXH")==null?null:params.get("BMXH").toString())
                && StringUtils.isBlank(params.get("SFZH")==null?null:params.get("SFZH").toString()))){
            object.setCode(2);
            object.setDescription("报名序号和身份证号二选一不能为空");
            return object;
        }
        try {
            // 接口调用
            String response = HttpRequestUtil.URLPost(urlPath, params, headMap);
            // 调用结果
            JSONObject jsonObj = new JSONObject();
            if (StringUtils.isNotBlank(response)) {
                Document doc = Jsoup.parse(response);//解析HTML字符串返回一个Document实现
                Elements links = doc.select("tr");
                logger.info("调用第三方接口出参："+links.toString());
                if (0<links.size()) {
                    for (Element src:links) {
                        String s = StringUtils.deleteWhitespace(src.select("td[class=tdXH]").text());
                        jsonObj.put(getKey(s),src.select("td[class=tdXX]").text());
                    }
                    object.setCode(0);
                    object.setDescription("成功");
                }else{
                    object.setCode("未查询到相关信息".equals(doc.select("div[class=Container]").text())?0:1);
                    if("考生号格式错误。".equals(doc.select("div[class=Container]").text())){
                        object.setDescription("准考证号格式错误。");
                    }else if("考生号格式错误。 报名序号格式错误。".equals(doc.select("div[class=Container]").text())){
                        object.setDescription("准考证号格式错误、报名序号格式错误。");
                    }else if("考生号格式错误。 身份证格式错误。".equals(doc.select("div[class=Container]").text())){
                        object.setDescription("准考证号格式错误、身份证格式错误。");
                    }else{
                        object.setDescription(doc.select("div[class=Container]").text());
                    }
                }
                object.setPayload(jsonObj);
            } else {
                logger.info(functionName + "调用第三方异常出参：" + response);
                object.setCode(0);
                object.setDescription("未查询到相关信息");
                object.setPayload(jsonObj);
            }
        } catch (Exception e) {
            logger.info(functionName + "异常：" + params, e);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        object.setLastUpdateTime(System.currentTimeMillis());
        logger.info(functionName + "出参：" + object);
        return object;
    }
    private String getKey(String str){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("身份证号","SFZH");
        jsonObj.put("姓名","XM");
        jsonObj.put("报名序号","BMXH");
        jsonObj.put("考生号","KSH");
        jsonObj.put("数学","SXCJ");
        jsonObj.put("听力","TLCJ");
        jsonObj.put("外语","WYCJ");
        jsonObj.put("语文","YWCJ");
        jsonObj.put("综合","ZHCJ");
        jsonObj.put("总分","ZFCJ");
        jsonObj.put("录取院校","LUYX");
        return jsonObj.get(str)==null?null:jsonObj.getString(str);
    }
    /**
     * @param params
     * @param request
     * @param
     */
    @SuppressWarnings({ "rawtypes"})
    public Response doExecute(String functionName, String urlPath, Map<String, Object> params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        logger.info(functionName + "入参：" + params);
        try {
            // headMap
            Map<String, Object> mapHeaders = getMapHeaders(request);
            if (StringUtils.isBlank(params.get("name")==null?null:params.get("name").toString())) {
                params.put("name",mapHeaders.get("name"));
            }
            if (StringUtils.isBlank(params.get("idno")==null?null:params.get("idno").toString())) {
                params.put("idno",mapHeaders.get("idno"));
            }
            if (StringUtils.isBlank(params.get("name")==null?null:params.get("name").toString())) {
                params.put("name","ceshi");
            }
            if (StringUtils.isBlank(params.get("idno")==null?null:params.get("idno").toString())) {
                params.put("idno","410000000000000000");
            }
            String paramsStr = JSON.toJSONString(params);
            List list = new ArrayList();
            list.add(params);
            mapHeaders.put("sign",MD5Util.MD5(JSON.toJSONString(list)+SIGN));
            // 接口调用
            String response = HttpRequestUtil.URLPostJSONParams(urlPath, JSON.toJSONString(list), mapHeaders);
            // 调用结果
            if (StringUtils.isNotBlank(response)) {
                logger.info(functionName + "调用第三方出参：" + response);
                JSONObject json = JSONObject.parseObject(response);
                String code = json.getString("code");
                String msg = json.getString("msg");
                msg = StringUtils.isBlank(msg)?"":msg;
                if ("1".equals(code)) {
                    object.setDescription("请求失败");
                }else if ("0".equals(code) && "失败".equals(msg)) {
                    object.setDescription("查询成功");
                }else{
                    object.setDescription(msg);
                }
                object.setCode(Integer.valueOf(code));
                json.remove("code");
                json.remove("msg");
                if("没有你要的查询结果!".equals(json.get("content")==null?"":json.get("content"))){
                    object.setDescription("未查询到结果");
                    json.put("content",null);
                    object.setPayload(json);
                }else{
                    object.setPayload(json);
                }
            } else {
                logger.info(functionName + "调用第三方异常出参：" + response);
                object.setCode(500);
                object.setDescription("内部服务错误");
            }
        } catch (Exception e) {
            logger.error(functionName + "异常：" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        long endTime = System.currentTimeMillis();
        object.setLastUpdateTime(endTime);
        logger.info(functionName + "出参：" + object);
        return object;
    }
    /**
     * 2.7.	特岗教师笔试成绩结果查询
     * @param params
     * @param request
     * @param response
     * @return
     * @throws GeneralException
     */
    @RequestMapping(value = "/writtenTestResults", method = RequestMethod.POST)
    public Response writtenTestResults(@RequestBody(required=false) Map params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        //验证码验证
//        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        try {
            logger.info("特岗教师笔试成绩结果查询接口[writtenTestResults]入参" + params);
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("edu.query.url.writtenTestResults");
            httpEduPost(urlPath,params == null?new HashMap():params,request,object,1);
        } catch (Exception e) {
            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }
    /**
     * 2.8.	特岗教师面试人员名单
     * @param params
     * @param request
     * @param response
     * @return
     * @throws GeneralException
     */
    @RequestMapping(value = "/interview", method = RequestMethod.POST)
    public Response interview(@RequestBody(required=false) Map params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
      //验证码验证
//        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        try {
            logger.info("特岗教师面试人员名单接口[interview]入参" + params);
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("edu.query.url.interview");
            httpEduPost(urlPath,params == null?new HashMap():params,request,object,2);
        } catch (Exception e) {
            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }
    /**
     * 2.9.	特岗教师面试成绩结果查询
     * @param params
     * @param request
     * @param response
     * @return
     * @throws GeneralException
     */
    @RequestMapping(value = "/interviewscore", method = RequestMethod.POST)
    public Response interviewscore(@RequestBody(required=false) Map params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        //验证码验证
//        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        try {
            logger.info("特岗教师面试成绩结果查询接口[interviewscore]入参" + params);
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("edu.query.url.interviewscore");
            httpEduPost(urlPath,params == null?new HashMap():params,request,object,1);
        } catch (Exception e) {
            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }
    /**
     * 2.10. 特岗教师体检人员名单
     * @param params
     * @param request
     * @param response
     * @return
     * @throws GeneralException
     */
    @RequestMapping(value = "/examination", method = RequestMethod.POST)
    public Response examination(@RequestBody(required=false) Map params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        //验证码验证
//        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        try {
            logger.info("特岗教师体检人员名单接口[examination]入参" + params);
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("edu.query.url.examination");
            httpEduPost(urlPath,params == null?new HashMap():params,request,object,2);
        } catch (Exception e) {
            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }
    /**
     * 2.11. 特岗教师体检结果查询
     * @param params
     * @param request
     * @param response
     * @return
     * @throws GeneralException
     */
    @RequestMapping(value = "/examinationresult", method = RequestMethod.POST)
    public Response examinationresult(@RequestBody(required=false) Map params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        //验证码验证
//        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        try {
            logger.info("特岗教师体检结果查询接口[examinationresult]入参" + params);
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("edu.query.url.examinationresult");
            httpEduPost(urlPath,params == null?new HashMap():params,request,object,1);
        } catch (Exception e) {
            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }
    /**
     * 2.12. 特岗教师拟聘用人员名单
     * @param params
     * @param request
     * @param response
     * @return
     * @throws GeneralException
     */
    @RequestMapping(value = "/teacherinvite", method = RequestMethod.POST)
    public Response teacherinvite(@RequestBody(required=false) Map params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        //验证码验证
//        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        try {
            logger.info("特岗教师拟聘用人员名单接口[teacherinvite]入参" + params);
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("edu.query.url.teacherinvite");
            httpEduPost(urlPath,params == null?new HashMap():params,request,object,2);
        } catch (Exception e) {
            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }
    /**
     * 2.14. 河南省中等职业教育学历认证查询
     * @param params
     * @param request
     * @param response
     * @return
     * @throws GeneralException
     */
    @RequestMapping(value = "/ysbCheck", method = RequestMethod.POST)
    public Response ysbCheck(@RequestBody(required=false) Map params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        //验证码验证
//        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        try {
            logger.info("河南省中等职业教育学历认证查询接口[ysbCheck]入参" + params);
            params.put("ysbCheckFlag", "1");
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("edu.query.url.ysb");
            httpEduPost(urlPath,params == null?new HashMap():params,request,object,1);
        } catch (Exception e) {
            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }
    /**
     * 3.2.	特岗教师人员名单公用接口：获取县区编码（特岗教师）
     * @param params
     * @param request
     * @param response
     * @return
     * @throws GeneralException
     */
    @RequestMapping(value = "/county", method = RequestMethod.POST)
	public Response county(@RequestBody(required=false) Map params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        //验证码验证
//        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        try {
            logger.info("特岗教师人员名单公用接口：获取县区编码（特岗教师）接口[county]入参" + "");
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("edu.query.url.county");
            httpEduGet(urlPath, params == null?new HashMap():params, request, object);
        } catch (Exception e) {
            logger.info("调用教育接口异常返回值为空,入参为：[{}]", "");
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }
    /**
     * 3.3.	特岗教师人员名单公用接口：获取学科编码（特岗教师）
     * @param params
     * @param request
     * @param response
     * @return
     * @throws GeneralException
     */
    @RequestMapping(value = "/subject", method = RequestMethod.POST)
    public Response subject(@RequestBody(required=false) Map params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        //验证码验证
//        verifyCode(request,response,String.valueOf(params.get("validateCode")),"page");
        try {
            logger.info("特岗教师人员名单公用接口：获取学科编码（特岗教师）接口[subject]入参" + params);
            //获取配置文件中的 url地址
            String urlPath = environment.getProperty("edu.query.url.subject");
            httpEduGet(urlPath, params == null?new HashMap():params, request, object);
        } catch (Exception e) {
            logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }
}

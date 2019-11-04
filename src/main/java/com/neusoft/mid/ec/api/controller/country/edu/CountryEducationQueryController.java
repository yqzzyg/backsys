package com.neusoft.mid.ec.api.controller.country.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.exception.GeneralException;
import com.neusoft.mid.ec.api.util.MD5Util;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;

import me.puras.common.json.Response;

/**
 * 教育
 */
@RestController
@RequestMapping("/country/edu/query")
public class CountryEducationQueryController extends BaseController {

	@Autowired
	private Environment environment;
	// 签名key
	private static final String SIGN = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCzkWazkpkoRxo7buRLbWTzLc";

	/**
	 * 普通话成绩查询接口
	 * 
	 * @param params
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getPthcj", method = RequestMethod.POST)
	public Response getPthcj(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		Response<Object> object = new Response<>();
		try {
			logger.info("普通话成绩查询接口[getPthcj]入参" + params);
			object.setLastUpdateTime(System.currentTimeMillis());
			 if (StringUtils.isBlank(params.get("name")==null?null:params.get("name").toString())) {
	                object.setCode(2);
	                object.setDescription("姓名不能为空");
	                return object;
	         }
	         if (StringUtils.isBlank(params.get("idno")==null?null:params.get("idno").toString()) &&
	                    StringUtils.isBlank(params.get("zkzh")==null?null:params.get("zkzh").toString())) {
	                object.setCode(2);
	                object.setDescription("身份证或准考证号二选一不能为空");
	                return object;
	         }
			// 获取配置文件中的 url地址
			String urlPath = environment.getProperty("edu.query.url.getPthcj");
			httpEduPost(urlPath, params, request, object, 1);
		} catch (Exception e) {
			logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
			object.setCode(500);
			object.setDescription("内部服务错误");
		}
		return object;
	}

	/**
	 * 幼儿园学校名单查询接口
	 * 
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/yeyshCheck", method = RequestMethod.POST)
	public Response yeyshCheck(@RequestParam Map<String, Object> params,  HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		Response<Object> object = new Response<>();
		try {
			logger.info("幼儿园学校名单查询接口[yeyshCheck]入参" + params);
			if (StringUtils.isBlank(params.get("schoolName") == null ? null : params.get("schoolName").toString())) {
				object.setCode(2);
				object.setDescription("幼儿园名称不能为空");
				return object;
			}
			// 获取配置文件中的 url地址
			String urlPath = environment.getProperty("edu.query.url.yeyshCheck");
			httpEduPost(urlPath, params, request, object, 2);
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
	public Response xxshCheck(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		Response<Object> object = new Response<>();
		try {
			logger.info("小学学校名查询接口[xxshCheck]入参" + params);
			if (StringUtils.isBlank(params.get("schoolName")==null?null:params.get("schoolName").toString())) {
                object.setCode(2);
                object.setDescription("小学学校名称不能为空");
                return object;
            }
			// 获取配置文件中的 url地址
			String urlPath = environment.getProperty("edu.query.url.xxshCheck");
			httpEduPost(urlPath, params, request, object, 2);
		} catch (Exception e) {
			logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
			object.setCode(500);
			object.setDescription("内部服务错误");
		}
		return object;
	}
	/**
     * 初级中学学校名单查询
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/czmd/getInfo")
    public Response czmdGetInfo(@RequestParam Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        if (StringUtils.isBlank(params.get("schoolName")==null?null:params.get("schoolName").toString())) {
            object.setCode(2);
            object.setDescription("学校名称不能为空");
            return object;
        }
        String urlPath = environment.getProperty("edu.query.url.czmd.getInfo");
        return doExecute("初级中学学校名单查询", urlPath, params, request);
    }
	/**
	 * 普通高中学校名单查询接口
	 * 
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ptgzCheck", method = RequestMethod.POST)
	public Response ptgzCheck(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response)
			throws GeneralException {
		Response<Object> object = new Response<>();
		try {
			logger.info("普通高中学校名单查询接口[ptgzCheck]入参" + params);
			if (StringUtils.isBlank(params.get("schoolName")==null?null:params.get("schoolName").toString())) {
                object.setCode(2);
                object.setDescription("学校名称不能为空");
                return object;
            }
			// 获取配置文件中的 url地址
			String urlPath = environment.getProperty("edu.query.url.ptgzCheck");
			httpEduPost(urlPath, params, request, object, 2);
		} catch (Exception e) {
			logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
			object.setCode(500);
			object.setDescription("内部服务错误");
		}
		return object;
	}
	  /**
     * 民办普通高校名单信息查询
     *
     * @return
     */
    @PostMapping("/mbptgxCheck/getInfo")
    public Response  mbptgxCheckGetInfo(@RequestParam Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        if (StringUtils.isBlank(params.get("schoolName")==null?null:params.get("schoolName").toString())) {
            object.setCode(2);
            object.setDescription("学校名称不能为空");
            return object;
        }
        String urlPath = environment.getProperty("edu.query.url.mbptgxCheck.getInfo");
        return doExecute("民办普通高校名单信息查询", urlPath, params, request);
    }

    /**
     * 中等专业学校名单查询
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/zzmd/getInfo")
    public Response zzmdGetInfo(@RequestParam Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
        if (StringUtils.isBlank(params.get("schoolName")==null?null:params.get("schoolName").toString())) {
            object.setCode(2);
            object.setDescription("学校名称不能为空");
            return object;
        }
        String urlPath = environment.getProperty("edu.query.url.zzmd.getInfo");
        return doExecute("中等专业学校名单查询", urlPath, params, request);
    }
    /**
     * 招收国际留学生资格高等学校名单查询
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/zswglxscheck/getInfo")
    public Response zswglxscheckGetInfo(@RequestParam Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
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
    public Response zwhzbxjgxxGetInfo(@RequestParam Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
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
    public Response hwkzxyxxGetInfo(@RequestParam Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
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
     * 中外及与港澳台合作办学项目名单信息查询
     *
     * @return
     */
    @PostMapping("/zwjygathzbxxmCheck/getInfo")
    public Response  zwjygathzbxxmGetInfo(@RequestParam Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
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
     * 国家公派出国留学录取信息查询
     *
     * @return
     */
    @PostMapping("/gjgpcglxmd/getInfo")
    public Response  gjgpcglxmdGetInfo(@RequestParam Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
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
     * 学位证书认证查询接口
     * @param params
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDegreeCertificate", method = RequestMethod.POST)
    public Response getDegreeCertificate(@RequestParam Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
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
            
            params.put("idno", params.get("idCard"));
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
     * 特岗教师服务期满证书查询
     *
     * @return
     */
    @PostMapping("/teacherZigeCheck/getInfo")
    public Response  teacherZigeCheckGetInfo(@RequestParam Map<String, Object> params, HttpServletRequest request,HttpServletResponse response) throws GeneralException {
        Response<Object> object = new Response<>();
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
	 * 查询统一封装 get
	 *
	 * @param params
	 * @param request
	 * @param object
	 */
	public void httpEduPost(String urlPath, Map params, HttpServletRequest request, Response<Object> object,
			Integer flag) {
		// 获取headers请求头数据
		Map<String, Object> mapHeaders = getMapHeaders(request);
		List list = new ArrayList();
		if (StringUtils.isBlank(params.get("name") == null ? null : params.get("name").toString())) {
			params.put("name", mapHeaders.get("name"));
		}
		if (StringUtils.isBlank(params.get("idno") == null ? null : params.get("idno").toString())) {
			params.put("idno", mapHeaders.get("idno"));
		}
		if (StringUtils.isBlank(params.get("name") == null ? null : params.get("name").toString())) {
			params.put("name", "ceshi");
		}
		if (StringUtils.isBlank(params.get("idno") == null ? null : params.get("idno").toString())) {
			params.put("idno", "410000000000000000");
		}
		list.add(params);
		mapHeaders.put("sign", MD5Util.MD5(JSON.toJSONString(list) + SIGN));
		logger.info("读取到教育接口MD5：" + MD5Util.MD5(JSON.toJSONString(list) + SIGN));
		logger.info("读取到教育接口地址：" + urlPath);
		logger.info("读取到教育接口地址参数：" + JSON.toJSONString(list));
		String resultStr = HttpRequestUtil.URLPostJSONParams(urlPath, JSON.toJSONString(list), mapHeaders);
		logger.info("调用教育接口查询到的数据：" + resultStr);
		if (StringUtils.isNotBlank(resultStr)) {
			JSONObject json = JSONObject.parseObject(resultStr);
			// 获取code值，如果msg为空，根据code值匹配对应的msg值
			if (1 == json.getInteger("code")) {
				object.setDescription("请求失败");
			} else if (0 == json.getInteger("code") && "失败".equals(json.getString("msg"))) {
				object.setDescription("查询成功");
			} else {
				object.setDescription(json.getString("msg"));
			}
			object.setCode(json.getInteger("code"));
			json.remove("code");
			json.remove("msg");
			// 如果回传数据成功取出data
			object.setPayload(flag == 1 ? json.get("content") : json);
		} else {
			logger.info("调用教育接口异常返回值为空,入参为：[{}]", params);
			object.setCode(500);
			object.setDescription("内部服务错误");
		}
		object.setLastUpdateTime(System.currentTimeMillis());
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
                    object.setDescription("未查询到有效信息");
                }else if ("0".equals(code) && "失败".equals(msg)) {
                    object.setDescription("查询成功");
                }else{
                    object.setDescription(msg);
                }
                object.setCode(Integer.valueOf(code));
                json.remove("code");
                json.remove("msg");
                object.setPayload(json);
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
}

package com.neusoft.mid.ec.api.controller.country.householdadministration;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.controller.householdadministration.HouseholdAdministrationApplyController;
import com.neusoft.mid.ec.api.serviceInterface.householdadministration.HouseholdAdministrationApplyService;
import com.neusoft.mid.ec.api.util.Date2TampsUtil;
import com.neusoft.mid.ec.api.util.SM4Utils;
import com.neusoft.mid.ec.api.util.XmlJsonUtil;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;
import com.neusoft.mid.ec.api.util.http.WebServiceClientUtil;

import me.puras.common.json.Response;

/**
 * 户政服务查询
 */
@RestController
@RequestMapping("/country/hz/query")
public class CountryHouseholdAdministrationQueryController extends BaseController {

	@Autowired
	private Environment environment;
	@Autowired
	private HouseholdAdministrationApplyService service;
	@Autowired
	private WebServiceClientUtil webServiceClientUtil;

	public Response errorRsponse(Response obj, int code, String msg) {
		obj.setCode(code);
		obj.setLastUpdateTime(System.currentTimeMillis());
		obj.setDescription(msg);
		logger.info("户政查询参数检验为空打印：" + obj);
		return obj;
	}

	/**
	 * 查询统一封装 post
	 *
	 * @param params
	 * @param request
	 * @param object
	 */
	public void httpHzPost(String urlPath, Map params, HttpServletRequest request, Response<Object> object,
			String flag) {
		// 获取headers请求头数据
		Map<String, Object> mapHeaders = getMapHeaders(request);
		// 初始化token
		// mapHeaders.put("token", service.initToken(request));
		// 调用公安厅接口
		String result = HttpRequestUtil.URLPost(urlPath, params, mapHeaders);
		logger.info("调用公安接口出参" + result);
		if (StringUtils.isNotBlank(result)) {
			JSONObject json = JSONObject.parseObject(result);
			// 如果回传数据成功取出data
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
			// 获取code值，如果msg为空，根据code值匹配对应的msg值
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
	 * 身份证办理进度查询
	 */
	@RequestMapping("/getIdCardProgress")
	public Response getIdCardProgress(@RequestParam Map<String, Object> params, HttpServletRequest request) {
		Response<Object> object = new Response<>();
		try {
			logger.info("调用身份证办理进度查询入参" + params);
			// 入参校验
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
			// 获取配置文件中的 url地址
			String urlPath = environment.getProperty("hz.query.url.getIdCardProgress");
			logger.info("调用公安接口地址：" + urlPath);
			// 调用公安厅接口
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
	 * 同名查询接口
	 */
	@RequestMapping("/getSameName")
	public Response getSameName(@RequestParam Map<String, Object> params, HttpServletRequest request) {
		Response<Object> object = new Response<>();
		try {
			logger.info("调用同名查询接口入参" + params);
			// 入参校验
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
			// 获取配置文件中的 url地址
			String urlPath = environment.getProperty("hz.query.url.getSameName");
			logger.info("调用公安接口地址：" + urlPath);
			// 调用公安厅接口
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
	 * 保安员考试成绩查询
	 */
	@RequestMapping("/getSecurityScores")
	public Response getSecurityScores(@RequestParam Map<String, Object> params, HttpServletRequest request) {
		Response<Object> object = new Response<>();
		try {
			logger.info("调用保安员成绩查询入参" + params);
			// 入参校验
			if (null == params || params.size() == 0) {
				logger.info("调用保安员成绩查询接口入参为空");
				return errorRsponse(object, 1, "请求参数为空！");
			}
			if (null == params.get("idCard") || StringUtils.isBlank(String.valueOf(params.get("idCard")))) {
				logger.info("调用保安员成绩查询接口入参idCard为空");
				return errorRsponse(object, 1, "身份证号不能为空！");
			}
			// 获取headers请求头数据
			Map<String, Object> mapHeaders = getMapHeaders(request);
			// 初始化token
			mapHeaders.put("token", service.initToken(request));
			// 获取配置文件中的 url地址
			String urlPath = environment.getProperty("hz.query.url.getSecurityScores");
			logger.info("调用公安接口地址：" + urlPath);
			// 调用公安厅接口
			if (null != params.get("idCard") && 18 >= params.get("idCard").toString().length()) {
				params.put("idCard", SM4Utils.encryptSm4Cbc(params.get("idCard").toString()));
			}
			if (null == params.get("timeStamp")) {
				params.put("timeStamp", Date2TampsUtil.dateFormat());
			}
			// 调用公安厅接口
			httpHzPost(urlPath, params, request, object, "1");
			// 敏感信息加密
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
	 * 保安员证办理进度查询
	 */
	@RequestMapping("/getSecurityProgress")
	public Response getSecurityProgress(@RequestParam Map<String, Object> params, HttpServletRequest request) {
		Response<Object> object = new Response<>();
		try {
			logger.info("调用保安员进度查询入参" + params);
			// 入参校验
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
			// 调用公安厅接口
			httpHzPost(urlPath, params, request, object, "1");
			// 敏感信息加密
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

	// 查询敏感信息脱敏
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
	 * 办理事项查询
	 */
	@RequestMapping(value = "/eventQuery", method = RequestMethod.POST)
	public Response eventQuery(@RequestParam Map<String, Object> params, HttpServletRequest request) {
		Response<Object> object = new Response<>();
		try {
			logger.info("办理事项查询[eventQuery]入参" + params);
			String idCard = null == params.get("idno") ? null : params.get("idno").toString();
			String userid = null == params.get("userid") ? null : params.get("userid").toString();
			String state = null == params.get("state") ? "0" : params.get("state").toString();
			String index = null == params.get("index") ? "1" : params.get("index").toString();
			String pageSize = null == params.get("pageSize") ? "5" : params.get("pageSize").toString();
			String count = null == params.get("count") ? null : params.get("count").toString();
			// 入参校验
			if (null == params || params.size() == 0) {
				logger.info("办理事项查询查询入参为空");
				return errorRsponse(object, 1, "请求参数为空！");
			}

			if (StringUtils.isBlank(userid)) {
				logger.info("办理事项查询入参idCard和userid为空");
				return errorRsponse(object, 1, "用户ID不能为空！");
			}
			// 获取配置文件中的 url地址
			String urlPath = environment.getProperty("hz.query.url.eventQuery");
			logger.info("云政办理事项查询接口地址：" + urlPath);
			logger.info(
					"云政办理事项{getApasInfoListByIdCardAndUse}查询入参 idCard={},userid={},state={},index={},pageSize={},count={}",
					idCard, userid, state, index, pageSize, count);
			String xmlList = webServiceClientUtil.getApasInfoListByIdCardAndUser(urlPath, idCard, userid, state, index,
					pageSize, count);
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
}

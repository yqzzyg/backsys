package com.neusoft.mid.ec.api.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.neusoft.mid.ec.api.Constants;
import com.neusoft.mid.ec.api.util.GetIPAddress;
import com.neusoft.mid.ec.api.util.JedisClusterUtil;

import me.puras.common.holder.ObjectMapperHolder;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;

@Component
public class WhiteIPInterceptor extends HandlerInterceptorAdapter {

	private static Logger LOGGEER = Logger.getLogger(WhiteIPInterceptor.class);

	@Autowired
	public JedisClusterUtil jedisClusterUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		boolean flag = true;
		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		// response.addHeader("Access-Control-Allow-Origin", "*");// 跨域问题处理
		// response.addHeader("Access-Control-Allow-Headers", "*");// 跨域问题处理
		// response.addHeader("Access-Control-Allow-Methods",
		// "GET,POST,PUT,DELETE");

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		String requestPath = request.getRequestURI();
		String userIP = GetIPAddress.getIPAddress(request);
		int returnCode = 0;
		String msg = "";
		LOGGEER.info("Method: " + method.getName());
		LOGGEER.info("requestPath: " + requestPath);
		LOGGEER.info("realIP:" + userIP);
		if (requestPath.contains("/login/**") || requestPath.contains("/swagger")) {
			return true;
		}
		if (requestPath.contains("/error")) {
			return true;
		}

		// 判断该IP是否在白名单里，若不在，拦截该请求

		List<String> ipList = new ArrayList<>();
		ipList = jedisClusterUtil.getAllList(Constants.WHITE_IP_PREFIX_API);
		if (ipList == null || ipList.isEmpty()) {
			return true;
		}
		for (int i = 0; i < ipList.size(); i++) {
			String ip = ipList.get(i).toString();
			if (ip.equals("*.*.*.*")) {
				return true;
			}
			if (ip.contains("*")) {
				ip = ip.substring(0, ip.indexOf("*") - 1);
				LOGGEER.info("截取后的ip====" + ip);

			}
			if (!userIP.contains(ip)) {
				flag = false;
			} else {
				flag = true;
				break;
			}

		}
		if (!flag) {
			returnCode = 8888;
			msg = "非法IP请求";
			responseNotLogin(request, response, returnCode, msg);
			LOGGEER.error(userIP + "====" + msg);
			return false;
		} else {
			return true;
		}


	}

	/**
	 * @author zhao.zhenyu
	 * @description 拦截请求响应
	 * @date 2018年10月23日 上午10:21:47
	 * @param request
	 * @param response
	 * @param returnCode
	 *            返回码
	 * @param msg
	 *            返回的消息描述
	 * @throws Exception
	 */
	protected void responseNotLogin(HttpServletRequest request, HttpServletResponse response, int returnCode, String msg) throws Exception {
		Response resp = ResponseHelper.createResponse(returnCode, msg);
		try {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getOutputStream().write(ObjectMapperHolder.getInstance().getMapper().writeValueAsBytes(resp));
		} catch (Exception ex) {
			LOGGEER.error("ResponseNotLogin failed", ex);
		}
	}
}

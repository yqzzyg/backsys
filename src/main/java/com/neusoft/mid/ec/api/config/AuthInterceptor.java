package com.neusoft.mid.ec.api.config;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
public class AuthInterceptor extends HandlerInterceptorAdapter {

	private static Logger LOGGEER = Logger.getLogger(AuthInterceptor.class);

	// 缓存有效期30分钟
	private static final int REDISTIME = 60 * 30;

	@Autowired
	public JedisClusterUtil jedisClusterUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 如果不是映射到方法直接通过
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		response.addHeader("Access-Control-Allow-Origin", "*");// 跨域问题处理
		response.addHeader("Access-Control-Allow-Headers", "*");// 跨域问题处理
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		String requestPath = request.getRequestURI();
		String userIP = GetIPAddress.getIPAddress(request);
		int returnCode = 0;
		String msg = "";
		LOGGEER.info("Method: " + method.getName());
		LOGGEER.info("requestPath: " + requestPath);
		LOGGEER.info("realIP:" + userIP);
		if (requestPath.contains("/login/**") || requestPath.contains("/swagger")|| requestPath.contains("/receive/msgRslt")|| requestPath.contains("/receive/msgTest")) {
			return true;
		}
		if (requestPath.contains("/error")) {
			return true;
		}
		// 传输的token信息
		String token = request.getHeader("token");

		LOGGEER.info("=====传输的token: " + token);

		if (StringUtils.isEmpty(token) || !jedisClusterUtil.exists(Constants.TOKEN_PREFIX + ":" + token)) {
			returnCode = 9999;
			msg = "登录超时";
			responseNotLogin(request, response, returnCode, msg);
			LOGGEER.error(msg);
			return false;
		} else {
			// 每次验证通过，都要更新token有效期
			jedisClusterUtil.expire(Constants.TOKEN_PREFIX + ":" + token, REDISTIME);

		}
		return true;
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

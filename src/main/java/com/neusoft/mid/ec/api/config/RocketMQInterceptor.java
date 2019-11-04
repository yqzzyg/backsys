package com.neusoft.mid.ec.api.config;

import com.alibaba.fastjson.JSON;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.domain.UserLog;
import com.neusoft.mid.ec.api.util.GetIPAddress;
import com.neusoft.mid.ec.api.util.RocketMQSendUitl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class RocketMQInterceptor extends HandlerInterceptorAdapter {

	private static Logger LOGGEER = LoggerFactory.getLogger(RocketMQInterceptor.class);

	private ThreadLocal<Long> timeThreadLocal = new ThreadLocal<>();

	@Autowired
	private RocketMQSendUitl rocketMQSendUitl;

	/**
	 *  拦截请求
	 *  异步记录操作日志
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		try {
			String requestPath = request.getRequestURI();
			String userIP = GetIPAddress.getIPAddress(request);
			LOGGEER.info("realIP:" + userIP);
			LOGGEER.info("preHandle requestPath: " + requestPath);
			timeThreadLocal.set(System.currentTimeMillis());
		} catch (Exception e) {
			LOGGEER.warn("preHandle:"+ e.getMessage());
		}
		return true;
	}


	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		try {
			int completionTime =(int) (System.currentTimeMillis() - timeThreadLocal.get());
			BaseController baseController = new BaseController();
			String requestPath = request.getRequestURI();
			LOGGEER.info("afterCompletion requestPath:{},completionTime={}ms" ,requestPath,completionTime);
			String userIP = GetIPAddress.getIPAddress(request);
			UserLog userLog = new UserLog();
			userLog.setUserIp(userIP);
			userLog.setInterfaceUrl(requestPath);
			userLog.setCompletionTime(completionTime);
			Map map = new HashMap(2);
			map.put("header", baseController.getRequestInfo(request));
			map.put("userLog",userLog);
			timeThreadLocal.set(null);
			rocketMQSendUitl.sendMQMsg(JSON.toJSONString(map));
		} catch (Exception e) {
			LOGGEER.warn("afterCompletion:"+ e.getMessage());
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}
}

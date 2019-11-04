package com.neusoft.mid.ec.api.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.neusoft.mid.ec.api.Constants;
import com.neusoft.mid.ec.api.util.JedisClusterUtil;

@WebFilter(urlPatterns = "", filterName = "authFilter")
public class AuthFilter implements Filter {
	
	private static Logger LOGGEER = Logger.getLogger(AuthFilter.class);

	private String NO_LOGIN = "Auth failed.";

	@Autowired
	public JedisClusterUtil jedisClusterUtil;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		System.out.println("进入authFilter过滤器");
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String token = request.getHeader("token");
		System.out.println(request.getRequestURI());
		boolean b = jedisClusterUtil.exists(Constants.TOKEN_PREFIX + ":" + token);

		if (StringUtils.isEmpty(token) || !b) {
			
			LOGGEER.error("获取图片的token无效！！");

			response.getWriter().write(this.NO_LOGIN);

		} else {
			filterChain.doFilter(servletRequest, servletResponse);

		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void destroy() {

	}

}

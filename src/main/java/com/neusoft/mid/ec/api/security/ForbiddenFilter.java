package com.neusoft.mid.ec.api.security;

import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 这个Filter永远返回forbidden，一般是放在过滤条件的最后，主要是在开发阶段防止有url意外泄露，造成安全隐患
 * Created by puras on 10/10/16.
 */
public class ForbiddenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    @Override
    public void destroy() {

    }
}

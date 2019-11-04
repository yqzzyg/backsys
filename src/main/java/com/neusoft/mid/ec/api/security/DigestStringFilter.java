package com.neusoft.mid.ec.api.security;

import me.puras.common.holder.ObjectMapperHolder;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.session.NoSessionCreationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通过Request里的Token验证用户身份，token无效时中止请求,tokenMust控制无token时的行为
 * Created by puras on 10/10/16.
 */
public class DigestStringFilter extends NoSessionCreationFilter {
    private static final Logger logger = LoggerFactory.getLogger(DigestStringFilter.class);

    public static final String TOKEN_KEY_NAME = "mo_token";

    private boolean tokenMust = true;

    public DigestStringFilter() {}

    public DigestStringFilter(boolean tokenMust) {
        this.tokenMust = tokenMust;
    }

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        super.onPreHandle(request, response, mappedValue);
        HttpServletRequest req = (HttpServletRequest) request;
        System.out.println(mappedValue);

        Subject subject = SecurityUtils.getSubject();
        try {
            AuthenticationToken authToken = createToken(req);

            if (null == authToken) {
                if (tokenMust) {
                    throw new AuthenticationException("No token provided");
                } else {
                    return true;
                }
            }

            subject.login(authToken);
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            responseNotLogin(req, (HttpServletResponse) response);
            return false;
        }

        return true;
    }

    private AuthenticationToken createToken(HttpServletRequest request) throws Exception {
        String token = getToken(request);
        if (null != token) {
            return new DigestStringToken(token);
        }
        return null;
    }

    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_KEY_NAME);
        if (null != token) {
            logger.debug("Found token in header!");
            return token;
        }

        return null;
    }

    protected void responseNotLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Response<?> resp = ResponseHelper.createResponse(-1, "没有访问权限，请重新登录!");
        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getOutputStream().write(ObjectMapperHolder.getInstance().getMapper().writeValueAsBytes(resp));
        } catch (Exception ex) {
            logger.error("ResponseNotLogin failed", ex);
        }
    }
}

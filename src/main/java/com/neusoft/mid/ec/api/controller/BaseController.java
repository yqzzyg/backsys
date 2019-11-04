package com.neusoft.mid.ec.api.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.neusoft.mid.ec.api.domain.RequestInfo;
import com.neusoft.mid.ec.api.exception.GeneralException;
import com.neusoft.mid.ec.api.util.RandomValidateCodeUtil;

@RestController
public class BaseController  {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    Environment environment;

    @Autowired
    RandomValidateCodeUtil randomValidateCodeUtil;

    /**
     * 验证验证码
     * @param request
     * @param response
     * @param inputRandomCode
     * @throws GeneralException
     */
    protected void verifyCode(HttpServletRequest request, HttpServletResponse response, String inputRandomCode,
        String flag) throws GeneralException {

        if (inputRandomCode == null || "".equals(inputRandomCode) || "null".equals(inputRandomCode)) {
            logger.info("验证码错误,code={}", inputRandomCode);
            throw new GeneralException("验证码错误");
        }

        boolean result = randomValidateCodeUtil.verifyCode(request, response, inputRandomCode, flag);

        if (!result) {
            logger.info("验证码错误,code={}", inputRandomCode);
            throw new GeneralException("验证码错误");
        }
        logger.info("验证码验证通过,code={}", inputRandomCode);
    }

    public RequestInfo getRequestInfo(HttpServletRequest request) {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setFromserverid(request.getHeader("fromserverid"));
        requestInfo.setToserverid(request.getHeader("toserverid"));
        requestInfo.setToken(request.getHeader("token"));
        requestInfo.setSysid(request.getHeader("sysid"));
        requestInfo.setFuncid(request.getHeader("funcid"));
        try {
            if (request.getHeader("name") != null) {
                requestInfo.setName(URLDecoder.decode(request.getHeader("name"), "utf-8"));
            }
        } catch (UnsupportedEncodingException e) {
            logger.error("error", e);
        }
        requestInfo.setIdno(request.getHeader("idno"));
        requestInfo.setIdtype(request.getHeader("idtype"));
        logger.info("云政userid：", request.getHeader("yunzheng"));
        requestInfo.setYunzheng(request.getHeader("yunzheng"));
        return requestInfo;
    }

    protected Map<String, Object> getMapHeaders(HttpServletRequest request) {
        Map<String, Object> headParams = new HashMap<>();
        headParams.put("fromserverid", request.getHeader("fromserverid"));
        headParams.put("toserverid", request.getHeader("toserverid"));
        headParams.put("token", request.getHeader("token"));
        headParams.put("sysid", request.getHeader("sysid"));
        headParams.put("funcid", request.getHeader("funcid"));
        headParams.put("name", request.getHeader("name"));
        headParams.put("idno", request.getHeader("idno"));
        headParams.put("idtype", request.getHeader("idtype"));
        headParams.put("yunzheng", request.getHeader("yunzheng"));
        return headParams;
    }

    /** 如果字符串为空则返回true  */
    protected boolean isNull(Object obj) {
        boolean result = true;
        try {
            if (null != obj && StringUtils.isNotBlank(obj.toString())) {
                result = false;
            }
        } catch (Exception e) {
            result = true;
            logger.error("验证数据是否为空出现异常：", e);
        }
        return result;

    }

    @GetMapping("/getPro")
    public  String getProperties(String proName){
        return environment.getProperty(proName);
    }
}

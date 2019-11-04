package com.neusoft.mid.ec.api.config;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;
import me.puras.common.json.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class token {

    private static Logger LOGGEER = Logger.getLogger(AuthFilter.class);
    private final String urlPath = "http://managerapi.henanga.gov.cn/mc/api/console/token";

    public String initToken(HttpServletRequest request) {
        Response<Object> response = new Response<>();
        try {
            Map params = new HashMap<>();
            params.put("appId", "100260");
            params.put("secrectKey", "C6djamlf1uHMm2xQ");
            //参数封装，调用服务查询数据
            Map<String, Object> mapHeaders = new HashMap<>();

            LOGGEER.info("调用公安接口地址：" + urlPath);
            String resultStr = HttpRequestUtil.URLPostJSONParams(urlPath, JSONObject.toJSONString(params), mapHeaders);
            LOGGEER.info("调用公安接口查询到的数据：" + resultStr);
            if (StringUtils.isNotBlank(resultStr)) {
                JSONObject json = JSONObject.parseObject(resultStr);
                //如果回传数据成功取出data
                if ("true".equals(json.getString("succ"))) {
                    String token = String.valueOf(json.get("data"));
                    LOGGEER.info("获取的token值" + token);
                    return token;
                }
            } else {
                LOGGEER.info("调用户政接口异常返回值为空");
                response.setCode(500);
                response.setDescription("内部服务错误");
            }
        } catch (Exception e) {
            LOGGEER.info("调用户政接口异常" + e.getMessage(), e);
            response.setCode(500);
            response.setDescription("内部服务错误");
        }
        return null;
    }

}

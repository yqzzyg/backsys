package com.neusoft.mid.ec.api.controller.health;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.serviceInterface.health.HealthService;
import com.neusoft.mid.ec.api.util.SM4Utils;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;

import me.puras.common.json.Response;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 儿童疫苗
 */
@RestController
@RequestMapping("/childrenHealth")
public class ChildrenHealthController  extends BaseController {
	
	/**
     * 普通高招录取查询
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/getAvailableReservationSchedule")
    public Response queryPZL(@RequestBody Map<String, String> params, HttpServletRequest request){
        return doExecute("普通高招录取查询", "", params,request);
    }
    
    /**
     * @param params
     * @param request
     * @param
     */
    @SuppressWarnings({ "rawtypes" })
    public Response doExecute(String functionName, String urlPath, Map<String, String> params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        logger.info(functionName + "入参：" + params);
        try {
            // 接口调用
        	Map<String, Object> headMap = getMapHeaders(request);
            String response = HttpRequestUtil.URLGet(urlPath, params, "UTF-8", headMap);
            // 调用结果
            if (StringUtils.isNotBlank(response)) {
            	response = SM4Utils.decryptSm4Cbc(response);
                logger.info(functionName + "调用第三方出参：" + response);
                JSONObject json = JSONObject.parseObject(response);
                String code = json.getString("result");
                String msg = json.getString("resultmsg");
                object.setCode(Integer.valueOf(code));
                object.setDescription(msg);
            } else {
                logger.info(functionName + "调用第三方异常出参：" + response);
                object.setCode(500);
                object.setDescription("内部服务错误");
            }
        } catch (Exception e) {
        	logger.info(functionName + "异常：" + params, e);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        object.setLastUpdateTime(System.currentTimeMillis());
        logger.info(functionName + "出参：" + object);
        return object;
    }
}

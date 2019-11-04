package com.neusoft.mid.ec.api.controller.country.assess;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;

import me.puras.common.json.Response;

/**
 * 评价查询
 */
@RestController
@RequestMapping("/country/assess/query")
public class CountryAssessQueryController extends BaseController {
	@Value("${assess.query.url}")
    private String assessQueryUrl;
	@Value("${assess.evaluation.url}")
    private String assessEvaluationUrl;
	
	/**
     * 查询评价信息
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
	@PostMapping("/getAppAssessType")
    public Response specialStart(@RequestParam Map<String, Object> params, HttpServletRequest request){
        return doExecute("查询评价信息", assessQueryUrl, params, request,"getAppAssessType");
    }

    /**
     * 好差评接口
     */
    @RequestMapping(value = "/getAppAssess", method = RequestMethod.POST)
    public Response getAppAssess(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        return doExecute("好差评接口", assessEvaluationUrl, params, request,"getAppAssess");
    }


	/**
     * @param params
     * @param request
     * @param
     */
	@SuppressWarnings({ "rawtypes" })
	public Response doExecute(String functionName, String urlPath, Map<String, Object> params, HttpServletRequest request,String method) {
    	Response<Object> object = new Response<>();
    	logger.info(functionName + "入参：" + params);
        try {
            // 接口调用
        	Map<String, Object> headMap = getMapHeaders(request);
            String response = HttpRequestUtil.URLPost(urlPath, params, headMap);
            // 调用结果
            if (StringUtils.isNotBlank(response)) {
            	logger.info("调用第三方出参：" + response);
            	JSONObject json = JSON.parseObject(response);
                String code = json.getString("code");
                String msg = json.getString("msg");
                if(StringUtils.isBlank(msg)) {
                    msg = json.getString("message");
                }
                Object type = json.get("type");
                if("getAppAssess".equals(method)){
                    object.setCode(StringUtils.equals("1", code)?1:0);
                }else{
                    object.setCode(StringUtils.equals("false", code)?1:0);
                }
                object.setDescription(msg);
                object.setPayload(type);
            } else {
            	logger.info("调用第三方异常出参：" + response);
            	object.setCode(500);
                object.setDescription("内部服务错误");
            }
        } catch (Exception e) {
            logger.error(functionName + "异常：" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        object.setLastUpdateTime(System.currentTimeMillis());
        logger.info(functionName + "出参：" + object);
        return object;
    }
}

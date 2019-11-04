package com.neusoft.mid.ec.api.controller.travel;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.util.Date2TampsUtil;
import com.neusoft.mid.ec.api.util.EncodeUtil;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.puras.common.json.Response;

/**
 * 旅游
 */
@RestController
@RequestMapping("/travel")
@Api(value = "旅行", description = "旅行")
public class TravelController extends BaseController {

    @Autowired
    private Environment environment;

    /**
     * 1   景区日停留人数接口OSP_NURSING_GETDAYSPONUM
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/ospNursingGetdaysponum")
    @ApiOperation("景区日停留人数接口")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "params", value = "查询日期JSON格式", required = true, dataType = "Map")})
    public Response ospNursingGetdaysponum(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("景区日停留人数接口 入参" + params);
            // 1.获取AccessToken
            String accessTokenulPath = environment.getProperty("ravel.url.getAccessTokenr");
            Map<String, String> tokenParam = new HashMap<>();
            tokenParam.put("app_id", EncodeUtil.app_id);
            tokenParam.put("app_key", EncodeUtil.app_key);
            tokenParam.put("grant_type", EncodeUtil.grant_type);
            String resultToken = HttpRequestUtil.URLGet(accessTokenulPath, tokenParam, "utf-8");
            // 2.获取sing
            String sing = EncodeUtil.getSing(JSON.toJSONString(params),
                JSON.parseObject(resultToken).getString("access_token"));

            object.setLastUpdateTime(System.currentTimeMillis());

            // ---------------------------------------------------------------------------

            logger.info("旅游——景区日停留人数接口[getDaySpotNumber]出参：" + JSON.toJSONString(object));
        } catch (Exception e) {
            logger.error("调用景区日停留人数接口 接口调用异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }

        return object;
    }

    /**
     * 1   景区日停留人数接口
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/getDaySpotNumber")
    @ApiOperation("景区日停留人数接口")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "params", value = "查询日期JSON格式", required = true, dataType = "Map")})
    public Response getDaySpotNumber(@RequestBody Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        try {
            logger.info("景区日停留人数接口 入参" + params);
            String urlPath = environment.getProperty("ravel.url.getDaySpotNumber");
            logger.info("读取到调用景区日停留人数接口接口地址：" + urlPath);
            if (null != params.get("dataDate")) {// 默认当前年
                params.put("dataDate", Date2TampsUtil.timeStamp2Date(Date2TampsUtil.timeStamp(), "yyyyMMdd"));
            }
            httpTravelResopnse(JSON.toJSONString(params), request, object, urlPath);
            logger.info("旅游——景区日停留人数接口[getDaySpotNumber]出参：" + JSON.toJSONString(object));
        } catch (Exception e) {
            logger.error("调用景区日停留人数接口 接口调用异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }

        return object;
    }

    public void httpTravelResopnse(@RequestBody String params, HttpServletRequest request, Response<Object> object,
        String urlPath) {
        String resultStr = HttpRequestUtil.URLPostJSONParams(urlPath, params, new HashMap<>());
        logger.info("调用旅行接口查询到的数据：" + resultStr);
        if (StringUtils.isNotBlank(resultStr)) {
            JSONObject json = JSON.parseObject(resultStr);
            // 如果回传数据成功取出data
            if ("200".equals(json.getString("status"))) {// status 200:成功；非200:失败
                object.setPayload(json.get("data"));// 返回数据
                object.setCode(json.getInteger("status"));
            }
            String s = json.getString("errorMsg");
            if (StringUtils.isNotBlank(s)) {
                object.setDescription(s);
            }
        } else {
            logger.info("调用旅行接口异常返回值为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        object.setLastUpdateTime(System.currentTimeMillis());
    }

}

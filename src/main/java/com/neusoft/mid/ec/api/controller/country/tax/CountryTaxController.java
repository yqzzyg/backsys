package com.neusoft.mid.ec.api.controller.country.tax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.exception.GeneralException;
import com.neusoft.mid.ec.api.util.ASEPlus;
import com.neusoft.mid.ec.api.util.Date2TampsUtil;
import com.neusoft.mid.ec.api.util.MapRemoveNullUtil;
import com.neusoft.mid.ec.api.util.RandomValidateCodeUtil;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.puras.common.json.Response;

/**
 * 税务
 */
@RestController
@RequestMapping("/country/tax")
public class CountryTaxController extends BaseController {

    @Autowired
    private Environment environment;
    @Autowired
    private RandomValidateCodeUtil randomValidateCodeUtil;

    /** 合计金额-验证发票代码后五位  */
    private static final String HJJE_FPDM_FIVE = "20013、20023、20033、20043、20054、20065、30011、30022、30031、30042、90012、90022、90093、90301、90333、90421、90431、90441、90531、90541、93811";
    /** 合计金额-验证发票代码  */
    private static final String HJJE_FPDM = "141000421863、141000521863、141000621863、141000721863、141000821863、141000921863、141001021863";
    /*** 对于出租车发票（发票代码141001830051 发票号码42775424）该情况没有收款方识别号 */
    private static final String FPDM = "141001830051 ";
    private static final String FPHM = "42775424";
    /*** 实体调用请求 不查询开具信息值为getFpzw;*/
    private static final String ACTION_getFpzw = "getFpzw";
    /**     查询开具信息值为： getFpzw_JDFPMX */
    private static final String ACTION_getFpzw_JDFPMX = "getFpzw_JDFPMX";
    private static final String yyyyMMdd = "yyyyMMdd";

    
    
    @SuppressWarnings({"rawtypes"})
    @RequestMapping("/findNsrztxx")
    @ApiOperation("纳税人状态查询接口")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "params", value = "查询参数JSON格式", required = true, dataType = "Map")})
    public Response findNsrztxx(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response)
        throws GeneralException {
        Response<Object> object = new Response<>();
        try {
            logger.info("纳税人状态查询接口 入参" + params);
            if (isNull(params.get("NSRSBH"))) {
                logger.error("纳税人状态查询接口 缺失纳税人识别号");
                object.setCode(500);
                object.setDescription("请输入纳税人识别号");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            String urlPath = environment.getProperty("tax.url.findNsrztxx");
            logger.info("读取到调用纳税人状态查询接口接口地址：" + urlPath);
            // 加入时间戳参数
            httpTaxPostResponse(params, urlPath, object, "1");// 返回值 // 数据说明： 1：成功 -1：查询失败 公用方法
            logger.info("税务——纳税人状态查询接口[getDaySpotNumber]出参：" + JSON.toJSONString(object));
        } catch (Exception e) {
            logger.error("调用纳税人状态查询接口 接口调用异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

  

    @SuppressWarnings({"rawtypes"})
    @RequestMapping("/findCktsl")
    @ApiOperation("出口退税率查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "params", value = "查询参数JSON格式", required = true, dataType = "Map")})
    public Response findCktsl(@RequestParam Map<String, String> params, HttpServletRequest request,
        HttpServletResponse response) throws GeneralException {
        logger.info("出口退税率查询接口 入参" + params);
        Response<Object> object = new Response<>();
        try {
            if (isNull(params.get("HGSPM")) || isNull(params.get("SPMC"))) {
                logger.error("出口退税率查询 缺失参数");
                object.setCode(500);
                object.setDescription("缺失参数请检查");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            String urlPath = environment.getProperty("tax.url.findCktsl");
            logger.info("读取到调用出口退税率查询接口接口地址：" + urlPath);
            // 加入时间戳参数
            //params.remove("loginKey");
            httpTaxGetResponse(params, urlPath, object, "1");// 返回值 // 数据说明： 1：成功 -1：查询失败 公用方法
            logger.info("税务——出口退税率查询接口[getDaySpotNumber]出参：" + JSON.toJSONString(object));
        } catch (Exception e) {
            logger.error("调用出口退税率查询接口 接口调用异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
    }

   

    @SuppressWarnings({"rawtypes", "unchecked"})
    @RequestMapping("/findBsrlxx")
    @ApiOperation("办税日历查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "params", value = "查询参数JSON格式", required = true, dataType = "Map")})
    public JSONObject findBsrlxx(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        JSONObject json = new JSONObject();
        try {
            logger.info("办税日历查询接口 入参" + params);
            MapRemoveNullUtil.removeNullKey(params);
            logger.info("办税日历查询接口 入参移除空key" + params);
            String urlPath = environment.getProperty("tax.url.findBsrlxx");
            logger.info("读取到调用办税日历查询接口接口地址：" + urlPath);
            // 加入时间戳参数
            if (isNull(params.get("timestamp"))) {
                String date = Date2TampsUtil.timeStamp2Date(Date2TampsUtil.timeStamp(), yyyyMMdd);
                logger.info("获取当前日期：" + date);
                params.put("timestamp", ASEPlus.Encrypt(date, ASEPlus.sKey));// 年月日
            } else if (8 >= params.get("timestamp").toString().length()) {
                params.put("timestamp", ASEPlus.Encrypt(params.get("timestamp").toString(), ASEPlus.sKey));// 加密 年月日
            }
            String resultStr = HttpRequestUtil.URLPost(urlPath, params, new HashMap<>());
            logger.info("调用纳税 查询接口查询到的数据：" + resultStr);
            if (StringUtils.isNotBlank(resultStr)) {
                json = JSON.parseObject(resultStr);
                json.put("description", json.get("msg"));
                json.put("payload", json.get("data"));
                json.remove("msg");
                json.remove("data");
                return json;
            } else {
                logger.info("调用税务接口异常返回值为空,入参为：[{}]", params);
                json.put("code", -1);
                json.put("description", "查询失败");
                json.put("lastUpdateTime", System.currentTimeMillis());
                json.put("payload", new JSONObject());
            }
//            logger.info("税务——办税日历查询接口[getDaySpotNumber]出参：" + JSON.toJSONString(object));
        } catch (Exception e) {
            logger.error("调用办税日历查询接口 接口调用异常" + e.getMessage(), e);
        }
        return json;
    }

    @SuppressWarnings("unchecked")
    private void httpTaxPostResponse(@RequestBody Map params, String urlPath, Response<Object> object, String code) {
        try {
            params.remove("loginKey");
            if (isNull(params.get("timestamp"))) {
                String date = Date2TampsUtil.timeStamp2Date(Date2TampsUtil.timeStamp(), yyyyMMdd);
                logger.info("获取当前日期：" + date);
                params.put("timestamp", ASEPlus.Encrypt(date, ASEPlus.sKey));// 年月日
            } else if (8 >= params.get("timestamp").toString().length()) {
                params.put("timestamp", ASEPlus.Encrypt(params.get("timestamp").toString(), ASEPlus.sKey));// 加密 年月日
            }
            // 加入时间戳参数

            String resultStr = HttpRequestUtil.URLPost(urlPath, params, new HashMap<>());
            logger.info("调用纳税 查询接口查询到的数据：" + resultStr);
            if (StringUtils.isNotBlank(resultStr)) {
                JSONObject json = JSON.parseObject(resultStr);
                // 如果回传数据成功取出data
                if (code.equals(json.getString("code"))) {// 数据说明： 1：成功 -1：查询失败
                    object.setPayload(null == json.get("data") ? new JSONObject() : json.get("data"));
                    object.setCode(json.getInteger("code"));
                } else {
                    // 获取code值，如果msg为空，根据code值匹配对应的msg值
                    object.setCode(json.getInteger("code"));
                    object.setPayload(new JSONObject());
                }
                String s = json.getString("msg");
                if (StringUtils.isNotBlank(s)) {
                    object.setDescription(s);
                }
            } else {
                logger.info("调用税务接口异常返回值为空,入参为：[{}]", params);
                object.setCode(500);
                object.setDescription("暂无数据");
                object.setPayload(new JSONObject());
            }
            object.setLastUpdateTime(System.currentTimeMillis());
        } catch (Exception e) {
            logger.error("税务公用方法出错：params=" + params + ";urlPath=" + urlPath, e);
        }
    }

    private void httpTaxGetResponse(@RequestBody Map<String, String> params, String urlPath, Response<Object> object,
        String code) {
        try {
            params.remove("loginKey");
            // 加入时间戳参数
            String date = Date2TampsUtil.timeStamp2Date(Date2TampsUtil.timeStamp(), yyyyMMdd);
            logger.info("获取当前日期：" + date);
            params.put("timestamp", ASEPlus.Encrypt(date, ASEPlus.sKey));// 年月日 k3b0r0iTezByiq//3uKtgg==
            String resultStr = HttpRequestUtil.URLGet(urlPath, params, "utf-8");
            logger.info("调用纳税 查询接口查询到的数据：" + resultStr);
            if (StringUtils.isNotBlank(resultStr)) {
                JSONObject json = JSON.parseObject(resultStr);
                // 如果回传数据成功取出data
                if (code.equals(json.getString("code"))) {// 数据说明： 1：成功 -1：查询失败
                    object.setPayload(null == json.get("data") ? new JSONObject() : json.get("data"));
                    object.setCode(json.getInteger("code"));
                } else {
                    // 获取code值，如果msg为空，根据code值匹配对应的msg值
                    object.setCode(json.getInteger("code"));
                    object.setPayload(new JSONObject());
                }
                String s = json.getString("msg");
                if (StringUtils.isNotBlank(s)) {
                    object.setDescription(s);
                }
            } else {
                logger.info("调用税务接口异常返回值为空,入参为：[{}]", params);
                object.setCode(500);
                object.setDescription("暂无数据");
                object.setPayload(new JSONObject());
            }
            object.setLastUpdateTime(System.currentTimeMillis());
        } catch (Exception e) {
            logger.error("税务公用方法出错：params=" + params + ";urlPath=" + urlPath, e);
        }
    }

   

    private Response<Object> httpTaxPostCheckFpxx(@RequestBody Map params, String urlPath, String code) {
        Response<Object> object = new Response<>();
        try {
            params.remove("loginKey");
            if (isNull(params.get("timestamp"))) {
                String date = Date2TampsUtil.timeStamp2Date(Date2TampsUtil.timeStamp(), yyyyMMdd);
                logger.info("获取当前日期：" + date);
                params.put("timestamp", ASEPlus.Encrypt(date, ASEPlus.sKey));// 年月日
            } else if (8 >= params.get("timestamp").toString().length()) {
                params.put("timestamp", ASEPlus.Encrypt(params.get("timestamp").toString(), ASEPlus.sKey));// 加密 年月日
            }
            // 加入时间戳参数

            String resultStr = HttpRequestUtil.URLPost(urlPath, params, new HashMap<>());
            logger.info("调用纳税 查询接口查询到的数据：" + resultStr);
            if (StringUtils.isNotBlank(resultStr)) {
                JSONObject json = JSON.parseObject(resultStr);
                // 如果回传数据成功取出data
                if (code.equals(json.getString("code"))) {// 数据说明： 1：成功 -1：查询失败
                    object.setPayload(null == json.get("data") ? new JSONObject() : json.get("data"));
                    object.setCode(json.getInteger("code"));
                } else {
                    // 获取code值，如果msg为空，根据code值匹配对应的msg值
                    object.setCode(json.getInteger("code"));
                    object.setPayload(new JSONObject());
                }
                String s = json.getString("msg");
                if (StringUtils.isNotBlank(s)) {
                    object.setDescription(s);
                }
            } else {
                logger.info("调用税务接口异常返回值为空,入参为：[{}]", params);
                object.setCode(500);
                object.setDescription("暂无数据");
                object.setPayload(new JSONObject());
            }
            object.setLastUpdateTime(System.currentTimeMillis());
        } catch (Exception e) {
            logger.error("税务公用方法出错：params=" + params + ";urlPath=" + urlPath, e);
        }
        return object;
    }

}

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
    @RequestMapping("/fpxxcx")
    @ApiOperation("发票查询接口")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "params", value = "查询参数JSON格式", required = true, dataType = "Map")})
    public JSONObject fpxxcx(@RequestParam Map<String, String> params, HttpServletRequest request,
        HttpServletResponse response) throws GeneralException {
        JSONObject returnJson = new JSONObject();
        logger.info("发票查询接口 入参" + params);
        Response<Object> object = new Response<>();
        try {
            String urlPath = environment.getProperty("tax.url.checkFpxx");
            logger.info("读取到调用发票查询接口接口地址：" + urlPath);
            if (isNull(params.get("FPHM")) || isNull(isNull(params.get("FPDM"))) || isNull(params.get("ACTION"))) {
                logger.error("发票查询接口 参数不完整");
                returnJson.put("code", "500");
                returnJson.put("description", "缺失参数请检查");
                returnJson.put("lastUpdateTime", System.currentTimeMillis());
                return returnJson;
            }
            if (!FPDM.equals(params.get("FPDM")) && !FPHM.equals(params.get("FPHM")) && isNull(params.get("NSRSBH"))) {
                logger.error("验证是否需要纳税人识别号");
                returnJson.put("code", "500");
                returnJson.put("description", "请输入收款方纳税人识别号");
                returnJson.put("lastUpdateTime", System.currentTimeMillis());
                return returnJson;
            }
            // 验证合计金额、开票日期 参数
            if (isNull(params.get("HJJE")) || isNull(params.get("KPRQ"))) {// 合计金额、开票日期
                String fpdm = params.get("FPDM") + "";
                if (fpdm.length() > 5
                    && (HJJE_FPDM.contains(fpdm) || HJJE_FPDM_FIVE.contains(fpdm.substring(fpdm.length() - 5)))) {
                    logger.error("发票代码等于" + HJJE_FPDM + " 或者后五位等于" + HJJE_FPDM_FIVE + "字符串必须传合计金额、开票日期");
                    returnJson.put("code", "500");
                    returnJson.put("description", "请检查合计金额、开票日期是否完整");
                    returnJson.put("lastUpdateTime", System.currentTimeMillis());
                    return returnJson;

                }
            }
            // 处理返回数据1.查询有开具信息首先调用无开具信息获取【领购人名称】【领购人状态】
            if (ACTION_getFpzw_JDFPMX.equals(params.get("ACTION"))) {
                Map<String, String> paramMap = new HashMap<>(params);
                params.put("ACTION", ACTION_getFpzw);
//              httpTaxPostResponse(params, urlPath, object, "0");// 返回标识，0返回成功
                Response<Object> getFpzw_object = httpTaxPostCheckFpxx(params, urlPath, "0");
                JSONObject json = JSON.parseObject(getFpzw_object.getPayload().toString());
                JSONArray jsonArr = JSON.parseArray(json.getString("rtn"));
                if (null != jsonArr && jsonArr.size() > 0) {
                    JSONObject map = JSON.parseObject(jsonArr.get(0).toString());
                    String nsrmc = map.getString("NSRMC");
                    String nsrzt_mc = map.getString("NSRZT_MC");
                    // 2.查询开具信息并处理 参数
                    paramMap.put("ACTION", ACTION_getFpzw_JDFPMX);
                    paramMap.remove("timestamp");
//                httpTaxPostResponse(paramMap, urlPath, object, "0");// 返回标识，0返回成功
                    getFpzw_object = httpTaxPostCheckFpxx(paramMap, urlPath, "0");
                    json = JSON.parseObject(getFpzw_object.getPayload().toString());
                    JSONArray list = JSON.parseArray(json.getString("rtn"));
                    JSONObject head = new JSONObject();
                    List listDate = new ArrayList();
                    JSONObject jo = null;
                    if (null != list) {
                        for (Object o : list) {
                            JSONObject sonJson = new JSONObject();
                            if (StringUtils.isNotBlank(o.toString())) {
                                jo = JSON.parseObject(o.toString());
                            }
                            sonJson.put("SPMC", jo.get("SPMC"));// 商品名称
                            sonJson.put("SPSL", jo.get("SPSL"));// 数量
                            sonJson.put("SPDJ", jo.get("SPDJ"));// 商品单价
                            sonJson.put("SPJE", jo.get("SPJE"));// 金额
                            listDate.add(sonJson);
                        }
                    }
                    if (null != jo) {
                        head.put("FPDM", jo.get("FPDM"));// 发票代码
                        head.put("FPHM", jo.get("FPHM"));// 发票号码
                        head.put("KPRQ", jo.get("KPRQ"));// 开票日期
                        head.put("FPJE", jo.get("FPJE"));// 合计金额-
                        head.put("FPZT_MC", jo.get("FPZT_MC"));// 发票状态
                        head.put("GHF_NSRMC", jo.get("GHF_NSRMC"));// 购货方纳税人名称
                        head.put("NSRSBH", jo.get("NSRSBH"));// 领购纳税人识别号
                        head.put("RQ", jo.get("RQ"));// 领购纳税人注销或转非日期
                    }
                    // -----------------------------------------------------

                    head.put("NSRMC", nsrmc);
                    head.put("NSRZT_MC", nsrzt_mc);
                    returnJson.put("code", "0");
                    returnJson.put("description", "操作成功");
                    returnJson.put("head", head);
                    returnJson.put("payload", listDate);
                } else {
                    returnJson.put("code", "0");
                    returnJson.put("description", "操作成功");
                    returnJson.put("head", new JSONObject());
                    returnJson.put("payload", new JSONArray());
                }
                logger.info("税务——发票查询接口[getDaySpotNumber]出参：" + JSON.toJSONString(returnJson));
            } else {
//                return  httpTaxPostCheckFpxx(params, urlPath, "0");
                Response<Object> getFpzw_object = httpTaxPostCheckFpxx(params, urlPath, "0");
                returnJson.put("code", getFpzw_object.getCode());
                returnJson.put("description", getFpzw_object.getDescription());
                returnJson.put("lastUpdateTime", System.currentTimeMillis());
                returnJson.put("payload", getFpzw_object.getPayload());

            }

            logger.info("税务——发票查询接口[getDaySpotNumber]出参：" + JSON.toJSONString(object));
        } catch (Exception e) {
            logger.error("调用发票查询接口 接口调用异常" + e.getMessage(), e);
            returnJson.put("code", "500");
            returnJson.put("description", "内部服务错误");
            returnJson.put("lastUpdateTime", System.currentTimeMillis());
            return returnJson;

        }
        return returnJson;
    }
    @SuppressWarnings({"rawtypes"})
    @RequestMapping("/ybnsrzgcx")
    @ApiOperation("一般纳税人资格查询")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType = "query", name = "params", value = "查询参数JSON格式", required = true, dataType = "Map")})
    public Response findYbnsrzgxx(@RequestParam Map params, HttpServletRequest request, HttpServletResponse response)
        throws GeneralException {
        logger.info("一般纳税人资格查询接口 入参" + params);
        Response<Object> object = new Response<>();
        try {
            if (isNull(params.get("NSRSBH"))) {
                logger.error("一般纳税人资格查询 缺失纳税人识别号");
                object.setCode(500);
                object.setDescription("请输入纳税人识别号");
                object.setLastUpdateTime(System.currentTimeMillis());
                return object;
            }
            String urlPath = environment.getProperty("tax.url.findYbnsrzgxx");
            logger.info("读取到调用一般纳税人资格查询接口接口地址：" + urlPath);
            // 加入时间戳参数
            httpTaxPostResponse(params, urlPath, object, "1");// 返回值 // 数据说明： 1：成功 -1：查询失败 公用方法
            logger.info("税务——一般纳税人资格查询接口[getDaySpotNumber]出参：" + JSON.toJSONString(object));
        } catch (Exception e) {
            logger.error("调用一般纳税人资格查询接口 接口调用异常" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
            object.setLastUpdateTime(System.currentTimeMillis());
        }
        return object;
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

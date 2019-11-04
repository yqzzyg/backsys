package com.neusoft.mid.ec.api.controller.socialsecurity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.serviceInterface.socialsecurity.SocialSecurityCardQueryService;
import com.neusoft.mid.ec.api.serviceInterface.socialsecurity.SocialSecurityQueryService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 社保查询
 */
@RestController
@RequestMapping("/rs/query")
public class SocialSecurityQueryController extends BaseController   {
    @Autowired
    SocialSecurityCardQueryService socialSecurityCardQueryService;


    @ApiOperation(value = "人员基本信息查询")
    @RequestMapping(value = "/personInfo",method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "query")
    public Response personInfo(@RequestBody Map params, HttpServletRequest request){
           Response<Object> object = new Response<>();

        try {
            Map<String, Object> mapHeaders = getMapHeaders(request);
            String idno = (String) mapHeaders.get("idno");
            String name = (String) mapHeaders.get("name");
            if (StringUtils.isEmpty(idno)||StringUtils.isEmpty(name)){
                object.setCode(1);
                object.setDescription("姓名和证件号码不能为空");
                return  object;
            }
            params.put("account",idno);
            params.put("name",name);
            params.put("aac147",idno);
            String result = socialSecurityCardQueryService.getPeron(params,mapHeaders);
            if(StringUtils.isEmpty(result)){
                return ResponseHelper.createResponse(-9999,"查询失败");
            }
            JSONArray jsonArray = JSONArray.parseArray(result);



            return ResponseHelper.createSuccessResponse(jsonArray);
        } catch (Exception e) {
            this.logger.error("getCard error",e);
            logger.info("调用人员基本信息查询为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }

    public Response errorRsponse(Response obj, int code, String msg) {
        obj.setCode(code);
        obj.setLastUpdateTime(System.currentTimeMillis());
        obj.setDescription(msg);
        logger.info("户政查询参数检验为空打印：" + obj);
        return obj;
    }

    @ApiOperation(value = "人员参保信息查询")
    @RequestMapping(value = "/inuredInfo",method = RequestMethod.POST )
    @ApiImplicitParam(paramType = "query")
    public Response inuredInfo(@RequestBody Map params,HttpServletRequest request){
        Response<Object> object = new Response<>();
        try {
            Map<String, Object> mapHeaders = getMapHeaders(request);
            String idno = (String) mapHeaders.get("idno");
            String name = (String) mapHeaders.get("name");
            if (StringUtils.isEmpty(idno)||StringUtils.isEmpty(name)){
                object.setCode(1);
                object.setDescription("姓名和证件号码不能为空");
                return  object;
            }
            params.put("account",idno);
            params.put("name",name);
            params.put("aac147",idno);
            String result = socialSecurityCardQueryService.getinuredInfo(params,mapHeaders);
            if(result == null){
                return ResponseHelper.createResponse(-9999,"查询失败");
            }
            JSONArray jsonArray = JSONArray.parseArray(result);

            return ResponseHelper.createSuccessResponse(jsonArray);
        } catch (Exception e) {
            this.logger.error("inuredInfo error",e);
            logger.info("调用人员参保信息查询为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }


    @ApiOperation(value = "养老账户信息查询")
    @RequestMapping(value = "/accountInfo" ,method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "query")
    public Response accountInfo(@RequestBody Map params,HttpServletRequest request) {
           Response<Object> object = new Response<>();
        try {
            if (null == params || params.size() == 0) {
                logger.info("调用养老账户信息查询接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if(null==params.get("aac001")||null==params.get("startTime")||null==params.get("stopTime")||null==params.get("aae140")){
                logger.info("调用养老账户信息查询接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            Map<String, Object> mapHeaders = getMapHeaders(request);
            String idno = (String) mapHeaders.get("idno");
            String name = (String) mapHeaders.get("name");
            if (StringUtils.isEmpty(idno)||StringUtils.isEmpty(name)){
                object.setCode(1);
                object.setDescription("姓名和证件号码不能为空");
                return  object;
            }
            params.put("account",idno);
            params.put("name",name);
            String result = socialSecurityCardQueryService.getaccountInfo(params, mapHeaders);
            if (result == null) {
                return ResponseHelper.createResponse(-9999, "查询失败");
            }
            JSONObject jsonObject = JSONObject.parseObject(result);


            return ResponseHelper.createSuccessResponse(jsonObject);
        } catch (Exception e) {
            this.logger.error("accountInfo error", e);
            logger.info("调用人员参保信息查询为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }



    @ApiOperation(value = "个人缴费明细查询")
    @RequestMapping(value = "/payInfo" ,method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "query")
    public Response payInfo(@RequestBody Map params,HttpServletRequest request){
          Response<Object> object = new Response<>();
        try {
            if (null == params || params.size() == 0) {
                logger.info("调用个人缴费明细查询接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if(null==params.get("aac001")||null==params.get("startTime")||null==params.get("stopTime")||null==params.get("aae140")||null==params.get("type")){
                logger.info("调用个人缴费明细查询接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            Map<String, Object> mapHeaders = getMapHeaders(request);
            String idno = (String) mapHeaders.get("idno");
            String name = (String) mapHeaders.get("name");
            if (StringUtils.isEmpty(idno)||StringUtils.isEmpty(name)){
                object.setCode(1);
                object.setDescription("姓名和证件号码不能为空");
                return  object;
            }
            params.put("account",idno);
            params.put("name",name);

            String result = socialSecurityCardQueryService.getpayInfo(params, mapHeaders);
            if (result == null) {
                return ResponseHelper.createResponse(-9999, "查询失败");
            }
            JSONObject jsonObject = JSONObject.parseObject(result);


            return ResponseHelper.createSuccessResponse(jsonObject);
        } catch (Exception e) {
            this.logger.error("payInfo error", e);
            logger.info("调用个人缴费明细查询为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }


    @ApiOperation(value = "失业待遇发放信息查询")
    @RequestMapping(value = "/lostInfo",method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "query")
    public Response lostInfo(@RequestBody Map params,HttpServletRequest request){
           Response<Object> object = new Response<>();
        try {
            if (null == params || params.size() == 0) {
                logger.info("调用失业待遇发放信息查询接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if(null==params.get("aac001")||null==params.get("aae140")){
                logger.info("调用失业待遇发放信息查询接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            Map<String, Object> mapHeaders = getMapHeaders(request);
            String idno = (String) mapHeaders.get("idno");
            String name = (String) mapHeaders.get("name");
            if (StringUtils.isEmpty(idno)||StringUtils.isEmpty(name)){
                object.setCode(1);
                object.setDescription("姓名和证件号码不能为空");
                return  object;
            }
            params.put("account",idno);
            params.put("name",name);
            String result = socialSecurityCardQueryService.getlostInfo(params, mapHeaders);
            if (result == null) {
                return ResponseHelper.createResponse(-9999, "查询失败");
            }

            JSONObject jsonObject = JSONObject.parseObject(result);


            return ResponseHelper.createSuccessResponse(jsonObject);
        } catch (Exception e) {
            this.logger.error("lostInfo error", e);
            logger.info("调用失业待遇发放信息查询为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        return object;
    }


    @ApiOperation(value = "养老待遇发放信息查询")
    @RequestMapping(value = "/oldInfo",method = RequestMethod.POST)
    @ApiImplicitParam(paramType = "query")
    public Response oldInfo(@RequestBody Map params,HttpServletRequest request){
          Response<Object> object = new Response<>();
        try {
            if (null == params || params.size() == 0) {
                logger.info("调用养老待遇发放信息查询接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            if(null==params.get("aac001")||null==params.get("startTime")||null==params.get("stopTime")||null==params.get("aae140")){
                logger.info("调用养老账户信息查询接口入参为空");
                return errorRsponse(object, 1, "请求参数为空！");
            }
            Map<String, Object> mapHeaders = getMapHeaders(request);
            String idno = (String) mapHeaders.get("idno");
            String name = (String) mapHeaders.get("name");
            if (StringUtils.isEmpty(idno)||StringUtils.isEmpty(name)){
                object.setCode(1);
                object.setDescription("姓名和证件号码不能为空");
                return  object;
            }
            params.put("account",idno);
            params.put("name",name);
            String result = socialSecurityCardQueryService.getoldInfo(params, mapHeaders);
            if (result == null) {
                return ResponseHelper.createResponse(-9999, "查询失败");
            }

            JSONObject jsonObject = JSONObject.parseObject(result);


            return ResponseHelper.createSuccessResponse(jsonObject);
        } catch (Exception e) {
            this.logger.error("oldInfo error", e);
            logger.info("调用养老待遇发放信息查询为空,入参为：[{}]", params);
            object.setCode(500);
            object.setDescription("内部服务错误");        }
        return object;
    }
}

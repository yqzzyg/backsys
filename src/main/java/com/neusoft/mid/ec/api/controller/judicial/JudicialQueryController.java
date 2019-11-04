package com.neusoft.mid.ec.api.controller.judicial;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.domain.RequestInfo;
import com.neusoft.mid.ec.api.serviceInterface.judicial.JudicialQueryService;
import com.neusoft.mid.ec.api.shareplatform.ServiceInvocation;
import com.neusoft.mid.ec.api.shareplatform.SymmetricEncoder;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 云政司法查询业务
 */
@RestController
@RequestMapping("/judicial/query")
public class JudicialQueryController extends BaseController   {
    private String POST = "POST";
    private String GET = "GET";
    @Value("${sp.appId}")
    private  String appId;
    @Value("${sp.appKey}")
    private  String appKey;
    @Value("${sp.url.token}")
    private  String tokenUrl;
    @Value("${jc.apply.url.queryInfoData}")
    private String getTransDataUrl;
  



    /**
     * 基本信息查询
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @GetMapping("/queryInfoData")
    public Response queryInfoData(@RequestParam Map<String, String> params, HttpServletRequest request){
        return doExecute("基本信息查询", getTransDataUrl, GET, params, request);
    }



    /**
     * 调用接口：POST params=Map<String, Object>；GET params=Map<String, String>
     * @param params
     * @param request
     * @param
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Response doExecute(String functionName, String urlPath, String method, Map params, HttpServletRequest request) {
        Response<Object> object = new Response<>();
        long startTime = System.currentTimeMillis();
        logger.info(functionName + "接口调用入参：" + params);
        try {
            // 取得 accessToken后解密；调用服务；创建headMap
            String accessToken = ServiceInvocation.getToken(appId,appKey,tokenUrl);
            String secretKey = SymmetricEncoder.AESDncode(appKey, accessToken);
            String sign1 = ServiceInvocation.gatewaySignEncode(appId, secretKey, String.valueOf(startTime));
            Map<String,Object> headMap = ServiceInvocation.buildBaseHeader(appId, String.valueOf(startTime), sign1);
            // 接口调用
            String response;
            if(StringUtils.equals(GET, method)) {
                response = HttpRequestUtil.URLGet(urlPath, params, HttpRequestUtil.UTF8, headMap);
            }else {
                response = HttpRequestUtil.URLPost(urlPath, params, headMap);
            }
            // 调用结果
            if (StringUtils.isNotBlank(response)) {
                long endTime = System.currentTimeMillis();
                logger.info("司法厅接口用时：" + (endTime-startTime) + "；调用出参：" + response);
                JSONObject json = JSONObject.parseObject(response);
                String code = json.getString("result");
                String msg = json.getString("resultmsg");
                msg = StringUtils.isBlank(msg)?codeMap().get(code):msg;
                object.setCode(Integer.valueOf(code));
                object.setDescription(msg);
            } else {
                logger.info("司法厅接口调用异常出参：" + response);
                object.setCode(500);
                object.setDescription("内部服务错误");
            }
        } catch (Exception e) {
            logger.error(functionName + "错误日志" + e.getMessage(), e);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        long endTime = System.currentTimeMillis();
        object.setLastUpdateTime(endTime);
        logger.info(functionName + "接口调用用时："+ (endTime-startTime) +"；出参：" + object);
        return object;
    }

    private Map<String, String> codeMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("01", "成功！");
        map.put("02", "失败！");
        map.put("99", "其他！");
        map.put("21", "必要的参数不能为空！");
        map.put("22", "参数格式错误！");
        map.put("23", "当时数据版本错误！");
        map.put("24", "参数之间的数据版本不一致！");
        map.put("25", "办件信息不存在！");
        map.put("97", "系统异常！");
        map.put("98", "无返回值！");
        return map;
    }




}

package com.neusoft.mid.ec.api.controller.judicial;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.shareplatform.ServiceInvocation;
import com.neusoft.mid.ec.api.shareplatform.SymmetricEncoder;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;

import me.puras.common.json.Response;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 司法厅接口 
 */
@RestController
@RequestMapping("/judicial/apply")
public class JudicialApplyController extends BaseController  {
	
	private String POST = "POST";
	private String GET = "GET";
	@Value("${sp.appId}")
    private  String appId;
    @Value("${sp.appKey}")
    private  String appKey;
    @Value("${sp.url.token}")
    private  String tokenUrl;
    @Value("${jc.apply.url.registerUrl}")
    private String registerUrl;
    @Value("${jc.apply.url.nodeinfoUrl}")
    private String nodeinfoUrl;
    @Value("${jc.apply.url.specialStartUrl}")
    private String specialStartUrl;
    @Value("${jc.apply.url.getAcceptDataUrl}")
    private String getAcceptDataUrl;
    @Value("${jc.apply.url.getTransDataUrl}")
    private String getTransDataUrl;
    @Value("${jc.apply.url.queryInfoData}")
    private String queryInfoData;
    @Value("${jc.apply.url.patchApplyUrl}")
    private String patchApplyUrl;
    @Value("${jc.apply.url.getSpecialEndUrl}")
    private String getSpecialEndUrl;
    @Value("${jc.apply.url.forminfoUrl}")
    private String forminfoUrl;
    @Value("${jc.apply.url.patchEndAcceptUrl}")
    private String patchEndAcceptUrl;
    
	/**
	 * 收件数据接收
	 * @param params
	 * @param request
	 * @return
	 */
    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public Response register(@RequestParam Map<String, Object> params, HttpServletRequest request){
        return doExecute("收件数据接收", registerUrl, POST, params, request);
    }
	
    /**
     * 办件环节信息接收
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/nodeinfo")
    public Response nodeinfo(@RequestParam Map<String, Object> params, HttpServletRequest request){
        return doExecute("办件环节信息接收", nodeinfoUrl, POST, params, request);
    }
    
    /**
     * 特别程序开始
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
	@PostMapping("/specialStart")
    public Response specialStart(@RequestParam Map<String, Object> params, HttpServletRequest request){
        return doExecute("特别程序开始", specialStartUrl, POST, params, request);
    }
    
    /**
     * 受理数据接收
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/getAcceptData")
    public Response getAcceptData(@RequestParam Map<String, Object> params, HttpServletRequest request){
        return doExecute("受理数据接收", getAcceptDataUrl, POST, params, request);
    }
    
    /**
     * 办结数据接收
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/getTransData")
    public Response getTransData(@RequestParam Map<String, Object> params, HttpServletRequest request){
        return doExecute("办结数据接收", getTransDataUrl, POST, params, request);
    }
    
    /**
     * 补件数据接收
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/patchApply")
    public Response patchApply(@RequestParam Map<String, Object> params, HttpServletRequest request){
        return doExecute("补件数据接收", patchApplyUrl, POST, params, request);
    }
    
    /**
     * 特别程序结束
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/getSpecialEnd")
    public Response getSpecialEnd(@RequestParam Map<String, Object> params, HttpServletRequest request){
        return doExecute("特别程序结束", getSpecialEndUrl, POST, params, request);
    }
    
    /**
     * 表单数据接收
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @PostMapping("/forminfo")
    public Response forminfo(@RequestParam Map<String, Object> params, HttpServletRequest request){
        return doExecute("表单数据接收", forminfoUrl, POST, params, request);
    }
    
    /**
     * 补齐补正结束
     * @param params
     * @param request
     * @return
     */
    @SuppressWarnings("rawtypes")
    @GetMapping("/patchEndAccept")
    public Response patchEndAccept(@RequestParam Map<String, String> params, HttpServletRequest request){
        return doExecute("补齐补正结束", patchEndAcceptUrl, GET, params, request);
    }
    
	/**
	 * 调用接口：POST params=Map<String, Object>；GET params=Map<String, String>
	 * @param params
	 * @param request
	 * @param object
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Response doExecute(String functionName, String urlPath, String method, Map params, HttpServletRequest request) {
    	Response<Object> object = new Response<>();
    	long startTime = System.currentTimeMillis();
    	logger.info(functionName + "接口调用入参：" + params);
        try {
        	// 取得 accessToken后解密；调用服务；创建headMap
        	String accessToken =ServiceInvocation.getToken(appId,appKey,tokenUrl);
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

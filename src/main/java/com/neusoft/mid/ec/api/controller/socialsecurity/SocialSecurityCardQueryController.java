package com.neusoft.mid.ec.api.controller.socialsecurity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayCommerceMedicalCardQueryRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipayCommerceMedicalCardQueryResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.domain.RequestInfo;
import com.neusoft.mid.ec.api.serviceInterface.socialsecurity.SocialSecurityCardQueryService;
import com.neusoft.mid.ec.api.util.JedisClusterUtil;
import com.neusoft.mid.ec.api.util.ValidateUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;
import net.sf.json.xml.XMLSerializer;

/**
 * @author Administrator
 * 社保卡
 */
@Api(value = "人社(社保卡查询)", description = "社保卡查询")
@RestController
@RequestMapping("/rs/sbk/query")
public class SocialSecurityCardQueryController extends BaseController {

    @Autowired
    SocialSecurityCardQueryService socialSecurityCardQueryService;
    @Autowired
    private Environment environment;
    @Value("${alipay.auth.url}")
    private String alipayAuthUrl;
    @Value("${alipay.auth.scope}")
    private String alipayAuthScope;
    @Value("${alipay.auth.redirect.uri}")
    private String alipayAuthRedirectUri;
    @Value("${alipay.server.url}")
    private String alipayServerUrl;
    @Value("${alipay.format}")
    private String alipayFormat;
    @Value("${alipay.charset}")
    private String alipayCharset;
    @Value("${alipay.public.key}")
    private String alipayPublicKey;
    @Value("${alipay.sign.type}")
    private String alipaySignType;
    @Value("${alipay.app.id}")
    private String alipayAppId;
    @Value("${alipay.app.private.key}")
    private String alipayAppPrivateKey;

    @Autowired
    private JedisClusterUtil jedisClusterUtil;
    private AlipayClient alipayClient;

    @PostConstruct
    private void init() {
        alipayClient = new DefaultAlipayClient(alipayServerUrl, alipayAppId, alipayAppPrivateKey, alipayFormat,
            alipayCharset, alipayPublicKey, alipaySignType);
    }

    @ApiOperation(value = "获取社保卡状态")
    @GetMapping(value = "/cardInfo")
    @ApiImplicitParam(paramType = "query")
    public Response getCard(HttpServletRequest request) {
        RequestInfo reqInfo = getRequestInfo(request);
        if (StringUtils.isEmpty(reqInfo.getIdno())) {
            return ResponseHelper.createResponse(-9999, "身份证号不能为空");
        }
        if (StringUtils.isEmpty(reqInfo.getName())) {
            return ResponseHelper.createResponse(-9999, "姓名不能为空");
        }
        try {
            String result = socialSecurityCardQueryService.getCard(reqInfo.getIdno(), reqInfo.getName());

            if (result == null) {
                return ResponseHelper.createResponse(-9999, "查询失败");
            }

            Map map = new HashMap(10);
            map.put("state", result.replaceAll("ok", "正常"));
            map.put("idno", ValidateUtils.idMask(reqInfo.getIdno(), 1, 1));
            map.put("name", reqInfo.getName());
            return ResponseHelper.createSuccessResponse(map);
        } catch (Exception e) {
            logger.error("getCard error", e);
        }
        return ResponseHelper.createResponse(-9999, "查询失败");
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "获取社保卡基本信息")
    @RequestMapping(value = "/cardBasicInfo")
    @ApiImplicitParam(paramType = "query")
    public Response getCardBasicQuery(HttpServletRequest request) {
        RequestInfo reqInfo = getRequestInfo(request);
        if (StringUtils.isEmpty(reqInfo.getIdno())) {
            return ResponseHelper.createResponse(-9999, "身份证号不能为空");
        }
        if (StringUtils.isEmpty(reqInfo.getName())) {
            return ResponseHelper.createResponse(-9999, "姓名不能为空");
        }
        try {
            String result = socialSecurityCardQueryService.getCardBasicInfo(reqInfo.getIdno(), reqInfo.getName());

            if (result == null) {
                return ResponseHelper.createResponse(-9999, "查询失败");
            }
            String xml="<?xml version='1.0' encoding='UTF-8'?><a>"+result+"</a>";
            XMLSerializer xmlSerializer = new XMLSerializer();
		    String resutStr = xmlSerializer.read(xml).toString();
		    net.sf.json.JSONObject resultjson = net.sf.json.JSONObject.fromObject(resutStr);
		    String err=resultjson.getString("ERR");
		    if (!"OK".equals(err)) {
		    	return ResponseHelper.createResponse(-9999, err);
			}
            Map map = new HashMap(10);
            map.put("result",resultjson);
            map.put("idno",reqInfo.getIdno());
            map.put("name", reqInfo.getName());
            return ResponseHelper.createSuccessResponse(map);
        } catch (Exception e) {
            logger.error("getCardBasicQuery error", e);
        }
        return ResponseHelper.createResponse(-9999, "查询失败，请稍候重试！");
    }
    @ApiOperation(value = "验证获取社保卡状态")
//    @GetMapping(value = "/checkcardInfo")
    @RequestMapping("/checkcardInfo")
    @ApiImplicitParam(paramType = "query")
    public Response getCardcheck(HttpServletRequest request) {
//        RequestInfo reqInfo = getRequestInfo(request);
        logger.info("验证获取社保卡状态" + request.getParameterMap());
        String idno = request.getParameter("idno");
        String name = request.getParameter("name");
        if (StringUtils.isEmpty(idno)) {
            return ResponseHelper.createResponse(-9999, "身份证号不能为空");
        }
        if (StringUtils.isEmpty(name)) {
            return ResponseHelper.createResponse(-9999, "姓名不能为空");
        }
        try {
            String result = socialSecurityCardQueryService.getCard(idno, name);

            if (result == null) {
                return ResponseHelper.createResponse(-9999, "查询失败");
            }

            Map map = new HashMap(10);
            map.put("state", result.replaceAll("ok", "正常"));
            map.put("idno", idno);
            map.put("name", name);
            return ResponseHelper.createSuccessResponse(map);
        } catch (Exception e) {
            logger.error("测试类getCard error", e);
        }
        return ResponseHelper.createResponse(-9999, "查询失败");
    }

    /**
     * 支付宝授权地址
     * @return
     */
    @RequestMapping("/alipayAuth")
    private Response<Object> alipayAuth() {
        String authUrl = alipayAuthUrl + "?app_id=" + alipayAppId + "&scope=" + alipayAuthScope + "&redirect_uri="
            + alipayAuthRedirectUri;
        Response<Object> object = new Response<>();
        object.setCode(0);
        object.setDescription("成功");
        object.setPayload(authUrl);
        object.setLastUpdateTime(System.currentTimeMillis());
        return object;
    }

    /**
     * 支付宝授权回调
     * @param app_id
     * @param scope
     * @param auth_code
     * @return
     */
    @RequestMapping("/alipayAuthRedirect")
    private Response<Object> alipayAuthRedirect(String app_id, String scope, String auth_code) {
        Response<Object> object = new Response<>();
        try {
            // 请求参数
            AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
            request.setCode(auth_code);
            request.setGrantType("authorization_code");
            // 调用接口
            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(request);
            // redis缓存
            String userId = oauthTokenResponse.getUserId();
            String accessToken = oauthTokenResponse.getAccessToken();
            String expireTime = oauthTokenResponse.getExpiresIn();
            jedisClusterUtil.setWithExpireTime("alipay", "socialSecurityCardUserId", userId,
                Integer.valueOf(expireTime));
            jedisClusterUtil.setWithExpireTime("alipay", "socialSecurityCardAccessToken", accessToken,
                Integer.valueOf(expireTime));
            //
            object.setCode(0);
            object.setDescription("成功");
        } catch (AlipayApiException e) {
            logger.error("电子社保卡签发查询授权异常", e);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        object.setLastUpdateTime(System.currentTimeMillis());
        return object;
    }

    /**
     * 电子社保卡签发查询
     * @param app_id
     * @param scope
     * @param auth_code
     * @return
     */
    @RequestMapping("/alipayCardQuery")
    private Response<Object> alipayCardQuery(String app_id, String scope, String auth_code) {
        Response<Object> object = new Response<>();
        try {
            // 请求参数
            AlipayCommerceMedicalCardQueryRequest request = new AlipayCommerceMedicalCardQueryRequest();
            String userId = jedisClusterUtil.get("alipay", "socialSecurityCardUserId");
            String accessToken = jedisClusterUtil.get("alipay", "socialSecurityCardAccessToken");
            Map<String, String> params = new HashMap<>();
            params.put("return_url", "http://www.alipay.com");
            params.put("scene", "bar_code,wave_code,face_code");
            params.put("buyer_id", userId);
            params.put("auth_code", accessToken);
            params.put("extend_params", "foreigner");
            params.put("card_org_no", "00011");
            request.setBizContent(JSON.toJSONString(params));
            // 调用接口
            AlipayCommerceMedicalCardQueryResponse response = alipayClient.execute(request);
            //
            if (response.isSuccess()) {
                System.out.println("调用成功");
            } else {
                System.out.println("调用失败");
            }
            object.setCode(0);
            object.setDescription("成功");
        } catch (AlipayApiException e) {
            logger.error("电子社保卡签发查询异常", e);
            object.setCode(500);
            object.setDescription("内部服务错误");
        }
        object.setLastUpdateTime(System.currentTimeMillis());
        return object;
    }
}

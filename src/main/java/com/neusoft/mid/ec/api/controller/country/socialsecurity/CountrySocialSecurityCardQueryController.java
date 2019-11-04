package com.neusoft.mid.ec.api.controller.country.socialsecurity;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.serviceInterface.socialsecurity.SocialSecurityCardQueryService;
import com.neusoft.mid.ec.api.util.JedisClusterUtil;
import com.neusoft.mid.ec.api.util.ValidateUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;

/**
 * @author Administrator
 * 社保卡
 */
@Api(value = "人社(社保卡查询)", description = "社保卡查询")
@RestController
@RequestMapping("/country/rs/sbk/query")
public class CountrySocialSecurityCardQueryController extends BaseController {

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
    @PostMapping(value = "/cardInfo")
    @ApiImplicitParam(paramType = "query")
    public Response getCard(@RequestParam Map<String, Object> params,HttpServletRequest request) {
        //RequestInfo reqInfo = getRequestInfo(request);
        if (StringUtils.isBlank(params.get("idno")==null?null:params.get("idno").toString())) {
            return ResponseHelper.createResponse(-9999, "身份证号不能为空");
        }
        if (StringUtils.isBlank(params.get("name")==null?null:params.get("name").toString())) {
            return ResponseHelper.createResponse(-9999, "姓名不能为空");
        }
        try {
            String result = socialSecurityCardQueryService.getCard(params.get("idno")+"", params.get("name")+"");

            if (result == null) {
                return ResponseHelper.createResponse(-9999, "查询失败");
            }

            Map map = new HashMap(10);
            map.put("state", result.replaceAll("ok", "正常"));
            map.put("idno", ValidateUtils.idMask(params.get("idno")+"", 1, 1));
            map.put("name", params.get("name")+"");
            return ResponseHelper.createSuccessResponse(map);
        } catch (Exception e) {
            logger.error("getCard error", e);
        }
        return ResponseHelper.createResponse(-9999, "查询失败");
    }

  
}

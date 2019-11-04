package com.neusoft.mid.ec.api.controller.socialsecurity;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayCommerceMedicalInstcardBindRequest;
import com.neusoft.mid.ec.api.controller.BaseController;
import com.neusoft.mid.ec.api.domain.RequestInfo;
import com.neusoft.mid.ec.api.domain.socialsecurity.CardUserInfo;
import com.neusoft.mid.ec.api.domain.socialsecurity.ReportLossInfo;
import com.neusoft.mid.ec.api.serviceInterface.socialsecurity.SocialSecurityCardApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.puras.common.json.Response;
import me.puras.common.json.ResponseHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * 社保卡
 */
@Api(value = "人社(社保卡办理)",description = "社保卡办理")
@RestController
@RequestMapping("/rs/sbk/apply")
public class SocialSecurityCardApplyController  extends BaseController   {

    @Autowired
    SocialSecurityCardApplyService socialSecurityCardApplyService;

    private static final String CHANGEUSERINFO_SUCCESS_CODE = "00";

    private static final String REPORTLOSS_SUCCESS_CODE[] = {"00","01","02"};
    
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
    
    private AlipayClient alipayClient;
    
    @PostConstruct
    private void init() {
    	alipayClient = new DefaultAlipayClient(alipayServerUrl, alipayAppId, alipayAppPrivateKey, alipayFormat, alipayCharset, alipayPublicKey, alipaySignType);
    }



    @ApiOperation(value = "修改人员信息")
    @PostMapping(value = "/changeUserInfo" )
    public Response changeUserInfo(HttpServletRequest request, @RequestBody CardUserInfo userInfo){

        this.logger.info("changeUserInfo userInfo = {}",userInfo.toString());

        RequestInfo reqInfo = getRequestInfo(request);
        userInfo.setIDNo(reqInfo.getIdno());
        userInfo.setName(reqInfo.getName());

        if(StringUtils.isEmpty(userInfo.getIDNo())){
            return ResponseHelper.createResponse(-9999,"身份证号不能为空");
        }
        if(StringUtils.isEmpty(userInfo.getName())){
            return ResponseHelper.createResponse(-9999,"姓名不能为空");
        }

        try {
            String result = socialSecurityCardApplyService.changeUserInfo(userInfo);
            if(result == null){
                return ResponseHelper.createResponse(-9999,"修改人员信息失败");
            }
            if(!CHANGEUSERINFO_SUCCESS_CODE.equals(result)){
                return ResponseHelper.createResponse(-9999,"修改人员信息失败");
            }
            return ResponseHelper.createSuccessResponse(result);
        } catch (Exception e) {
            this.logger.error("changeUserInfo error",e);
        }
        return ResponseHelper.createResponse(-9999,"修改人员信息失败");
    }

    @ApiOperation(value = "社保卡挂失")
    @PostMapping(value = "/reportLoss" )
    public Response reportLoss(HttpServletRequest request, @RequestBody ReportLossInfo reportLossInfo){

        RequestInfo reqInfo = getRequestInfo(request);
        reportLossInfo.setIDNo(reqInfo.getIdno());
        reportLossInfo.setName(reqInfo.getName());

        this.logger.info("reportLoss reportLossInfo = {}",reportLossInfo.toString());

        if(StringUtils.isEmpty(reportLossInfo.getIDNo())){
            return ResponseHelper.createResponse(-9999,"身份证号不能为空");
        }
        if(StringUtils.isEmpty(reportLossInfo.getName())){
            return ResponseHelper.createResponse(-9999,"姓名不能为空");
        }
        if(StringUtils.isEmpty(reportLossInfo.getCardNo())){
            return ResponseHelper.createResponse(-9999,"社保卡号不能为空");
        }
        /*if(StringUtils.isEmpty(reportLossInfo.getCityCode())){
            return ResponseHelper.createResponse(-9999,"城市编码不能为空");
        }*/

        try {
            String result = socialSecurityCardApplyService.reportLossInfo(reportLossInfo);

            if(result == null){
                return ResponseHelper.createResponse(-9999,"挂失失败");
            }
            for(String code : REPORTLOSS_SUCCESS_CODE){
                if(code.startsWith(result)){
                    return ResponseHelper.createResponse(0,"社保卡社保账户已挂失。如您需要挂失社保卡银行账户，请到银行网点或拨打银行服务电话挂失。");
                }
            }
            return  ResponseHelper.createResponse(-9999,result);
        } catch (Exception e) {
            this.logger.error("getCard error",e);
        }
        return ResponseHelper.createResponse(-9999,"挂失失败");
    }

    /**
     * 电子社保卡签发
     * @param app_id
     * @param scope
     * @param auth_code
     * @param response
     */
    @RequestMapping("/alipayInstcardBind")
    public void alipayInstcardBind(String app_id, String scope, String auth_code, HttpServletResponse response) {
    	try {
	    	AlipayCommerceMedicalInstcardBindRequest request = new AlipayCommerceMedicalInstcardBindRequest();
	    	Map<String, Object> params = new HashMap<String, Object>();
	    	params.put("return_url", "http://www.alipay.com");
	    	params.put("ins_code", "SZHRSS");
	    	params.put("buyer_id", "310100");
	    	Map<String, String> sonParams = new HashMap<String, String>();
	    	sonParams.put("sys_service_provider_id","2088511833207846");
	    	sonParams.put("serial_no","20160101100001");
	    	sonParams.put("return_params","medical_card_no=000102020011");
	    	params.put("extend_params", sonParams);
	    	request.setBizContent(JSON.toJSONString(params));
	    	String form = alipayClient.pageExecute(request).getBody();
	    	response.setContentType("text/html;charset="+alipayCharset);
	    	response.getWriter().write(form);
	    	response.getWriter().flush();
    	} catch (Exception e) {
    		logger.error("电子社保卡签发异常", e);
		}
    }
}

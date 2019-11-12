package com.neusoft.mid.ec.api.service.housingconstruction;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.exception.GeneralException;
import com.neusoft.mid.ec.api.serviceInterface.housingconstruction.HousingConstructionService;
import com.neusoft.mid.ec.api.shareplatform.ServiceInvocation;
import com.neusoft.mid.ec.api.shareplatform.SymmetricEncoder;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;

/***
 *住建
 */
@Service
public class HousingConstructionServiceImpl implements HousingConstructionService {

    private Logger LOG = LoggerFactory.getLogger(HousingConstructionServiceImpl.class);

    @Value("${sp.appId}")
    private String appId;
    @Value("${sp.appKey}")
    private String appKey;
    
    
    @Value("${spyp.appId}")
    private String spappId;
    @Value("${spyp.appKey}")
    private String spappKey;
    
    
    @Value("${sp.url.token}")
    private String tokenUrl;

    @Value("${sp.hc.url.c0001}")
    private String c0001Url;

    @Value("${sp.hc.url.c0002}")
    private String c0002Url;

    @Value("${sp.hc.url.c0003}")
    private String c0003Url;

    @Value("${sp.hc.url.c0004}")
    private String c0004Url;

    @Value("${sp.hc.url.c0005}")
    private String c0005Url;
    
    @Value("${sp.hc.url.c0006}")
    private String c0006Url;
    
    @Value("${sp.hc.url.c0007}")
    private String c0007Url;
    
    @Value("${sp.hc.url.c0008}")
    private String c0008Url;
    
    @Value("${sp.hc.url.c0009}")
    private String c0009Url;
    
    @Value("${sp.hc.url.c0010}")
    private String c0010Url;

    @Override
    public JSONArray getC001(String zsbh, String sfzh, String qymc) throws Exception {
        String currTime = System.currentTimeMillis() + "";
        LOG.info("getC001 request params>>>>zcbh={},zjhm={},qymc={},currTime=", zsbh, sfzh, qymc, currTime);

        String accessToken = ServiceInvocation.getToken(appId, appKey, tokenUrl);
        // 2.在获得服务调用秘钥 access_token 后，根据自己的 appKey 使用 AES 解密算法对返回值进行解密，最终获得真正秘钥的过程。
        String secretKey = SymmetricEncoder.AESDncode(appKey, accessToken);
        // 3.调用服务，获取服务的 json 数据。
        String sign1 = ServiceInvocation.gatewaySignEncode(appId, secretKey, currTime);
        Map queryParams = new HashMap(5);
        queryParams.put("zsbh", zsbh);
        queryParams.put("sfzh", sfzh);
        queryParams.put("qymc", qymc);
        String result = HttpRequestUtil.URLGet(c0004Url, queryParams, "utf-8", ServiceInvocation.buildBaseHeader(appId, currTime, sign1));

        LOG.info("getC001 request  result >>>>{}", result);

        if (result == null) {
            LOG.info("调用接口错误");
            throw new GeneralException("安全生产考核合格证书信息查询 getC001 获取错误");
        }

        JSONObject serviceJsonData = JSONObject.parseObject(result);

        if (serviceJsonData.getBoolean("flag")) {
            JSONObject resultObj = new JSONObject();
            JSONArray succMsg = JSON.parseArray(serviceJsonData.getString("succMsg"));
            resultObj.put("succMsg",succMsg);
            return succMsg;
        }
        LOG.info("接口调用成功但返回错误信息");
        throw new GeneralException("查询失败");
    }


    @Override
    public JSONArray getC002(String zjhm, String zsbh) throws Exception {
        String currTime = System.currentTimeMillis() + "";
        LOG.info("getC002 request params>>>>zcbh={},sfzh={},currTime=", zsbh, zjhm, currTime);
        String accessToken = ServiceInvocation.getToken(appId, appKey, tokenUrl);
        // 2.在获得服务调用秘钥 access_token 后，根据自己的 appKey 使用 AES 解密算法对返回值进行解密，最终获得真正秘钥的过程。
        String secretKey = SymmetricEncoder.AESDncode(appKey, accessToken);
        // 3.调用服务，获取服务的 json 数据。
        String sign1 = ServiceInvocation.gatewaySignEncode(appId, secretKey, currTime);
        Map queryParams = new HashMap(5);
        queryParams.put("zsbh", zsbh);
        queryParams.put("sfzh", zjhm);
        String result = HttpRequestUtil.URLGet(c0005Url, queryParams, "utf-8", ServiceInvocation.buildBaseHeader(appId, currTime, sign1));

        LOG.info("getC002 request  result >>>>{}", result);

        if (result == null) {
            LOG.info("调用接口错误");
            throw new GeneralException("建筑施工特种作业操作资格证书信息查询 getC002 获取错误");
        }

        JSONObject serviceJsonData = JSONObject.parseObject(result);

        if (serviceJsonData.getBoolean("flag")) {
            JSONObject resultObj = new JSONObject();
            JSONArray succMsg = JSON.parseArray(serviceJsonData.getString("succMsg"));
            resultObj.put("succMsg",succMsg);
            return succMsg;
        }
        LOG.info("接口调用成功但返回错误信息");
        throw new GeneralException("查询失败");
    }


    @Override
    public JSONObject getC003(String zcbh, String zjhm, String qymc) throws Exception {
        String currTime = System.currentTimeMillis() + "";
        LOG.info("getC003 request params>>>>zcbh={},zjhm={},qymc={},currTime=", zcbh, zjhm, qymc, currTime);

        String accessToken = ServiceInvocation.getToken(appId, appKey, tokenUrl);
        // 2.在获得服务调用秘钥 access_token 后，根据自己的 appKey 使用 AES 解密算法对返回值进行解密，最终获得真正秘钥的过程。
        String secretKey = SymmetricEncoder.AESDncode(appKey, accessToken);
        // 3.调用服务，获取服务的 json 数据。
        String sign1 = ServiceInvocation.gatewaySignEncode(appId, secretKey, currTime);
        Map queryParams = new HashMap(5);
        queryParams.put("zjhm", zjhm);
        queryParams.put("zcbh", zcbh);
        queryParams.put("qymc", qymc);
        String result = HttpRequestUtil.URLGet(c0003Url, queryParams, "utf-8", ServiceInvocation.buildBaseHeader(appId, currTime, sign1));

        LOG.info("getC003 request  result >>>>{}", result);

        if (result == null) {
            LOG.info("调用接口错误");
            throw new GeneralException("二级建造师证书信息查询 getC003 获取错误");
        }

        JSONObject serviceJsonData = JSONObject.parseObject(result);

        if (serviceJsonData.getBoolean("flag")) {
            JSONObject resultObj = new JSONObject();
            //JSONArray succMsg = JSON.parseArray(serviceJsonData.getString("succMsg"));
           // resultObj.put("succMsg",succMsg);
           // return succMsg;
            JSONArray succMsg = JSON.parseArray(serviceJsonData.getString("succMsg"));
            if (succMsg != null && !succMsg.isEmpty()) {
               for(int i=0;i<succMsg.size();i++){
                    if(zcbh.equals(succMsg.getJSONObject(i).getString("zcbh"))){
                        return succMsg.getJSONObject(i);
                    }
                }
            }
        }
        LOG.info("接口调用成功但返回错误信息");
        throw new GeneralException("查询失败");
    }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getC006(String SHXYDM) throws Exception {
		 String currTime = System.currentTimeMillis() + "";
	        LOG.info("getC006 request params>>>>SHXYDM={},currTime=", SHXYDM, currTime);
	        String accessToken = ServiceInvocation.getToken(spappId, spappKey, tokenUrl);
	        // 2.在获得服务调用秘钥 access_token 后，根据自己的 appKey 使用 AES 解密算法对返回值进行解密，最终获得真正秘钥的过程。
	        String secretKey = SymmetricEncoder.AESDncode(spappKey, accessToken);
	        // 3.调用服务，获取服务的 json 数据。
	        String sign1 = ServiceInvocation.gatewaySignEncode(spappId, secretKey, currTime);
	        Map queryParams = new HashMap(5);
	        queryParams.put("SHXYDM", SHXYDM);
	        String result = HttpRequestUtil.URLGet(c0006Url, queryParams, "utf-8", ServiceInvocation.buildBaseHeader(spappId, currTime, sign1));

	        LOG.info("getC006 request  result >>>>{}", result);

	        if (result == null) {
	            LOG.info("调用接口错误");
	            throw new GeneralException("化妆品许可证信息查询 getC006 获取错误");
	        }

	        JSONObject serviceJsonData = JSONObject.parseObject(result);

	        if (serviceJsonData.getBoolean("flag")) {
	            JSONObject resultObj = new JSONObject();
	            JSONArray succMsg = JSON.parseArray(serviceJsonData.getString("succMsg"));
	            resultObj.put("succMsg",succMsg);
	            return succMsg;
	        }
	        LOG.info("化妆品许可证信息查询接口调用成功但返回错误信息");
	        throw new GeneralException("化妆品许可证信息查询失败");
	}


	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public JSONArray getC007(String xzqh, String xkzbh, String comMc) throws Exception {
		String currTime = System.currentTimeMillis() + "";
        LOG.info("getC007 request params>>>>xzqh={},xkzbh={},comMc={},currTime={}", xzqh,xkzbh,comMc, currTime);
        String accessToken = ServiceInvocation.getToken(spappId, spappKey, tokenUrl);
        // 2.在获得服务调用秘钥 access_token 后，根据自己的 appKey 使用 AES 解密算法对返回值进行解密，最终获得真正秘钥的过程。
        String secretKey = SymmetricEncoder.AESDncode(spappKey, accessToken);
        // 3.调用服务，获取服务的 json 数据。
        String sign1 = ServiceInvocation.gatewaySignEncode(spappId, secretKey, currTime);
        Map queryParams = new HashMap(5);
        queryParams.put("xzqh", xzqh);
        queryParams.put("xkzbh", xkzbh);
        queryParams.put("comMc", comMc);
        String result = HttpRequestUtil.URLGet(c0007Url, queryParams, "utf-8", ServiceInvocation.buildBaseHeader(spappId, currTime, sign1));

        LOG.info("getC007 request  result >>>>{}", result);

        if (result == null) {
            LOG.info("返回结果为null，调用接口错误");
            throw new GeneralException("食品经营许可证信息查询 getC007 获取错误");
        }

        JSONObject serviceJsonData = JSONObject.parseObject(result);

        if (serviceJsonData.getBoolean("flag")) {
            JSONObject resultObj = new JSONObject();
            JSONArray succMsg = JSON.parseArray(serviceJsonData.getString("succMsg"));
            resultObj.put("succMsg",succMsg);
            return succMsg;
        }
        LOG.info("食品经营许可证信息查询接口调用成功但返回错误信息");
        throw new GeneralException("食品经营许可证信息查询失败");
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getC008(String xzqh, String xkzbh, String comMc) throws Exception {
		String currTime = System.currentTimeMillis() + "";
        LOG.info("getC008 request params>>>>xzqh={},xkzbh={},comMc={},currTime={}", xzqh,xkzbh,comMc, currTime);
        String accessToken = ServiceInvocation.getToken(spappId, spappKey, tokenUrl);
        // 2.在获得服务调用秘钥 access_token 后，根据自己的 appKey 使用 AES 解密算法对返回值进行解密，最终获得真正秘钥的过程。
        String secretKey = SymmetricEncoder.AESDncode(spappKey, accessToken);
        // 3.调用服务，获取服务的 json 数据。
        String sign1 = ServiceInvocation.gatewaySignEncode(spappId, secretKey, currTime);
        Map queryParams = new HashMap(5);
        queryParams.put("xzqh", xzqh);
        queryParams.put("xkzbh", xkzbh);
        queryParams.put("comMc", comMc);
        String result = HttpRequestUtil.URLGet(c0008Url, queryParams, "utf-8", ServiceInvocation.buildBaseHeader(spappId, currTime, sign1));

        LOG.info("getC008 request  result >>>>{}", result);

        if (result == null) {
            LOG.info("返回结果为null，调用接口错误");
            throw new GeneralException("药品经营零售GSP许可证信息查询 getC008 获取错误");
        }

        JSONObject serviceJsonData = JSONObject.parseObject(result);

        if (serviceJsonData.getBoolean("flag")) {
            JSONObject resultObj = new JSONObject();
            JSONArray succMsg = JSON.parseArray(serviceJsonData.getString("succMsg"));
            resultObj.put("succMsg",succMsg);
            return succMsg;
        }
        LOG.info("药品经营零售GSP许可证信息查询接口调用成功但返回错误信息");
        throw new GeneralException("药品经营零售GSP许可证信息查询失败");
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getC009(String xzqh, String xkzbh, String comMc) throws Exception {
		String currTime = System.currentTimeMillis() + "";
		 LOG.info("getC008 request params>>>>xzqh={},xkzbh={},comMc={},currTime={}", xzqh,xkzbh,comMc, currTime);
        String accessToken = ServiceInvocation.getToken(spappId, spappKey, tokenUrl);
        // 2.在获得服务调用秘钥 access_token 后，根据自己的 appKey 使用 AES 解密算法对返回值进行解密，最终获得真正秘钥的过程。
        String secretKey = SymmetricEncoder.AESDncode(spappKey, accessToken);
        // 3.调用服务，获取服务的 json 数据。
        String sign1 = ServiceInvocation.gatewaySignEncode(spappId, secretKey, currTime);
        Map queryParams = new HashMap(5);
        queryParams.put("xzqh", xzqh);
        queryParams.put("xkzbh", xkzbh);
        queryParams.put("comMc", comMc);
        String result = HttpRequestUtil.URLGet(c0009Url, queryParams, "utf-8", ServiceInvocation.buildBaseHeader(spappId, currTime, sign1));

        LOG.info("getC009 request  result >>>>{}", result);

        if (result == null) {
            LOG.info("调用接口错误");
            throw new GeneralException("药品经营零售许可证信息查询 getC009 获取错误");
        }

        JSONObject serviceJsonData = JSONObject.parseObject(result);

        if (serviceJsonData.getBoolean("flag")) {
            JSONObject resultObj = new JSONObject();
            JSONArray succMsg = JSON.parseArray(serviceJsonData.getString("succMsg"));
            resultObj.put("succMsg",succMsg);
            return succMsg;
        }
        LOG.info("药品经营零售许可证信息查询接口调用成功但返回错误信息");
        throw new GeneralException("药品经营零售许可证信息查询失败");
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public JSONArray getC010(String SHXYDM) throws Exception {
		 String currTime = System.currentTimeMillis() + "";
	        LOG.info("getC010 request params>>>>SHXYDM={},currTime={}", SHXYDM, currTime);
	        String accessToken = ServiceInvocation.getToken(spappId, spappKey, tokenUrl);
	        // 2.在获得服务调用秘钥 access_token 后，根据自己的 appKey 使用 AES 解密算法对返回值进行解密，最终获得真正秘钥的过程。
	        String secretKey = SymmetricEncoder.AESDncode(spappKey, accessToken);
	        // 3.调用服务，获取服务的 json 数据。
	        String sign1 = ServiceInvocation.gatewaySignEncode(spappId, secretKey, currTime);
	        Map queryParams = new HashMap(5);
	        queryParams.put("SHXYDM", SHXYDM);
	        String result = HttpRequestUtil.URLGet(c0010Url, queryParams, "utf-8", ServiceInvocation.buildBaseHeader(spappId, currTime, sign1));

	        LOG.info("getC006 request  result >>>>{}", result);

	        if (result == null) {
	            LOG.info("药品生产许可证信息查询 返回结果为空，调用接口失败错误");
	            throw new GeneralException("药品生产许可证信息查询 getC010 获取错误");
	        }

	        JSONObject serviceJsonData = JSONObject.parseObject(result);

	        if (serviceJsonData.getBoolean("flag")) {
	            JSONObject resultObj = new JSONObject();
	            JSONArray succMsg = JSON.parseArray(serviceJsonData.getString("succMsg"));
	            resultObj.put("succMsg",succMsg);
	            return succMsg;
	        }
	        LOG.info("药品生产许可证信息查询接口调用成功但返回错误信息");
	        throw new GeneralException("药品生产许可证信息查询失败");
	}
}

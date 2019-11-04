package com.neusoft.mid.ec.api.service.housingconstruction;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.exception.GeneralException;
import com.neusoft.mid.ec.api.serviceInterface.housingconstruction.HousingConstructionService;
import com.neusoft.mid.ec.api.shareplatform.ServiceInvocation;
import com.neusoft.mid.ec.api.shareplatform.SymmetricEncoder;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
}

package com.neusoft.mid.ec.api.shareplatform;

import com.alibaba.fastjson.JSONObject;
import com.neusoft.mid.ec.api.exception.GeneralException;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 共享平台接口调用
 */
public class ServiceInvocation {

    private static  Logger LOG = LoggerFactory.getLogger(ServiceInvocation.class);

    public static String gatewaySignEncode(String appId, String appKey, String rtime) throws Exception {
        String inputString = appId + rtime;
        return encode(appKey, inputString);
    }


    private static String encode(String appKey, String inputStr) throws Exception {
        String sign;
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        byte[] keyBytes = appKey.getBytes("UTF-8");
        hmacSha256.init(new SecretKeySpec(keyBytes, 0, keyBytes.length,
                "HmacSHA256"));
        byte[] hmacSha256Bytes =
                hmacSha256.doFinal(inputStr.getBytes("UTF-8"));
        sign = new String(Base64.encodeBase64(hmacSha256Bytes), "UTF-8");
        return sign;
    }

    /**
     * http接口调用
     * @param requestUrl
     * @param requestMethod
     * @param appIdorSecretKey
     * @param currTime
     * @param sign
     * @return
     * @throws Exception
     */
    public static JSONObject httpRequest(String requestUrl,
                                         String requestMethod,
                                         String appIdorSecretKey,
                                         String currTime, String sign) throws Exception {

        LOG.info("requestUrl={},requestMethod={},appIdorSecretKey={},currTime={},sign={}",requestUrl,requestMethod,appIdorSecretKey,currTime,sign);

        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConnection =
                    (HttpURLConnection) url.openConnection();
            httpUrlConnection.setRequestMethod(requestMethod);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setRequestProperty("gateway_appid",
                    appIdorSecretKey);
            httpUrlConnection.setRequestProperty("gateway_rtime",
                    currTime);
            httpUrlConnection.setRequestProperty("gateway_sig", sign);
            httpUrlConnection.connect();
            inputStream = httpUrlConnection.getInputStream();
            InputStreamReader inputStreamReader = new
                    InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferReader = new
                    BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferReader.readLine()) != null) {
                buffer.append(str);
            }
            String buffer2str = buffer.toString();
            LOG.info("响应数据={}",buffer2str);
            jsonObject = JSONObject.parseObject(buffer2str);
            bufferReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            httpUrlConnection.disconnect();
        } catch (ConnectException ce) {
            LOG.info("Server connect time out!");
            throw new ConnectException();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            LOG.info("ioexception request error");
            throw new IOException();
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("http request error!");
            throw new Exception();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ioe) {
                //ignore
            }
        }
        return jsonObject;
    }

    /**
     * 获取token
     * @param appId
     * @param tokenUrl
     */
    public static String  getToken(String appId,String appKey,String  tokenUrl) throws Exception {

        String currTime = System.currentTimeMillis() + "";
        // 1.获取 access_token。
        String sign = gatewaySignEncode(appId, appKey, currTime);
        Map<String,Object> headerParams = buildBaseHeader(appId,currTime,sign);
        String result = HttpRequestUtil.URLPost(tokenUrl, Collections.emptyMap(), headerParams);
        if(result == null){
            throw new GeneralException("获取Token失败");
        }

        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject jsonObjectBody =JSONObject.parseObject(jsonObject.getString("body"));

        if (jsonObjectBody != null) {
            String accessToken = jsonObjectBody.getString("access_token");
            return  accessToken;
        }else{
            throw new GeneralException("获取Token失败");
        }
    }




    /**
     * 获取token社保
     * @param
     * @param tokenUrl
     */
    public static String  getrToken(String  tokenUrl,String name,String account) throws Exception {

        // 1.获取 access_token。
        Map<String,String> bodyParams = buildrsBaseHeader(name,account,"2");
        Map<String,Object> headerParams = new HashMap<>();
        headerParams.put("Authorization","Basic MTAyOTAwMDE6ODJjZDk0YWQ2Njk5NDRlNmE1MzRiY2VhOThlMDFhM2M=");
        String result =   HttpRequestUtil.URLGet(tokenUrl, bodyParams,"utf-8" ,headerParams);
        if(StringUtils.isEmpty(result)){
            throw new GeneralException("获取Token失败");
        }

        JSONObject jsonObject = JSONObject.parseObject(result);

        if (jsonObject != null) {
            String accessToken = jsonObject.getString("access_token");
            return  accessToken;
        }else{
            throw new GeneralException("获取Token失败");
        }
    }

    public  static Map<String,String> buildrsBaseHeader(String name,String account,String type){
        Map<String,String> headerParams = new HashMap<>(5);
        headerParams.put("grant_type",  "open");
        headerParams.put("name", name);
        headerParams.put("account", account);
        headerParams.put("type",type);
        return headerParams;
    }

    public  static Map<String,Object> buildBaseHeader(String appId,String timestamp,String sign){
        Map<String,Object> headerParams = new HashMap<>(5);
        headerParams.put("gateway_appid",  appId);
        headerParams.put("gateway_rtime", timestamp);
        headerParams.put("gateway_sig", sign);
        return headerParams;
    }

  /*  public static void main(String[] args) throws Exception {
        // 参数设定
        String appId = "0c99e056180f4fc6b85e200ed8f2aa44";
        String appKey ="MGM5OWUwNTYxODBmNGZjNmI4NWUyMDBlZDhmMmFhNDQ6MTIzNDU2";
        String currTime = System.currentTimeMillis() + "";
        String generateTokenUrl = "http://59.207.107.18:5000/auth/token";
        // 服务调用的 url
        String invokingServiceUrl ="http://59.207.107.18:5000/api/inservicedemo/microinservice/direct/ejjzszczsxx";
        String accessToken = getToken(appId,appKey,generateTokenUrl);
        // 2.在获得服务调用秘钥 access_token 后，根据自己的 appKey 使用 AES 解密算法对返回值进行解密，最终获得真正秘钥的过程。
        String secretKey = SymmetricEncoder.AESDncode(appKey, accessToken);
        // 3.调用服务，获取服务的 json 数据。
        String sign1 = gatewaySignEncode(appId, secretKey, currTime);
        Map queryParams = new HashMap(5);
        queryParams.put("zjhm","350******057X");
        queryParams.put("zcbh","213234234324");
        queryParams.put("qymc","有限公司");
        String result = HttpRequestUtil.URLGet(invokingServiceUrl, queryParams, "utf-8", buildBaseHeader(appId, currTime, sign1));
        JSONObject serviceJsonData = JSONObject.parseObject(result);
        // 生成 access_token 秘钥的 url
        String codeValue = serviceJsonData.getString("code");
        if("200".equals(codeValue)) {
            System.out.println("服务数据结果:" +
                    serviceJsonData.toString());
        }else {
            LOG.info("获取服务失败，请查看响应数据中的错误码和错误信,result={}",result);
        }
    }*/



}

package com.neusoft.mid.ec.api.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.asiainfo.openplatform.common.util.SecurityUtils;
import com.asiainfo.openplatform.common.util.SignUtil;

public class EncodeUtil {
    private static final Log log = LogFactory.getLog(EncodeUtil.class);
    /**  应用在开放平台注册时分配的应用标识 */
    public static final String app_id = "";
    /**应用注册时分配的应用密钥**/
    public static final String app_key = "";
    /** 响应类型，此处必须为”client_credentials”*/
    public static final String grant_type = "client_credentials";

    @SuppressWarnings("unchecked")
    public static String getSing(String busiParam, String accessToken) {
        String sign = "";
        try {
//            String busiParam = "{\"BILL_ID\":\"15093325804\"}";// ---------------业务参数，其中 \ 为转义符，为了转义中间的双引号
//            String key = "844e37d8e6d904eacc476c3611863445";// ------------------密钥
            @SuppressWarnings("rawtypes")
            Map sysParam = new HashMap();// ------------------------------------系统参数的Map
            sysParam.put("method", "OSP_NURSING_GETDAYSPONUM");
            sysParam.put("format", "json");
            sysParam.put("timestamp", Date2TampsUtil.dateFormat());// 时间戳，年月份时分秒
            sysParam.put("appId", app_id);
//            sysParam.put("version", "1.0");//版本可不传
            sysParam.put("busiSerial", "1");// 固定值 1
            sysParam.put("accessToken", accessToken);// 动态获取

            String busiContent = SecurityUtils.encodeAES256HexUpper(busiParam, SecurityUtils.decodeHexUpper(app_key));// -----加密业务参数
            log.info("加密后的业务参数为**********" + busiContent + "***********");
            sign = SignUtil.sign(sysParam, busiContent, "HmacSHA256", app_key);// ----------生成sign
            log.info("生成的sign为**********" + sign + "**********");
        } catch (Exception e) {
            log.error("生成sign出错：", e);
        }
        return sign;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        String busiParam = "{\"BILL_ID\":\"15093325804\"}";// ---------------业务参数，其中 \ 为转义符，为了转义中间的双引号

        String key = "844e37d8e6d904eacc476c3611863445";// ------------------密钥

        Map sysParam = new HashMap();// ------------------------------------系统参数的Map
        sysParam.put("method", "SO_MEMBER_DEAL_QUERY");
        sysParam.put("format", "json");
        sysParam.put("timestamp", "20160310162624");
        sysParam.put("appId", "503020");
        sysParam.put("version", "1.0");
        sysParam.put("busiSerial", "1");
        sysParam.put("accessToken", "e9044255-2af8-4bc7-9229-d1a4349fde40");

        String busiContent = SecurityUtils.encodeAES256HexUpper(busiParam, SecurityUtils.decodeHexUpper(key));// -----加密业务参数
        log.info("加密后的业务参数为**********" + busiContent + "***********");

        String sign = SignUtil.sign(sysParam, busiContent, "HmacSHA256", app_key);// ----------生成sign
        log.info("生成的sign为**********" + sign + "**********");

    }
}

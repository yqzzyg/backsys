
package com.neusoft.mid.ec.api.util;

import com.alibaba.fastjson.JSON;
import com.neusoft.mid.ec.api.util.http.HttpRequestUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 验证签名工具类
 *
 * @author zhangap
 * @date 2015-7-28
 */
public class RopUtil {
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /**
     * 签名密匙
     */
    public static final String SECRET = "828cep4e9e9c794c0267";
    /**
     * 渠道接入码key
     */
    public static final String APPKEY = "72581cep4dbbe0dfb8ec";
    /**
     * 交易接收方代码
     */
    public static final String TORGCODE = "5510gjj";

    public static final String TXCHANNEL = "1";

    /**
     * 验证签名值
     *
     * @param params 签名参数
     * @param secret 签名密钥
     * @return
     * @throws Exception
     */
    public static boolean checkSign(Map params, String secret) throws Exception {
        String txdate = (String) params.get("txdate");
        String txtime = (String) params.get("txtime");

        if (isEmpty(txdate)
                || isEmpty(txtime)) {
            throw new Exception("请求中缺少交易时间(txdate/txtime)参数");
        }
        DateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        Date time = null;
        try {
            time = format.parse(txdate + txtime);
        } catch (ParseException e1) {
            throw new Exception("交易时间格式不合法");
        }
        // 验证时间戳
        long timestamp = time.getTime();
        long min = Math.abs(new Date().getTime() - timestamp) / 1000 / 60;
        if (min > 10) {
            throw new Exception("请求中时间戳与服务器之间大于10分钟");
        }
        String sign = (String) params.get("sign");
        if (isEmpty(sign)) {
            throw new Exception("签名串不能为空");
        }
        return sign.equals(getSign(params, secret));
    }

    public static boolean isEmpty(String value) {
        if (value == null || "".equals(value.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 签名
     *
     * @param params 签名参数
     * @param secret 签名密钥
     * @return
     * @throws Exception
     */
    public static String getSign(Map params, String secret) throws Exception {
        params.remove("sign");
        List<String> paramKeys = new ArrayList<String>(params.size());
        paramKeys.addAll(params.keySet());
        Collections.sort(paramKeys);
        StringBuilder sb = new StringBuilder();
        sb.append(secret);
        String val = null;
        try {
            for (String key : paramKeys) {
                val = (String) params.get(key);
                if (isChineseStr(val)) {
                    sb.append(key).append(URLEncoder.encode(val, "utf-8"));
                } else if (!isEmpty(val)) {
                    sb.append(key).append(val);
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new Exception("解码异常");
        }
        sb.append(secret);
        return sha1(sb.toString());
    }

    /**
     * sha1加密
     *
     * @param encStr
     * @return
     * @throws Exception
     */
    private static String sha1(String encStr) throws Exception {
        if (encStr == null)
            return "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(encStr.getBytes("utf-8"));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 把密文转换成十六进制的字符串形式
     *
     * @param bytes
     * @return
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * 是否是中文字符
     *
     * @param str
     * @return
     */
    private static boolean isChineseStr(String str) {
        boolean temp = false;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            temp = true;
        }
        return temp;
    }

    public static String mapToXml(Map map) {
        StringBuilder xml = new StringBuilder();
        xml.append("<data>\n");
        if (map != null) {
            Set entrySet = map.entrySet();
            for (Iterator iterator = entrySet.iterator(); iterator.hasNext(); ) {
                Entry entry = (Entry) iterator.next();
                addXmlText(entry.getKey(), entry.getValue(), xml);
            }
        }
        xml.append("</data>");
        return xml.toString();
    }

    private static void addXmlText(Object key, Object value, StringBuilder paramXml) {
        if (value instanceof List) {
            List list = (List) value;
            for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
                Object o = iterator.next();
                if (o instanceof Map)
                    addXmlText(key, o, paramXml);
                else
                    paramXml.append("<").append(key.toString().toLowerCase()).append(">")
                            .append("<![CDATA[").append(o).append("]]>")
                            .append("</").append(key.toString().toLowerCase()).append(">\n");
            }
        } else if (value instanceof Map) {
            Set s = ((Map) value).entrySet();
            paramXml.append("<").append(key.toString().toLowerCase()).append(">");
            for (Iterator irt = s.iterator(); irt.hasNext(); ) {
                Entry e = (Entry) irt.next();
                addXmlText(e.getKey(), e.getValue(), paramXml);
            }
            paramXml.append("</").append(key.toString().toLowerCase()).append(">\n");
        } else {
            if (value != null)
                paramXml.append("<").append(key.toString().toLowerCase()).append(">")
                        .append("<![CDATA[").append(value).append("]]>")
                        .append("</").append(key.toString().toLowerCase()).append(">\n");
        }
    }

    /**
     * xml转换为HashMap
     *
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static Map xmlTOMap(String xml) throws DocumentException {
        HashMap map = null;
        if (xml != null && !"".equals(xml)) {
            map = new HashMap();
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            addElements(root, map);
        }
        return map;
    }

    /**
     * 添加Element判断是否已存在元素，存在添加到list中
     *
     * @param e
     * @param map
     * @param value
     */
    private static void addElementsInMap(Element e, Map map, Object value) {
        String name = e.getName().toLowerCase();
        if (map.get(name) != null) {
            Object obj = map.get(name);
            List list = null;
            if (obj instanceof List) {
                list = (List) obj;
            } else {
                list = new ArrayList();
                list.add(obj);
            }
            list.add(value);
            map.put(name, list);
        } else {
            if ("null".equals(value))
                map.put(name, "");
            else
                map.put(name, value);
        }
    }

    /**
     * 添加Element元素到Map中
     *
     * @param e
     * @param map
     */
    private static void addElements(Element e, Map map) {
        if (e != null) {
            List elements = e.elements();
            if (elements != null && elements.size() > 0) {
                Map m = null;
                for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
                    Element element = (Element) iterator.next();
                    if (element.elements() != null && element.elements().size() > 0) {
                        m = new HashMap();
                        addElements(element, m);
                        addElementsInMap(element, map, m);
                    } else {
                        addElementsInMap(element, map, element.getText());
                    }
                }
            } else {
                String v = e.getText();
                if ("null".equals(v))
                    map.put(e.getName().toLowerCase(), "");
                else
                    map.put(e.getName().toLowerCase(), v);
            }
        }
    }

    public static String getHHMMSS() {
        String format = "HHmmss";
        java.util.Calendar c = java.util.Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(c.getTime());
    }

    public static String getCurrDate() {
        String format = "yyyyMMdd";
        java.util.Calendar c = java.util.Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(c.getTime());
    }

    public static String getRequestData(Map<String, String> bussData) throws Exception {
        bussData.put("txtime", RopUtil.getHHMMSS());
        bussData.put("txdate", RopUtil.getCurrDate());
        bussData.put("reqident", System.currentTimeMillis() + "");
        String sign = RopUtil.getSign(bussData, bussData.get("certcode"));
        bussData.put("sign", sign);
        return RopUtil.mapToXml(bussData);
    }





    public static void main(String[] args) throws Exception {
        Map params = new HashMap();
        params.put("certcode", "828cep4e9e9c794c0267");
        params.put("txcode", "1PBL004");
        params.put("txtime", RopUtil.getHHMMSS());
        params.put("forgcode", "72581cep4dbbe0dfb8ec");
        params.put("txdate", "20170915");
        params.put("torgcode", "5510gjj");
        params.put("percode", "220130065612");
        params.put("idcard", "51112719550309001X");
        params.put("txchannel", "1");
        params.put("reqident", "1111");
        params.put("sign", "13dc477c7174ec887698adffb802ac1b506cad3c");
        System.out.println(RopUtil.getSign(params, "828cep4e9e9c794c0261"));
        params.put("sign", "13dc477c7174ec887698adffb802ac1b506cad3c");
        System.out.println(RopUtil.getCurrDate());

        Map paramsJson = new HashMap();
        paramsJson.put("appId","100260");
        paramsJson.put("secrectKey","C6djamlf1uHMm2xQ");
        String resp = HttpRequestUtil.URLPostJSONParams("http://127.0.0.1/mc/api/console/token", JSON.toJSONString(paramsJson) , new HashMap<>());
        System.out.println(resp);
    }
}

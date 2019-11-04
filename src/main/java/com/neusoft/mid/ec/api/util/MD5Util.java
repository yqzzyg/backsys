package com.neusoft.mid.ec.api.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

/**
 * 采用MD5加密解密
 * @author tfq
 * @datetime 2011-10-13
 */
public class MD5Util {

    /**
     * 教育接口请求加密方式
     * @param s
     * @return
     */
    public static String MD5(String s) {
        try {
            String sign = DigestUtils.md5Hex(s);
            return sign;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /***
     * MD5加密 utf8编码格式加密
     */
    public static String string2Md5(String string) {

        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();

    }


    /**
     * 加密解密算法 执行一次加密，两次解密
     */
    public static String convertMD5(String inStr) {

        char[] a = inStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;

    }

    // 测试主函数
    public static void main(String args[]) throws Exception {
    	
    	/*//String str = "appKey=30000001&phone_no=15982319430&qry_end_date=201406&qry_start_date=201406&qry_type=1&serv_type=4&timeStamp=20140711112354&userName=15982319430";
    	String str = "appKey=30000001&phone_no=15982319430&qry_end_date=201406&qry_start_date=201406&qry_type=1&serv_type=4&timeStamp=20140711112354&userName=15982319430";
    	
    	String s = "";
    	try {
			s = URLEncoder.encode(str,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	System.out.println("原始： "+str);
        System.out.println("encode UTF-8后：" + s);
        System.out.println("MD5后：" + string2MD5(s));
//        System.out.println("urlencode: " + URLEncoder.encode(test(string2MD5(s)), "utf-8"));
*/    


    }
    
    public static String makeSign(String urlEncode,String privKey)throws Exception{
    	
    	//请求参数做urlencode操作，字符集采用utf-8
    	urlEncode=URLEncoder.encode(urlEncode,"UTF-8");
    	
    	//md5运算
    	urlEncode=string2MD5(urlEncode);
    	
    	//md5运算后的值进行私钥加密
    	urlEncode=test(urlEncode,privKey);
    	
    	
    	//加密后的值 做urlencode操作，字符集采用utf-8
    	urlEncode=URLEncoder.encode(urlEncode,"UTF-8");
    	
    	return urlEncode;
    	
    }
    
    public static String test(String input,String privKey) throws Exception{
         Cipher cipher = Cipher.getInstance("RSA");          
         RSAPrivateKey privKeys = (RSAPrivateKey) getPrivateKey(privKey);  
         cipher.init(Cipher.ENCRYPT_MODE, privKeys);  
         byte[] cipherText = cipher.doFinal(input.getBytes());  
         //加密后的东西  
         String cipherStr = Base64.getEncoder().encodeToString(cipherText);
         System.out.println("cipher: " + cipherStr);
         return cipherStr;

    }
    
    public static PrivateKey getPrivateKey(String privKey)throws Exception {  
//    	String keyStr = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBANM3hhN3HvGo/IC9x4N3uFP2GphDIn3EZe4VVssPOVWW/ctvyBsnvsZklb6tpvbKFSQu2bNQDhTPNPhKxzRqAUxNGc2/i3Lv405SyN/ASrGrVE02Vx3Rcj4cSnQ4O2aL2HxibTLhBsycoxJhqrFy0O4QIJ3dqgHLcolYlIVjKCW/AgMBAAECgYA4GA3ei4tHSMbOdhhPfPMSMVD80Q+O8SLU6Qvk38UtSu1aIvS06YhL7hiqzDmEX0TgGCUu7vreYe1CZ7Gh5Ok029pKLCkYufXzfilFjTZDoxXxs2JO6eQIsxRld1Y2KkuveGD8WQTj84qlhOl77Ay/UdSzTjJNiu+TaorxstLm0QJBAOwoqs/fHjJk+j75INbed9XtuLxbINOeLQlfcVOcDmB08Y1ac6eTf2upjFpK/JOf3sf4yJ4B+hHu85u2gIN3TRcCQQDk9mbWnuspj5qpU3a+xWP7lwSYDFbx/SdApSNnD7la8X8nI7W1P2Vdl1vSd7aOMn7lQYMLegatRl0BcoL8t2WZAkAb1FMEugtJ7wJaZ2tKRt8iU6hAchC1P1+ZSikFrE85aK6KdM1KQyRx4IIMpeeL0fwj3pptnFgGwvzsLZ6JX4azAkAjVMZ/vdwp+Kf0ExYS0CDilOY3lEfiZZ8mAZWZ9Lo0h2mIn6ENi+/XZmDb9G8uOCqOs/JF1hmrqVz2uTXvS1mZAkBTD22C2rBdC3pfkVhJmVzr4YFkRcQMxAh0H0fHtbTuVpvWe/iyFRmdUMrexVQVoaGW89J23YYs4COd3qwF2dr+";
    	byte[] keyBytes = Base64.getDecoder().decode(privKey);
        PKCS8EncodedKeySpec spec =new PKCS8EncodedKeySpec(keyBytes);  
        KeyFactory kf = KeyFactory.getInstance("RSA");  
        return kf.generatePrivate(spec);  
      }
    
    /**
     * 将byte数组转换成16进制数据
     * @param b byte[] byte数组
     * @return String 16进制字符串
     */
    public static String bytes2HexString(byte[] b) {
        String ret = "";
        if (null != b) {
            for (int i = 0; i < b.length; i++) {
                String hex = Integer.toHexString(b[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                ret += hex.toUpperCase() + " ";
            } 
        }        
        return ret;
    }

    /**
     * 将成16进制数据转换byte数组
     * @param sString sString 16进制字符串
     * @return byte[] byte数组
     */
    public static byte[] hexString2Bytes(String hexString) {  
        if (hexString == null || hexString.equals("")) {  
            return null;  
        }  
        hexString = hexString.toUpperCase().replaceAll(" ", "");  
        int length = hexString.length() / 2;  
        char[] hexChars = hexString.toCharArray();  
        byte[] d = new byte[length];  
        for (int i = 0; i < length; i++) {  
            int pos = i * 2;  
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
        }  
        return d;  
    }  
    /** 
     * Convert char to byte 
     * @param c char 
     * @return byte 
     */  
     private static byte charToByte(char c) {  
        return (byte) "0123456789ABCDEF".indexOf(c);  
    }
     
     
}

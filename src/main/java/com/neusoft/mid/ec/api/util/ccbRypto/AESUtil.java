package com.neusoft.mid.ec.api.util.ccbRypto;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESUtil {
    private static final String ENCODING_CODE = "UTF-8";
    private static final String KEY_ALGORITHM = "AES";
    /**
     * 密钥长度
     */
    private static final int KEY_ALGORITHM_SIZE = 128;
    /**
     * 安全序列模式
     */
    private static final String SECURE_RANDOM = "SHA1PRNG";
    /**
     * 算法/工作模式/填充方式
     */
    private static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";


    /**
     * 根据用户密钥进行加密
     * @param count 需要加密的字符串
     * @param password 加密的密钥
     * @return
     */
    public static String encrypt(String count, String password) {
        try {
            //加密
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(Cipher.ENCRYPT_MODE, getKey(password));
            byte[] result = cipher.doFinal(count.getBytes(ENCODING_CODE));
            return Base64.encodeBase64String(result);//通过Base64转码返回
        } catch (Exception e) {
            System.out.println("AES加密失败！");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 解密AES加密过的字符串
     * @param count 加密过的字符串
     * @param password 加密时的密钥
     * @return
     */
    public static String decrypt(String count, String password) {
        try {
            //解密 创建密码器
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(Cipher.DECRYPT_MODE, getKey(password));
            byte[] result = cipher.doFinal(Base64.decodeBase64(count));
            return new String(result);
        } catch (Exception e) {
            System.out.println("AES解密失败！");
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 用于生成AES密钥的工具
     */
    public static String getAESSecureKey(){
        String aesKey = "";
        try {//生成KEY, AES要求密钥长度为 256
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            keyGenerator.init(KEY_ALGORITHM_SIZE);
            //生成密钥
            SecretKey secretKey = keyGenerator.generateKey();
            aesKey = Hex.encodeHexString(secretKey.getEncoded());
            System.out.println("生成AES密钥：" + Hex.encodeHexString(secretKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return aesKey;
    }


    /**
     * 创建AES KEY生产者
     * @param password
     * @return
     */
    public static SecretKey getKey(String password) {
        try {
            //生成KEY,AES 要求密钥长度为 256
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom secureRandom = SecureRandom.getInstance(SECURE_RANDOM);
            secureRandom.setSeed(password.getBytes(ENCODING_CODE));
            keyGenerator.init(KEY_ALGORITHM_SIZE, secureRandom);
            //根据用户密码生成密钥
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] byteKey = secretKey.getEncoded();
            //转换KEY AES专属密钥
            return new SecretKeySpec(byteKey, KEY_ALGORITHM);
        } catch (Exception e) {
            System.out.println("创建AES KEY失败！");
            e.printStackTrace();
            return null;
        }
    }
    
    public static String getSecStr(String password) {
        String secStr = "";
        try{
            //生成KEY,AES 要求密钥长度为 128
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM);
            SecureRandom secureRandom = SecureRandom.getInstance(SECURE_RANDOM);
            secureRandom.setSeed(password.getBytes(ENCODING_CODE));
            keyGenerator.init(KEY_ALGORITHM_SIZE, secureRandom);
            //根据用户密码生成密钥
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] byteKey = secretKey.getEncoded();
            secStr = Base64.encodeBase64String(byteKey);
            System.out.println("secretKey = "+secStr);
        } catch (Exception e) {
            System.out.println("创建AES KEY失败！");
            e.printStackTrace();
            return null;
        }
        return secStr;
    }

    public static SecretKey getSecKey(String secStr) {
        return new SecretKeySpec(Base64.decodeBase64(secStr), KEY_ALGORITHM);
    }

    public static String encrypt2(String count, String secStr) {
        try {
            //加密
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(Cipher.ENCRYPT_MODE, getSecKey(secStr));
            byte[] result = cipher.doFinal(count.getBytes(ENCODING_CODE));
            return Base64.encodeBase64String(result);//通过Base64转码返回
        } catch (Exception e) {
            System.out.println("AES加密失败！");
            e.printStackTrace();
        }
        return "";
    }

    public static String decrypt2(String count, String secStr) {
        try {
            //解密 创建密码器
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            cipher.init(Cipher.DECRYPT_MODE, getSecKey(secStr));
            byte[] result = cipher.doFinal(Base64.decodeBase64(count));
            return new String(result);
        } catch (Exception e) {
            System.out.println("AES解密失败！");
            e.printStackTrace();
        }
        return "";
    }
}

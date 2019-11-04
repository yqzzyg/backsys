package com.neusoft.mid.ec.api.shareplatform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * AES 对称加密和解密
 */
public class SymmetricEncoder {

    private static Logger LOG = LoggerFactory.getLogger(SymmetricEncoder.class);

    public static String AESEncode(String encodeRules, String content) {

        // 初始化向量,必须 16 位
        String ivStr = "AESCBCPKCS5Paddi";
        try {
            // 1.构造密钥生成器，指定为 AES 算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            //新增下面两行，处理 Linux 操作系统下随机数生成不一致的问题
            SecureRandom secureRandom =
                    SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encodeRules.getBytes());
            keygen.init(128, secureRandom);
            // 3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            // 5.根据字节数组生成 AES 密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法 AES 自成密码器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的 KEY
            ////指定一个初始化向量 (Initialization vector，IV)， IV 必须是 16 位
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(ivStr.getBytes("UTF-8")));
            // 8.获取加密内容的字节数组(这里要设置为 utf-8)不然内容中如果有中文 和英文混合中文就会解密为乱码
            byte[] byte_encode = content.getBytes("utf-8");
            // 9.根据密码器的初始化方式--加密：将数据加密
            byte[] byte_AES = cipher.doFinal(byte_encode);
            // 10.将加密后的数据转换为字符串
            // 这里用 Base64Encoder 中会找不到包
            // 解决办法：
            // 在项目的 Build path 中先移除 JRE System Library，再添加库 JRE System
            // Library，重新编译后就一切正常了。
            String AES_encode = new String(new
                    BASE64Encoder().encode(byte_AES));
            // 11.将字符串返回
            return AES_encode;
        } catch (Exception e) {
            LOG.error("AESEncode error:", e);
        }
        // 如果有错就返加 nulll
        return null;
    }


    public static String AESDncode(String encodeRules, String content) {
         // 初始化向量,必须 16 位
        String ivStr = "AESCBCPKCS5Paddi";
        try {
             // 1.构造密钥生成器，指定为 AES 算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
             //新增下面两行，处理 Linux 操作系统下随机数生成不一致的问题
            SecureRandom secureRandom =
                    SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(encodeRules.getBytes());
            keygen.init(128, secureRandom);
            // 3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] raw = original_key.getEncoded();
            // 5.根据字节数组生成 AES 密钥
            SecretKey key = new SecretKeySpec(raw, "AES");
            // 6.根据指定算法 AES 自成密码器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密 (Decrypt_mode)操作，第二个参数为使用的 KEY
            ////指定一个初始化向量 (Initialization vector，IV)， IV 必须是 16 位
            cipher.init(Cipher.DECRYPT_MODE, key, new
                    IvParameterSpec(ivStr.getBytes("UTF-8")));
            // 8.将加密并编码后的内容解码成字节数组
            byte[] byte_content = new BASE64Decoder().decodeBuffer(content);
            /*
             * 解密
             */
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        } catch (Exception e) {
            LOG.error("AESEncode error:", e);
        }
        return null;
    }


    public static void main(String[] args) {
        String encodeStr = AESEncode("!11", "111");
        System.out.println(AESDncode("!11", encodeStr));
    }
}


package com.neusoft.mid.ec.api.util;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @Title: ASEPlus.java
 * @Package szht.tjgs.util
 * @author hanx
 * @date 2010-9-19  02:11:38
 * @version V1.0
 *
 * Modification History:
 * Date                 Author        Version     Description
 * --------------------------------------------------------
 * 2010-9-19         hanx          1.0         ����
 */
public class ASEPlus {
    private static PropertyUtilsBean propertyUtilsBean;
    private static ConvertUtilsBean convertUtilsBean;

    private static final Log log = LogFactory.getLog(ASEPlus.class);
    /**加密key */
    public static final String sKey = "zwfw83d125fe46b7";

    // 加密
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        if (sKey == null) {
            log.error("Key is null");
            return null;
        }
        if (sKey.length() != 16) {
            log.error("Key不等于16位!");
            return null;
        }
        byte[] raw = sKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("GBK"));
        return new BASE64Encoder().encode(encrypted);
    }

    // 解密
    public static String Decrypt(String sSrc, String sKey) throws Exception {
        try {
            if (sKey == null) {
                log.error("Key is null");
                return null;
            }
            if (sKey.length() != 16) {
                log.error("Key不等于16位!");
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, "GBK");
                return originalString;
            } catch (Exception e) {
                log.error(e.toString());
                return null;
            }
        } catch (Exception ex) {
            log.error(ex.toString());
            return null;
        }
    }

    public static void EncryptFile(File fileIn, File fileOut, String pwd) throws Exception {
        FileOutputStream fos = null;
        try {
            FileInputStream fis = new FileInputStream(fileIn);
            byte[] bytIn = readbyte(fis);
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(pwd.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();

            byte[] raw = secretKey.getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] bytOut = cipher.doFinal(bytIn);
            fos = new FileOutputStream(fileOut);
            InputStream sbs = new ByteArrayInputStream(bytOut);
            fos.write(readbyte(sbs));
            fos.close();
            fis.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (null != fos) {
                fos.close();
            }
        }
    }

    public static void DecryptFile(File fileIn, File fileOut, String pwd) throws Exception {
        FileOutputStream fos = null;
        try {
            FileInputStream fis = new FileInputStream(fileIn);
            byte[] bytIn = readbyte(fis);

            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(pwd.getBytes());
            kgen.init(128, secureRandom);
            SecretKey secretKey = kgen.generateKey();
            byte[] raw = secretKey.getEncoded();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] bytOut = cipher.doFinal(bytIn);
            fos = new FileOutputStream(fileOut);
            InputStream sbs = new ByteArrayInputStream(bytOut);
            fos.write(readbyte(sbs));
            fos.close();
            fis.close();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            if (null != fos) {
                fos.close();
            }
        }
    }

    protected static byte[] readbyte(InputStream stream) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(8196);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = stream.read(buffer)) > 0) {
                baos.write(buffer, 0, length);
            }
            return baos.toByteArray();

        } catch (Exception exception) {
            return exception.getMessage().getBytes();
        }
    }

    private static void copyProperty(Object bean, String name, Object value)
        throws IllegalAccessException, InvocationTargetException {
        // Resolve any nested expression to get the actual target bean
        Object target = bean;
        Class type = null;
        // Calculate the target property type
        if (target instanceof DynaBean) {
            DynaClass dynaClass = ((DynaBean)target).getDynaClass();
            DynaProperty dynaProperty = dynaClass.getDynaProperty(name);
            if (dynaProperty == null) {
                return; // Skip this property setter
            }
            type = dynaProperty.getType();
        } else {
            PropertyDescriptor descriptor = null;
            try {
                descriptor = getPropertyUtils().getPropertyDescriptor(target, name);
                if (descriptor == null) {
                    return; // Skip this property setter
                }
            } catch (NoSuchMethodException e) {
                return; // Skip this property setter
            }
            type = descriptor.getPropertyType();
        }
        value = convert(value, type);
        try {
            getPropertyUtils().setSimpleProperty(target, name, value);
        } catch (NoSuchMethodException e) {
            throw new InvocationTargetException(e, "Cannot set " + name);
        }

    }

    private static Object convert(Object value, Class type) {
        Converter converter = getConvertUtils().lookup(type);
        if (converter != null) {
            return converter.convert(type, value);
        } else {
            return value;
        }
    }

    private static PropertyUtilsBean getPropertyUtils() {
        if (propertyUtilsBean == null) {
            propertyUtilsBean = new PropertyUtilsBean();
        }
        return propertyUtilsBean;
    }

    private static ConvertUtilsBean getConvertUtils() {
        if (convertUtilsBean == null) {
            convertUtilsBean = new ConvertUtilsBean();
        }
        return convertUtilsBean;
    }

    /** 合计金额-验证发票代码后五位  */
    private static final String HJJE_FPDM_FIVE = "20013、20023、20033、20043、20054、20065、30011、30022、30031、30042、90012、90022、90093、90301、90333、90421、90431、90441、90531、90541、93811";
    /** 合计金额-验证发票代码  */
    private static final String HJJE_FPDM = "141000421863、141000521863、141000621863、141000721863、141000821863、141000921863、141001021863";

    public static void main(String[] args) {

        String fpdm = "141000421863";

        System.out.println(fpdm.substring(fpdm.length() - 5));

        System.out.println("是否需要输入金额：" + HJJE_FPDM_FIVE.contains(fpdm));
        System.out.println("是否需要输入金额：" + HJJE_FPDM.contains(fpdm));

        String date = "20190906";// Date2TampsUtil.timeStamp2Date(Date2TampsUtil.timeStamp(), "yyyyMMdd");
        System.out.println("日期：" + date);

        try {
            System.out.println("加密 " + Encrypt(date, sKey));
            System.out.println("解密" + Decrypt(Encrypt(date, sKey), sKey));
//            System.out.println(Decrypt("n/5XMo/MLYyWjMepavATrQ==", sKey));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

package com.neusoft.mid.ec.api.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.SM4Engine;
import org.bouncycastle.crypto.macs.CBCBlockCipherMac;
import org.bouncycastle.crypto.macs.GMac;
import org.bouncycastle.crypto.modes.GCMBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class SM4Utils {
    private static final String ALGORITHM_NAME = "SM4"; private static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding"; private static final String ALGORITHM_NAME_ECB_NOPADDING = "SM4/ECB/NoPadding"; private static final String ALGORITHM_NAME_CBC_PADDING = "SM4/CBC/PKCS5Padding"; private static final String ALGORITHM_NAME_CBC_NOPADDING = "SM4/CBC/NoPadding"; private static final int DEFAULT_KEY_SIZE = 128; private static byte[] key = parseHexStr2Byte("38F754C08A383E92171D391C7E06585C"); private static byte[] iv = parseHexStr2Byte("5EB04B813C71E6C3C4C2A985C710BF73");

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static byte[] generateKey() throws NoSuchAlgorithmException, NoSuchProviderException { return generateKey(DEFAULT_KEY_SIZE);
    }

    public static byte[] generateKey(int keySize) throws NoSuchAlgorithmException,
            NoSuchProviderException {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME); kg.init(keySize, new SecureRandom()); return kg.generateKey().getEncoded();
    }

    public static byte[] encrypt_Ecb_Padding(byte[] key, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt_Ecb_Padding(byte[] key, byte[] cipherText) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException,
            NoSuchProviderException, NoSuchPaddingException {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    public static byte[] encrypt_Ecb_NoPadding(byte[] key, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_NOPADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt_Ecb_NoPadding(byte[] key, byte[] cipherText) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException,
            NoSuchProviderException, NoSuchPaddingException {
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_NOPADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    public static byte[] encrypt_Cbc_Padding(byte[] key, byte[] iv, byte[] data)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = generateCbcCipher(ALGORITHM_NAME_CBC_PADDING, Cipher.ENCRYPT_MODE, key, iv); return cipher.doFinal(data);
    }

    public static byte[] decrypt_Cbc_Padding(byte[] key, byte[] iv, byte[] cipherText) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException,
            NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = generateCbcCipher(ALGORITHM_NAME_CBC_PADDING, Cipher.DECRYPT_MODE, key, iv); return cipher.doFinal(cipherText);
    }

    public static byte[] encrypt_Cbc_NoPadding(byte[] key, byte[] iv, byte[] data) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException,
            NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        Cipher cipher = generateCbcCipher(ALGORITHM_NAME_CBC_NOPADDING, Cipher.ENCRYPT_MODE, key, iv); return cipher.doFinal(data);
    }

    public static byte[] decrypt_Cbc_NoPadding(byte[] key, byte[] iv, byte[] cipherText) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException,
            NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException { Cipher cipher = generateCbcCipher(ALGORITHM_NAME_CBC_NOPADDING, Cipher.DECRYPT_MODE, key, iv); return cipher.doFinal(cipherText);
    }

    public static byte[] doCMac(byte[] key, byte[] data)
            throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException { Key keyObj = new SecretKeySpec(key, ALGORITHM_NAME); return doMac("SM4-CMAC", keyObj, data);
    }

    public static byte[] doGMac(byte[] key, byte[] iv, int tagLength, byte[] data) { org.bouncycastle.crypto.Mac mac = new GMac(new GCMBlockCipher(new SM4Engine()), tagLength * 8); return doMac(mac, key, iv, data);
    }

    /**
     *	默认使用PKCS7Padding/PKCS5Padding填充的CBCMAC
     *
     *	@param key
     *	@param iv
     *	@param data
     *	@return
     */
    public static byte[] doCBCMac(byte[] key, byte[] iv, byte[] data) { SM4Engine engine = new SM4Engine(); org.bouncycastle.crypto.Mac mac = new CBCBlockCipherMac(engine, engine.getBlockSize() * 8, new PKCS7Padding());
        return doMac(mac, key, iv, data);
    }

    /**
     *	@param key
     *	@param iv
     *	@param padding
     *	可以传null，传null表示NoPadding，由调用方保证数据必须是BlockSize的整数倍
     *	@param data
     *	@return
     *	@throws Exception
     */
    public static byte[] doCBCMac(byte[] key, byte[] iv, BlockCipherPadding padding, byte[] data) throws Exception {
        SM4Engine engine = new SM4Engine();
        if (padding == null) { if (data.length % engine.getBlockSize() != 0) { throw new Exception("if no padding, data length must be multiple of SM4 BlockSize");
        }
        }
        org.bouncycastle.crypto.Mac mac = new CBCBlockCipherMac(engine, engine.getBlockSize() * 8, padding); return doMac(mac, key, iv, data);
    }

    private static byte[] doMac(org.bouncycastle.crypto.Mac mac, byte[] key, byte[] iv, byte[] data) { CipherParameters cipherParameters = new KeyParameter(key); mac.init(new ParametersWithIV(cipherParameters, iv)); mac.update(data, 0, data.length); byte[] result = new byte[mac.getMacSize()]; mac.doFinal(result, 0); return result;
    }

    private static byte[] doMac(String algorithmName, Key key, byte[] data)
            throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeyException { Mac mac = Mac.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME); mac.init(key); mac.update(data); return mac.doFinal();
    }

    private static Cipher generateEcbCipher(String algorithmName, int mode, byte[] key) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME); Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME); cipher.init(mode, sm4Key); return cipher;
    }

    private static Cipher generateCbcCipher(String algorithmName, int mode, byte[] key, byte[] iv) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            NoSuchProviderException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME); IvParameterSpec ivParameterSpec = new IvParameterSpec(iv); cipher.init(mode, sm4Key, ivParameterSpec); return cipher;
    }

    /**
     *	转化为String
     *
     *	@param buf
     *	@return
     */
    private static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF); if (hex.length() == 1) { hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        } return sb.toString();
    }

    /**
     *	将16进制转换为二进制
     *
     *	@param hexStr
     *	@return
     */
    private static byte[] parseHexStr2Byte(String hexStr) { if (hexStr.length() < 1) { return null;
    } byte[] result = new byte[hexStr.length() / 2]; for (int i = 0; i < hexStr.length() / 2; i++) { int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16); int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16); result[i] = (byte) (high * 16 + low);
    } return result;
    }

    public static String encryptSm4Cbc(String content)
            throws NoSuchPaddingException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException { byte[] contentBytes = content.getBytes(); return parseByte2HexStr(encrypt_Cbc_Padding(key, iv, contentBytes));
    }

    public static String decryptSm4Cbc(String cipher)
            throws NoSuchPaddingException, InvalidAlgorithmParameterException,
            NoSuchAlgorithmException,
            IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException { byte[] cipherBytes = parseHexStr2Byte(cipher);

        return new String(decrypt_Cbc_Padding(key, iv, cipherBytes)); }

    /**
     * 测试
     */
    /*public static void main(String[] args) throws Exception {

// 身份证本例进度查询
        System.out.println("身份证本例进度查询=====");
        String content = "500237199510165669";
        System.out.println("加密前：" + content);
        String encrypt = encryptSm4Cbc(content);
        System.out.println("加密后：" + encrypt);
        String decrypt = decryptSm4Cbc(encrypt);
        System.out.println("解密后：" + decrypt);

        // 保安员成绩查询
        System.out.println("保安员成绩查询=====");
        String content1 = "410725199308029866";
        System.out.println("加密前：" + content1);
        String encrypt1 = encryptSm4Cbc(content1);
        System.out.println("加密后：" + encrypt1);
        String decrypt1 = decryptSm4Cbc(encrypt1);
        System.out.println("解密后：" + decrypt1);

       // 保安员进度查询
        System.out.println("保安员进度查询=====");
        String content2 = "411403198806295456";
        System.out.println("加密前：" + content2);
        String encrypt2 = encryptSm4Cbc(content2);
        System.out.println("加密后：" + encrypt2);
        String decrypt2 = decryptSm4Cbc(encrypt2);
        System.out.println("解密后：" + decrypt2);
    }
    */
}

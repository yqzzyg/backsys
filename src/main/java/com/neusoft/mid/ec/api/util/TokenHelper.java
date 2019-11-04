package com.neusoft.mid.ec.api.util;

import me.puras.common.util.MD5;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Created by puras on 10/10/16.
 */
public abstract class TokenHelper {
    private static final String SEP = ":";

    protected abstract String getDigestKey(Long userId);

    public String generateToken(Long userId) {
        return generateToken(userId, System.currentTimeMillis());
    }

    public String generateUUIDToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public String generateToken(Long userId, long timestamp) {
        return userId + SEP + timestamp + SEP + getDigest(userId, timestamp);
    }

    public String getDigest(Long userId, long timestamp) {
        String info = userId + SEP + timestamp;
        return MD5.getMD5(getUTF8(info + getDigestKey(userId)));
    }

    public static String[] parseToken(String token) {
        return token.split(SEP);
    }

    public boolean isValid(Long userId, Long timestamp, String digest) {
        return digest.equalsIgnoreCase(getDigest(userId, timestamp));
    }

    private static byte[] getUTF8(String data) {
        try {
            return data.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return null;
        }
    }
}

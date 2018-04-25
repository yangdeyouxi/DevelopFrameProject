package com.develop.frame.util;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by sam on 2017/12/26.
 * <p>
 * <p>
 * 加密，解密相关的工具类
 */

public final class EncryptUtils {

    private EncryptUtils() {
        throw new UnsupportedOperationException("U can't instantiate me...");
    }


    /**
     * MD5 加密
     *
     * @param data 明文字符串
     * @return 16进制密文
     */


    public static String encryptMD5ToString(final String data) {
        return encryptMD5ToString(data.getBytes());
    }

    /**
     * MD5 加密
     *
     * @param data 明文字符串
     * @param salt 盐
     * @return 16进制加盐密文
     */
    public static String encryptMD5ToString(final String data, final String salt) {
        return byte2HexString(encryptMD5((data + salt).getBytes()));
    }

    /**
     * 加密 明文字节组
     *
     * @param data
     * @return 16进制密文
     */

    public static String encryptMD5ToString(final byte[] data) {
        return byte2HexString(encryptMD5(data));
    }

    /**
     * MD5 加密
     *
     * @param data 明文字节数组
     * @param salt 盐字节数组
     * @return 16进制加盐密文
     */

    public static String encryptMD5ToString(final byte[] data, final byte[] salt) {
        if (data == null || salt == null) return null;
        byte[] dataSalt = new byte[data.length + salt.length];
        System.arraycopy(data, 0, dataSalt, 0, data.length);
        System.arraycopy(salt, 0, dataSalt, data.length, salt.length);
        return byte2HexString(encryptMD5(dataSalt));
    }

    /**
     * @param data 明文字节数组
     * @return 密文字节数组
     */

    public static byte[] encryptMD5(final byte[] data) {
        return hashTemplate(data, "MD5");
    }


    /**
     * hash 加密模板
     *
     * @param data      数据
     * @param algorithm 加密算法
     * @return 密文字节数组
     */
    private static byte[] hashTemplate(final byte[] data, final String algorithm) {
        if (data == null || data.length <= 0) return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encryptHmacMD5ToString(final String data, final String key) {
        return encryptHmacMD5ToString(data.getBytes(), key.getBytes());
    }


    public static String encryptHmacMD5ToString(final byte[] data, final byte[] key) {
        return byte2HexString(encryptHmacMD5(data, key));
    }


    public static byte[] encryptHmacMD5(final byte[] data, final byte[] key) {
        return hmacTemplate(data, key, "HmacMD5");
    }


    /**
     * HmacSHA256 加密
     * @param data  明文字符串
     * @param key   密钥
     * @return    16进制密文
     */
    public static String encryptHmacSHA256ToString(final String data, final String key) {
        return encryptHmacSHA256toString(data.getBytes(), key.getBytes());
    }

    /**
     *  HmacSHA256 加密
     * @param data  明文字节数组
     * @param key   密钥
     * @return   16进制密文
     */

    public static String encryptHmacSHA256toString(final byte[] data, final byte[] key) {
        return byte2HexString(encryptHmacSHA256(data, key));
    }

    /**
     * HmacSHA256 加密
     * @param data  明文字节数组
     * @param key  密钥
     * @return   16进制密文
     */
    public static byte[] encryptHmacSHA256(final byte[] data,final byte[] key){
        return hmacTemplate(data,key,"HmacSHA256");
    }


    /**
     *  Hmac 加密模板
     * @param data   数据
     * @param key    密钥
     * @param algorithm  加密算法
     * @return   密文字节数组
     */
    private static byte[] hmacTemplate(final byte[] data, final byte[] key, final String algorithm) {
        if (data == null || data.length == 0 || key == null || key.length == 0) return null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
            'D', 'E', 'F'};

    public  static String byte2HexString(final byte[] bytes) {
        if (bytes == null) return null;

        int len = bytes.length;
        if (len <= 0) return null;

        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }
}

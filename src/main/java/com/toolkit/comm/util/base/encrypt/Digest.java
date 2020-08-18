package com.toolkit.comm.util.base.encrypt;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:计算摘要的工具类
 */
public class Digest {

    /**
     * 使用MD5算法计算摘要，并对结果进行hex转换
     *
     * @param str 源数据
     * @return 摘要信息
     */
    public static String md5Digest(String str) {
        try {
            byte[] data = str.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            MessageDigest md = MessageDigest.getInstance(ConfigureEncryptAndDecrypt.MD5_ALGORITHM);
            return Hex.toHex(md.digest(data));
        } catch (Exception e) {
            throw new RuntimeException("digest fail!", e);
        }
    }
    /**
     * 使用MD5算法计算摘要，并对结果进行hex转换 16位加密
     * @param digest
     * @return
     */
    public static String md5Digest16(String digest) {
        try {
            byte[] data = digest.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            MessageDigest md = MessageDigest.getInstance(ConfigureEncryptAndDecrypt.MD5_ALGORITHM);
            return Hex.toHex(md.digest(data)).substring(8,24);
        } catch (Exception e) {
            throw new RuntimeException("digest fail!", e);
        }
    }
    /**
     * 使用MD5算法计算摘要，并对结果进行hex转换 16位加密
     * @param digest
     * @return
     */
    public static String md5Digest8(String digest) {
        try {
            byte[] data = digest.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            MessageDigest md = MessageDigest.getInstance(ConfigureEncryptAndDecrypt.MD5_ALGORITHM);
            return Hex.toHex(md.digest(data)).substring(8,16);
        } catch (Exception e) {
            throw new RuntimeException("digest fail!", e);
        }
    }
    /**
     * 使用SHA-0算法计算摘要，并对结果进行hex转换
     *
     * @param str 源数据
     * @return 摘要信息
     */
    public static String shaDigest(String str) {
        try {
            byte[] data = str.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            MessageDigest md = MessageDigest.getInstance(ConfigureEncryptAndDecrypt.SHA_ALGORITHM);
            return Hex.toHex(md.digest(data));
        } catch (Exception e) {
            throw new RuntimeException("digest fail!", e);
        }
    }

    /**
     * 根据指定算法计算摘要
     *
     * @param str          源数据
     * @param alg          摘要算法
     * @param charencoding 源数据获取字节的编码方式
     * @return 摘要信息
     */
    public static String digest(String str, String alg, String charencoding) {
        try {
            byte[] data = str.getBytes(charencoding);
            MessageDigest md = MessageDigest.getInstance(alg);
            return Hex.toHex(md.digest(data));
        } catch (Exception e) {
            throw new RuntimeException("digest fail!", e);
        }
    }

    private static final String HMAC_SHA1 = "HmacSHA1";

    /**
     * 生成签名数据
     *
     * @param data 待加密的数据
     * @param key  加密使用的key
     * @return 生成MD5编码的字符串
     * @throws java.security.InvalidKeyException
     * @throws java.security.NoSuchAlgorithmException
     */
    public static String getSignature(byte[] data, byte[] key) throws InvalidKeyException, NoSuchAlgorithmException {
        SecretKeySpec signingKey = new SecretKeySpec(key, HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data);
        return org.apache.commons.codec.binary.Base64.encodeBase64String(rawHmac);

    }
}

package com.toolkit.comm.util.base.encrypt;


import com.toolkit.comm.util.base.CheckUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:3des加解密算法工具类
 */
public class DESede {
    /**
     * 字符串 DESede(3DES) 加密
     *
     * @param key  - 为24字节的密钥（3组x8字节）
     * @param data - 需要进行加密的数据（8字节）
     * @return
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        CheckUtils.notEmpty(data, "data");
        CheckUtils.notEmpty(key, "key");
        if (key.length != 24) {
            throw new RuntimeException("Invalid DESede key length (must be 24 bytes)");
        }
        try {
            SecretKey deskey = new SecretKeySpec(key, ConfigureEncryptAndDecrypt.DES_ALGORITHM);
            Cipher c1 = Cipher.getInstance(ConfigureEncryptAndDecrypt.DES_ALGORITHM);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 字符串 DESede(3DES) 解密
     *
     * @param key  - 为24字节的密钥（3组x8字节）
     * @param data - 需要进行解密的数据（8字节）
     * @return
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        CheckUtils.notEmpty(data, "data");
        CheckUtils.notEmpty(key, "key");
        if (key.length != 24) {
            throw new RuntimeException("Invalid DESede key length (must be 24 bytes)");
        }
        try {
            SecretKey deskey = new SecretKeySpec(key, ConfigureEncryptAndDecrypt.DES_ALGORITHM);
            Cipher c1 = Cipher.getInstance(ConfigureEncryptAndDecrypt.DES_ALGORITHM);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }
    /**
     * 字符串 DESede(3DES) 解密
     *
     * @param key  - 为24字节的密钥（3组x8字节）
     * @param data - 需要进行解密的数据（8字节）
     * @return
     */
    public static String decrypt2String(byte[] data, byte[] key) {
        CheckUtils.notEmpty(data, "data");
        CheckUtils.notEmpty(key, "ke");
        if (key.length != 24) {
            throw new RuntimeException("Invalid DESede key length (must be 24 bytes)");
        }
        try {
            SecretKey deskey = new SecretKeySpec(key, ConfigureEncryptAndDecrypt.DES_ALGORITHM);
            Cipher c1 = Cipher.getInstance(ConfigureEncryptAndDecrypt.DES_ALGORITHM);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return new String(c1.doFinal(data));
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }
    /**
     * 加密并对加密结果进行base64编码
     *
     * @param key
     * @param data
     * @return
     */
    public static String encryptToBase64(String data, String key) {
        try {
            byte[] keyByte = key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            byte[] dataByte = data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            byte[] valueByte = encrypt(dataByte, keyByte);
            return new String(Base64.encode(valueByte));
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 先进行base64解码，再进行3des解密
     *
     * @param data
     * @param key
     * @return
     */
    public static String decryptFromBase64(String data, String key) {
        try {
            byte[] keyByte = key.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            byte[] valueByte = Base64.decode(data.getBytes());
            byte[] dataByte = decrypt(valueByte, keyByte);
            String str = new String(dataByte, ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            return str;
        } catch (Exception e) {
            throw new RuntimeException("decrypt fail!", e);
        }
    }
}
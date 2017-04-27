package com.eeduspace.uuims.comm.util.base.encrypt.pki;


import com.eeduspace.uuims.comm.util.base.encrypt.Base64;
import com.eeduspace.uuims.comm.util.base.encrypt.ConfigureEncryptAndDecrypt;

import javax.crypto.Cipher;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:
 */
public class RSA {
    /**
     * 验证签名
     *
     * @param data      数据
     * @param sign      签名
     * @param publicKey 公钥
     * @return
     */
    public static boolean verifySign(byte[] data, byte[] sign,
                                     PublicKey publicKey) {
        try {
            Signature signature = Signature
                    .getInstance(ConfigureEncryptAndDecrypt.SIGNATURE_ALGORITHM);
            signature.initVerify(publicKey);
            signature.update(data);
            boolean result = signature.verify(sign);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("verifySign fail!", e);
        }
    }

    /**
     * 验证签名
     *
     * @param data     数据
     * @param sign     签名
     * @param pubicKey 公钥
     * @return
     */
    public static boolean verifySign(String data, String sign,
                                     PublicKey pubicKey) {
        try {
            byte[] dataByte = data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            byte[] signByte = Base64.decode(sign.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING));
            return verifySign(dataByte, signByte, pubicKey);
        } catch (UnsupportedEncodingException e) {

            throw new RuntimeException("verifySign fail!", e);
        }
    }

    /**
     * 签名
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] sign(byte[] data, PrivateKey key) {
        try {
            Signature signature = Signature
                    .getInstance(ConfigureEncryptAndDecrypt.SIGNATURE_ALGORITHM);
            signature.initSign(key);
            signature.update(data);
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException("sign fail!", e);
        }
    }

    /**
     * 签名
     *
     * @param data
     * @param key
     * @return
     */
    public static String sign(String data, PrivateKey key) {
        try {
            byte[] dataByte = data
                    .getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
            return new String(Base64.encode(sign(dataByte, key)));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("sign fail!", e);
        }
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(byte[] data, Key key) {
        Cipher cipher;
        try {
            cipher = Cipher
                    .getInstance(ConfigureEncryptAndDecrypt.KEY_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {

            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptToBase64(String data, Key key) {
        try {
            return new String(Base64.encode(encrypt(
                    data.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING),
                    key)));
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decrypt(byte[] data, Key key) {
        try {
            Cipher cipher = Cipher
                    .getInstance(ConfigureEncryptAndDecrypt.KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static String decryptFromBase64(String data, Key key) {
        try {
            return new String(decrypt(Base64.decode(data.getBytes()), key),
                    ConfigureEncryptAndDecrypt.CHAR_ENCODING);
        } catch (Exception e) {
            throw new RuntimeException("encrypt fail!", e);
        }
    }
}

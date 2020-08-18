package com.toolkit.comm.util.base.encrypt.pki;



import com.toolkit.comm.util.base.CheckUtils;
import com.toolkit.comm.util.base.encrypt.AES;
import com.toolkit.comm.util.base.encrypt.Base64;
import com.toolkit.comm.util.base.encrypt.ConfigureEncryptAndDecrypt;
import com.toolkit.comm.util.base.encrypt.HmacSign;

import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:
 */
public class DigitalEnvelopeUtils {

    public static String encrypt(String source, PrivateKey privateKey, PublicKey publicKey) {
        CheckUtils.notEmpty(source, "source data");
        CheckUtils.notEmpty(privateKey, "privateKey");
        CheckUtils.notEmpty(publicKey, "publicKey");

        byte[] data = null;
        try {
            data = source.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encrypt fail!", e);
        }
        //生成随机密钥
        byte[] randomKey = AES.genarateRandomKey();

        //对密钥加密
        byte[] encryptedRandomKey = RSA.encrypt(randomKey, publicKey);
        String encryptedRandomKeyToBase64 = new String(Base64.encode(encryptedRandomKey));

        //使用随机密钥对数据进行
        byte[] encryptedData = AES.encrypt(data, randomKey);
        String encryptedDataToBase64 = new String(Base64.encode(encryptedData));

        //对数据进行签名
        byte[] sign = RSA.sign(data, privateKey);
        String signToBase64 = new String(Base64.encode(sign));
        //把密文和签名进行打包
        String envelopeData = encryptedRandomKeyToBase64 + ConfigureEncryptAndDecrypt.SEPERATOR + encryptedDataToBase64 + ConfigureEncryptAndDecrypt.SEPERATOR + signToBase64;

        //用随机密钥对以上打包结果进行hmacSign
        String hmacSignToBase64 = null;
        try {
            byte[] hmacSign = HmacSign.sign(envelopeData.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING), randomKey);
            hmacSignToBase64 = new String(Base64.encode(hmacSign));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("encrypt fail!", e);
        }

        //返回数据
        return envelopeData + ConfigureEncryptAndDecrypt.SEPERATOR + hmacSignToBase64;
    }

    public static String decrypt(String source, PrivateKey privateKey, PublicKey publicKey) {
        CheckUtils.notEmpty(source, "source data");
        CheckUtils.notEmpty(privateKey, "privateKey");
        CheckUtils.notEmpty(publicKey, "publicKey");

        //分解参数
        String[] args = source.split("\\" + ConfigureEncryptAndDecrypt.SEPERATOR);
        if (args.length != 4) {
            throw new RuntimeException("source invalid : " + source);
        }
        String encryptedRandomKeyToBase64 = args[0];
        String encryptedDataToBase64 = args[1];
        String signToBase64 = args[2];
        String hmacSignToBase64 = args[3];
        if (CheckUtils.isEmpty(encryptedRandomKeyToBase64) || CheckUtils.isEmpty(encryptedDataToBase64)
                || CheckUtils.isEmpty(signToBase64) || CheckUtils.isEmpty(hmacSignToBase64)) {
            throw new RuntimeException("source invalid : " + source);
        }

        //用私钥对随机密钥进行解密
        byte[] randomKey = RSA.decrypt(Base64.decode(encryptedRandomKeyToBase64.getBytes()), privateKey);

        //验证hmacSign
        try {
            String envelopeData = encryptedRandomKeyToBase64 + ConfigureEncryptAndDecrypt.SEPERATOR + encryptedDataToBase64 + ConfigureEncryptAndDecrypt.SEPERATOR + signToBase64;
            byte[] hmacSign = HmacSign.sign(envelopeData.getBytes(ConfigureEncryptAndDecrypt.CHAR_ENCODING), randomKey);
            if (!new String(Base64.encode(hmacSign)).equals(hmacSignToBase64)) {
                throw new RuntimeException("verify hmacsign fail!");
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decrypt fail!", e);
        }

        //解密得到源数据
        byte[] sourceData = AES.decrypt(Base64.decode(encryptedDataToBase64.getBytes()), randomKey);
        //验证签名
        boolean verifySign = RSA.verifySign(sourceData, Base64.decode(signToBase64.getBytes()), publicKey);
        if (!verifySign) {
            throw new RuntimeException("verifySign fail!");
        }

        //返回源数据
        try {
            return new String(sourceData, ConfigureEncryptAndDecrypt.CHAR_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("decrypt fail!");
        }
    }
}

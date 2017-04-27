package com.eeduspace.uuims.comm.util.base.encrypt.pki;

import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertPath;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:
 */
public class KeyUtils {

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    public static CertPath getCertificateChain(InputStream is) {
        if (is == null) {
            throw new IllegalArgumentException(
                    "no certificate inputstream specified !!");
        }
        CertPath certpath = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(is);
            List caCertsList = new ArrayList();
            caCertsList.add(cert);
            CertPath path = cf.generateCertPath(caCertsList);
            certpath = path;
            return certpath;
        } catch (Exception e) {
            throw new RuntimeException("get certificateChain fail!", e);
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                }
        }
    }

    /**
     * 从文件流中获得证书对象
     *
     * @param is
     * @return
     * @throws java.security.cert.CertificateException
     */
    public static X509Certificate getCertificate(InputStream is)
            throws CertificateException {
        if (is == null) {
            throw new IllegalArgumentException(
                    "no certificate inputstream specified !!");
        }
        X509Certificate cert = null;
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X509");
            cert = (X509Certificate) cf.generateCertificate(is);
            return cert;
        } catch (Exception e) {
            throw new RuntimeException("get certificate fail!", e);
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                }
        }
    }

    /**
     * 从keyStore中获取私钥
     *
     * @param keyStore
     * @param alias
     * @param password
     * @return
     */
    public static PrivateKey getPrivateKey(KeyStore keyStore, String alias,
                                           String password) {
        try {
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias,
                    password.toCharArray());
            return privateKey;
        } catch (Exception e) {
            throw new RuntimeException("get PrivateKey fail!", e);
        }
    }

    /**
     * 从文件流中获取keyStore
     *
     * @param is
     * @param keyStorePass
     * @param keyStoreType
     * @return
     */
    public static KeyStore getKeyStore(InputStream is, String keyStorePass,
                                       String keyStoreType) {
        if (is == null) {
            throw new IllegalArgumentException(
                    "no keystore inputstream specified !!");
        }
        if (keyStorePass == null) {
            throw new IllegalArgumentException("no keyStorePass specified !!");
        }
        if (keyStoreType == null) {
            throw new IllegalArgumentException("no keyStoreType specified !!");
        }
        try {
            KeyStore keystore = KeyStore.getInstance(keyStoreType);
            keystore.load(is, keyStorePass.toCharArray());
            return keystore;
        } catch (Exception e) {
            throw new RuntimeException("get KeyStore fail!", e);
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                }
        }
    }

    /**
     * 初始化密钥
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        // 私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 取得私钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static Key getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        // return new String(Base64.encode(key.getEncoded()));
        return key;
    }

    /**
     * 取得公钥
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static Key getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        // return new String(Base64.encode(key.getEncoded()));
        return key;
    }

}

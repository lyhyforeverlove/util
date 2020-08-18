package com.toolkit.comm.util.base.http;

import javax.net.ssl.KeyManagerFactory;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:hmac签名算法工具类
 */
public class ClientKeyStore {
    private KeyManagerFactory keyManagerFactory;

    ClientKeyStore(KeyManagerFactory keyManagerFactory) {
        this.keyManagerFactory = keyManagerFactory;
    }

    KeyManagerFactory getKeyManagerFactory() {
        return keyManagerFactory;
    }
}

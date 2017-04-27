package com.eeduspace.uuims.comm.util.base.http;

import javax.net.ssl.TrustManagerFactory;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:HTTP通讯辅助工具类
 */
public class TrustKeyStore {
    private TrustManagerFactory trustManagerFactory;

    TrustKeyStore(TrustManagerFactory trustManagerFactory) {
        this.trustManagerFactory = trustManagerFactory;
    }

    TrustManagerFactory getTrustManagerFactory() {
        return trustManagerFactory;
    }
}

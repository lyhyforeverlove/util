package com.toolkit.comm.util.base.encrypt;

import com.toolkit.comm.util.EncodeUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: dingran
 * Date: 2015/10/26
 * Description:
 */
public class AESTest {

    @Test
    public void testGenarateRandomKeyWithBase64() {
        String key = AES.genarateRandomKeyWithBase64();
        logger.info("key:{}", key);
    }

    @Test
    public void testGenarateRandomKey() {
        String key = EncodeUtil.bytesToHexStr(AES.genarateRandomKey());
        logger.info("key:{}", key);
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());
}

package com.eeduspace.uuims.comm.util.base.encrypt;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: dingran
 * Date: 2016/4/10
 * Description:
 */
public class Base64Test {


    @Test
    public  void testBase64() {

        String encodeKey =Base64.encode("test");
        logger.info("encodeKey:{}", encodeKey);
        String key =Base64.decode(encodeKey);
        logger.info("key:{}", key);

//        byte[] bytes="test".getBytes();
//        byte[] encodeKey1 =Base64.encodeBase64(bytes);
//        logger.info("encodeKey:{}", new String(encodeKey1));
//        byte[] key1 =Base64.decodeBase64(encodeKey1);
//        logger.info("key:{}", new String(key1));


        String md58= Digest.md5Digest8("test");
        logger.info("md58:{}",md58);
        String md516= Digest.md5Digest16("test");
        logger.info("md516:{}",md516);
        String md532= Digest.md5Digest("test");
        logger.info("md532:{}",md532);
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());
}

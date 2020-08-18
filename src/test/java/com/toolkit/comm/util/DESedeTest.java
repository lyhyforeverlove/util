package com.toolkit.comm.util;


import com.toolkit.comm.util.base.encrypt.DESede;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:
 */
public class DESedeTest {

    private final static Logger logger = LoggerFactory.getLogger(DESedeTest.class);

    //加密
    @Test
    public void encryptTest(){


        String data = "192.168.100.221";


        String key = "1af4f815e95d23e6463fadc8";
        logger.info("data.getBytes():{}",data.getBytes());
        byte[] secretArr  = DESede.encrypt(data.getBytes(), key.getBytes());
        logger.info("secretArr:{}",secretArr);

        byte[] myMsgArr = DESede.decrypt(secretArr, key.getBytes());
        logger.info("myMsgARR:{}",new String(myMsgArr));


    }


}

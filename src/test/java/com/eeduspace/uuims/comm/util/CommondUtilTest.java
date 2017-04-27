package com.eeduspace.uuims.comm.util;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:
 */
public class CommondUtilTest {

    @Test
    public void testExec(){
        String result = CommondUtil.exec("dir");
        logger.info("result:{}",result);
    }

    private final static Logger logger = LoggerFactory.getLogger(CommondUtil.class);
}

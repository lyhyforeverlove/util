package com.toolkit.comm.util.base;

import com.toolkit.comm.util.base.http.MethodType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: dingran
 * Date: 2015/10/26
 * Description:
 */
public class HttpClientUtilTest {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test() {
        String r = null;
        try {
            r = new HttpClientUtil().doRequest(MethodType.GET, "http://www.163.com", "gb2312");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        logger.info(r);
    }
}

package com.toolkit.comm.util.base;

import com.toolkit.comm.util.HTTPClientUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Author: dingran
 * Date: 2015/10/26
 * Description:
 */
public class HttpClientUtilsTest {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtilsTest.class);

    @Test
    public void testHttpClientUtils(){
        try {
            //String response = HTTPClientUtils.httpGetRequest("http://192.168.100.213/api/cloud/Vm/aaa","","");
            String response = HTTPClientUtils.httpPostRequestJson("http://192.168.100.213:8080/v1/node?user=tianli" +
                            "&body_md5hex=eb1757426516e8bbbb2d9600fadf1a60&timestamp=1397034352804&token=5b1ed23bd70acbcb3eec6c8635607a3c",
                    "{\"action\":\"node_list\",\"region\":\"127.0.0.1\"}"
            );
            logger.info("response:{}", response);
        } catch (IOException e) {
            logger.error("io e:{}",e.getMessage());
        } catch (Exception e){
            logger.error("e:{}",e.getMessage());
        }
    }

    @Test
    public void testHttpClientUtil(){
//        try {
///*            HttpResponse response = HttpClientUtil.httpPostRequestJson("http://192.168.100.213:8080/v1/node?user=tianli" +
//                    "&body_md5hex=eb1757426516e8bbbb2d9600fadf1a60&timestamp=1397034352804&token=5b1ed23bd70acbcb3eec6c8635607a3c",
//                    "{\"action\":\"node_list\",\"region\":\"127.0.0.1\"}");*/
//            HttpResponse response = HttpClientUtil.httpPostRequestJson("http://www.baidu.com","");
//            HttpEntity entity = response.getEntity();
//            StatusLine statusLine = response.getStatusLine();
//
//            logger.info("entity:{}", ToStringBuilder.reflectionToString(entity));
//            logger.info("statusLine:{}", ToStringBuilder.reflectionToString(statusLine));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}

package com.eeduspace.uuims.comm.util.base.http;

import java.io.IOException;
import java.io.InputStream;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:回调接口
 */
public interface HttpResponseCallBack {

    public void processResponse(InputStream responseBody) throws IOException;
}

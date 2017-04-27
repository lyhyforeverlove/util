package com.eeduspace.uuims.comm.util.base.http;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:把httpclient 发送请求的 方法封装成枚举类型
 * 这样可以避免字符串出错的情况
 * GET 代表法get 请求
 * POST代表发post 请求
 */
public enum MethodType {

    GET, POST, DELETE, PUT, TRACE, OPTION

}

package com.toolkit.comm.util.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.Map.Entry;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:通用工具类(提供一些常用而不好归类的方法)
 */
public class CommonUtils {
    public static final String SYSPROP_RUNMODE = "runMode";
    public static final String SYSPROP_RUNMODE_TEST = "test";
    public static final String SYSPROP_TESTMODE = "testMode";
    public static final String SYSPROP_TESTMODE_ON = "on";

    /**
     * 获取16个字符的唯一号
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 把properties文件加载到map里面
     *
     * @param uri 文件URI(暂时只支持classpath路径)
     * @return 加载后用来存放参数的map
     */
    public static Map<String, String> loadProps(String uri) {
        OrderedProperties props = new OrderedProperties();
        Map result = new LinkedHashMap();
        InputStream is = null;
        try {
            is = CommonUtils.class.getClassLoader().getResourceAsStream(uri);
        } catch (Exception e) {
            ;
        }
        if (is == null) {
            try {
                is = CommonUtils.class.getResourceAsStream(uri);
            } catch (Exception e) {
                ;
            }
        }

        if (is == null) {
            try {
                is = CommonUtils.class.getResource("/" + uri).openStream();
            } catch (Exception e) {
                ;
            }
        }

        if (is == null) {
            try {
                is = Thread.currentThread().getClass().getResourceAsStream(uri);
            } catch (Exception e) {
                ;
            }
        }

        String basepath = "";
        if (is == null) {
            try {
                basepath = System.getProperty("user.dir");

                is = new FileInputStream(new File(basepath + "/" + uri));
            } catch (Exception e) {
                ;
            }
        }

        try {
            props.loadMap(is, result);
        } catch (Exception e) {
            throw new RuntimeException("load resource fail, uri:" + uri + " errorMsg:" + e.getMessage() + basepath, e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return result;
    }

    /**
     * 把properties文件加载到map里面
     *
     * @param uri 文件URI(暂时只支持classpath路径)
     * @param map 用来存放参数的map
     */
    @SuppressWarnings("unchecked")
    public static void loadProps(String uri, Map map) {
        CheckUtils.notNull(map, "map");
        map.putAll(loadProps(uri));
    }

    public static List<Entry<String, String>> loadList(String uri) {
        OrderedProperties props = new OrderedProperties();
        List<Entry<String, String>> list = new ArrayList<Entry<String, String>>();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(uri);
        try {
            props.loadList(is, list);
        } catch (Exception e) {
            throw new RuntimeException("load resource fail, uri:" + uri + " errorMsg:" + e.getMessage(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        return list;
    }

    public static Object newInstance(String className) {
        try {
            return Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("new instance fail : " + e.getMessage(), e);
        }
    }


    public boolean isTestMode() {
        return SYSPROP_RUNMODE_TEST.equals(System.getProperty(SYSPROP_RUNMODE));
    }

    public boolean isTestMode(String funcName) {
        if (isTestMode()) {
            return true;
        }
        return SYSPROP_TESTMODE_ON.equals(System.getProperty(SYSPROP_TESTMODE + "." + funcName));
    }
}

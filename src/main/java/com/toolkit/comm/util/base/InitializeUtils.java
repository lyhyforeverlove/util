package com.toolkit.comm.util.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:身份证工具类
 */
public class InitializeUtils {
    private static final Log logger = LogFactory.getLog(InitializeUtils.class);
    private static List<String> destoryList = new ArrayList<String>();

    public static void initComponents() {
        Map<String, String> props = null;
        try {
            props = CommonUtils.loadProps("runtimecfg/initcomponents.properties");
        } catch (Exception e) {
        }
        if (props == null) {
            props = CommonUtils.loadProps("initcomponents.properties");
        }
        String[] initargs = new String[props.keySet().size()];
        props.keySet().toArray(initargs);
        initComponents(initargs);
    }

    public static void registDestoryMethod(String method) {
        destoryList.add(method);
    }

    public static void destoryComponents() {
        if (destoryList.size() > 0) {
            String[] destoryargs = new String[destoryList.size()];
            destoryList.toArray(destoryargs);
            initComponents(destoryargs);
        }
    }

    public static void initComponents(String[] args) {
        for (String arg : args) {
            try {
                executeInitMethod(arg);
                logger.info("init component [" + arg + "] success");
            } catch (Throwable e) {
                logger.error("init component [" + arg + "] error : " + e.getMessage(), e);
                throw new RuntimeException("init component error : " + arg + e.getMessage() + e.toString(), e);
            }
        }
    }

    private static void executeInitMethod(String arg) throws Throwable {
        CheckUtils.notNull(arg, "");
        int index = arg.lastIndexOf(".");
        if (index <= 0) {
            throw new IllegalArgumentException("invalid init arguments[" + arg + "]");
        }
        String className = arg.substring(0, index);
        String methodName = arg.substring(index + 1, arg.length()).replaceAll("\\(", "").replaceAll("\\)", "");
        Class clz = Class.forName(className);
        Method method = clz.getMethod(methodName);
        method.invoke(null);
    }
}

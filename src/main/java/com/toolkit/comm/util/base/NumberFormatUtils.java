package com.toolkit.comm.util.base;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:数字格式化工具类
 */
public class NumberFormatUtils {

    /**
     * 格式化工具集合
     */
    private static final Map<String, NumberFormat> fmtMap = new HashMap<String, NumberFormat>();

    /**
     * @param ex 格式表达式
     * @return 数字格式化工具
     */
    private static NumberFormat getFormat(String ex) {
        NumberFormat fmt = fmtMap.get(ex);
        if (fmt == null) {
            fmt = new DecimalFormat(ex);
            fmtMap.put(ex, fmt);
        }
        return fmt;
    }

    /**
     * 格式化
     *
     * @param obj 对象
     * @param ex  格式表达式
     * @return 数字字符串
     */
    public static String format(Object obj, String ex) {
        return getFormat(ex).format(obj);
    }

    /**
     * 格式化
     *
     * @param number 数字
     * @param ex     格式表达式
     * @return 数字字符串
     */
    public static String format(double number, String ex) {
        return getFormat(ex).format(number);
    }

    /**
     * 格式化
     *
     * @param number 数字
     * @param ex     格式表达式
     * @return 数字字符串
     */
    public static String format(long number, String ex) {
        return getFormat(ex).format(number);
    }

    /**
     * 解析
     *
     * @param source 数字字符串
     * @param ex     格式表达式
     * @return 数字
     * @throws java.text.ParseException
     */
    public static Number parse(String source, String ex) throws ParseException {
        return getFormat(ex).parse(source);
    }

    /**
     * 除法操作
     * @param data1 被除数
     * @param data2 除数
     * @param keep 保留小数点的位数
     * @return 值
     */
    public static double toDivision(double data1, double data2, int keep){
        double k = data1/data2;
        java.math.BigDecimal big = new java.math.BigDecimal(k);
        return big.setScale(keep,java.math.BigDecimal.ROUND_HALF_UP).doubleValue();
    }


}

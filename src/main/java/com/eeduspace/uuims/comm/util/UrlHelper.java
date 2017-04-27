package com.eeduspace.uuims.comm.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:数据格式验证
 */
public final class UrlHelper {
    //单例要加私有的构造函数
    private UrlHelper() {
    }
    /**
     * 验证邮箱格式
     */
    public static boolean isboolEmail(String idCard) {
        String str = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(idCard);
        return matcher.matches();
    }
    /**
     * 正则验证IP地址，正确返回true，错误返回false
     */
    public static boolean isboolIP(String ipAddress) {
        String ip = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    /**
     * 正则验证idCard 18位，正确返回true，错误返回false
     */
    public static boolean isboolIdCard(String idCard) {
        String str = "([1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3})";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(idCard);
        return matcher.matches();
    }

    /**
     * 正则验证idCard  15位，正确返回true，错误返回false
     */
    public static boolean isbool15IdCard(String idCard) {
        String str = "([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4})";
        Pattern pattern = Pattern.compile(str);
        Matcher matcher = pattern.matcher(idCard);
        return matcher.matches();
    }

    /**
     * 验证url是否合法(支持IP)
     * 例： http://127.0.0.1:8080/action/t/userCenter/account/rechargeRecord.html
     * @param url
     * @return
     */
    public static boolean isURL(String url){

//        String regEx = "^(http|https|ftp)\\://([a-zA-Z0-9\\.\\-]+(\\:[a-zA-"
//                + "Z0-9\\.&%\\$\\-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
//                + "2}|[1-9]{1}[0-9]{1}|[1-9])\\.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
//                + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|"
//                + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)\\.(25[0-5]|2[0-"
//                + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
//                + "-9\\-]+\\.)*[a-zA-Z0-9\\-]+\\.[a-zA-Z]{2,4})(\\:[0-9]+)?(/"
//                + "[^/][a-zA-Z0-9\\.\\,\\?\\'\\\\/\\+&%\\$\\=~_\\-@]*)*$";

        String reg = "^(http|www|ftp|https)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?" +
                "(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*" +
                "(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";

        return url.matches(reg);
    }



    /**
     * 产生随机字符串
     */
    public static synchronized String randomString(int length) {
        /**
         * DEMO
         */
        Random r = new Random();
        final String chars = "0123456789abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }
        return sb.toString();
    }


}

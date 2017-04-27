package com.eeduspace.uuims.comm.util;


/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:验证工具类
 */
public final class EncodeUtil {

    /**
     * 把16进制字符串转换成字节数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexStrToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /**
     * 把字节数组转换成16进制字符串
     *
     * @param bArray
     * @return
     */
    public static String bytesToHexStr(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 十六进制字节数组转二进制字节
     *
     * @param b
     * @return
     */
    public static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException("长度不是偶数");
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            // 两位一组，表示一个字节，把这样表示的16进制字符串，还原成一个二进制字节
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    /**
     * 十六进制字节数组转字符串
     *
     * @param b
     * @return
     */
    public static String hex2Str(byte[] b) {
        String item = new String(b, 0, b.length);
        // 两位一组，表示一个字节，把这样表示的16进制字符串，还原成一个二进制字节
        return item;
    }

    /**
     * 把字符串转换为16进制ascii码
     *
     * @param str
     * @return
     */
    public static byte[] StrToHexAscii(String str) {
        byte[] b = new byte[str.length()];
        for (int i = 0; i < str.length(); i++) {
            b[i] = (byte) (0xFF & str.charAt(i));
        }
        return b;
    }
}

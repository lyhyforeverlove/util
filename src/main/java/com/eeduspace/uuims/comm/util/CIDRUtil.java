package com.eeduspace.uuims.comm.util;

import org.apache.commons.net.util.SubnetUtils;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:验证工具类
 */
public class CIDRUtil {
    public static void main(String [] args){
//        String[] parts = addr.split("/");
//        String ip = parts[0];
//        int prefix;
//        if (parts.length < 2) {
//            prefix = 0;
//        } else {
//            prefix = Integer.parseInt(parts[1]);
//        }
//        int mask = 0xffffffff << (32 - prefix);
//        System.out.println("Prefix=" + prefix);
//        System.out.println("Address=" + ip);
//
//        int value = mask;
//        byte[] bytes = new byte[]{
//                (byte)(value >>> 24), (byte)(value >> 16 & 0xff), (byte)(value >> 8 & 0xff), (byte)(value & 0xff) };
//
//        InetAddress netAddr = InetAddress.getByAddress(bytes);
//        System.out.println("Mask=" + netAddr.getHostAddress());


        SubnetUtils utils = new SubnetUtils("172.17.109.252/30");
        System.out.println(utils.getInfo().getAddress());
    }
}

package com.eeduspace.uuims.comm.util.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:网络工具类
 */
public class NetUtil {
    static final String VLAN_PREFIX = "vlan://";
    static final int VLAN_PREFIX_LENGTH = VLAN_PREFIX.length();

    /**
     * 获得一个子网内所有ip
     *
     * @param ipStart
     * @param ipEnd
     * @param mask
     * @return
     */
    public static List<String> getSubnetIps(String ipStart, String ipEnd, String mask) {

        //判断是否在同一子网
        isSameSubnet(ipStart, ipEnd, mask);

        List<String> result = new ArrayList<>();
        String[] ipArray = ipStart.split("\\.");
        int part1 = Integer.parseInt(ipArray[0]);
        int part2 = Integer.parseInt(ipArray[1]);
        int part3 = Integer.parseInt(ipArray[2]);
        int part4 = Integer.parseInt(ipArray[3]);

        int i = 0;
        while (i == 0) {
            String item = new String();
            if (part4 != 256) {
                item = part1 + "." + part2 + "." + part3 + "." + part4;
                result.add(item);
            }
            part4++;
            if (part4 == 256) {
                part3++;
                part4 = 0;
            } else if (part3 == 256) {
                part2++;
                part3 = 0;
            } else if (part2 == 256) {
                part1++;
            }

            if (ipEnd.equals(item)) {
                i = 1;
            }
        }
        return result;
    }

    /**
     * 判断是否在同一子网
     * @param ipStart
     * @param ipEnd
     * @param mask
     */
    private static void isSameSubnet(String ipStart, String ipEnd, String mask) {
        if (!isValidIp(ipStart)) {
            throw new IllegalArgumentException("错误: ipStart 格式错误：" + ipStart);
        }
        if (!isValidIp(ipEnd)) {
            throw new IllegalArgumentException("错误: ipEnd 格式错误：" + ipEnd);
        }
        if (!validIpRange(ipStart, ipEnd)) {
            throw new IllegalArgumentException("错误: ipStart " + ipStart + " 小于 ipEnd " + ipEnd);
        }
        if (!isValidNetmask(mask)) {
            throw new IllegalArgumentException("mask 格式错误：" + mask);
        }
        if (!sameSubnet(ipStart, ipEnd, mask)) {
            throw new IllegalArgumentException("起始ip" + ipStart + ",结束ip" + ipEnd + ",mask" + mask + ",不在同一子网.");
        }
    }

    /**
     * 获取子网内最大可用ip数
     *
     * @param ipStart
     * @param ipEnd
     * @param mask
     * @return
     * @throws IllegalArgumentException
     */
    public static int getMaxLease(String ipStart, String ipEnd, String mask) throws IllegalArgumentException {

        int maxLease = getSubnetIps(ipStart, ipEnd, mask).size();

        return maxLease;
    }
    public static boolean ipRangesOverlap(String startIp1, String endIp1, String startIp2, String endIp2) {
        long startIp1Long = ip2Long(startIp1);
        long endIp1Long = startIp1Long;
        if (endIp1 != null) {
            endIp1Long = ip2Long(endIp1);
        }
        long startIp2Long = ip2Long(startIp2);
        long endIp2Long = startIp2Long;
        if (endIp2 != null) {
            endIp2Long = ip2Long(endIp2);
        }

        if (startIp1Long == startIp2Long || startIp1Long == endIp2Long || endIp1Long == startIp2Long || endIp1Long == endIp2Long) {
            return true;
        } else if (startIp1Long > startIp2Long && startIp1Long < endIp2Long) {
            return true;
        } else if (endIp1Long > startIp2Long && endIp1Long < endIp2Long) {
            return true;
        } else if (startIp2Long > startIp1Long && startIp2Long < endIp1Long) {
            return true;
        } else if (endIp2Long > startIp1Long && endIp2Long < endIp1Long) {
            return true;
        } else {
            return false;
        }
    }

    public static long ip2Long(String ip) {
        String[] tokens = ip.split("[.]");
        assert (tokens.length == 4);
        long result = 0;
        for (int i = 0; i < tokens.length; i++) {
            try {
                result = (result << 8) | Integer.parseInt(tokens[i]);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Incorrect number", e);
            }
        }

        return result;
    }

    public static String long2Ip(long ip) {
        StringBuilder result = new StringBuilder(15);
        result.append((ip >> 24 & 0xff)).append(".");
        result.append((ip >> 16 & 0xff)).append(".");
        result.append((ip >> 8 & 0xff)).append(".");
        result.append(ip & 0xff);

        return result.toString();
    }

    public static long mac2Long(String macAddress) {
        String[] tokens = macAddress.split(":");
        assert (tokens.length == 6);
        long result = 0;
        for (int i = 0; i < tokens.length; i++) {
            result = result << 8;
            result |= Integer.parseInt(tokens[i], 16);
        }
        return result;
    }
    //@Deprecated
    public static boolean isValidIp(final String ip) {
        final String[] ipAsList = ip.split("\\.");

        // The IP address must have four octets
        if (Array.getLength(ipAsList) != 4) {
            return false;
        }

        for (int i = 0; i < 4; i++) {
            // Each octet must be an integer
            final String octetString = ipAsList[i];
            int octet;
            try {
                octet = Integer.parseInt(octetString);
            } catch (final Exception e) {
                return false;
            }
            // Each octet must be between 0 and 255, inclusive
            if (octet < 0 || octet > 255) {
                return false;
            }

            // Each octetString must have between 1 and 3 characters
            if (octetString.length() < 1 || octetString.length() > 3) {
                return false;
            }
        }

        // IP is good, return true
        return true;
    }
    public static boolean validIpRange(String startIP, String endIP) {
        if (endIP == null || endIP.isEmpty()) {
            return true;
        }

        long startIPLong = ip2Long(startIP);
        long endIPLong = ip2Long(endIP);
        return (startIPLong <= endIPLong);
    }
    public static boolean isValidNetmask(String netmask) {
        if (!isValidIp(netmask)) {
            return false;
        }

        long ip = ip2Long(netmask);
        int count = 0;
        boolean finished = false;
        for (int i = 31; i >= 0; i--) {
            if (((ip >> i) & 0x1) == 0) {
                finished = true;
            } else {
                if (finished) {
                    return false;
                }
                count += 1;
            }
        }

        if (count == 0) {
            return false;
        }

        return true;
    }
    public static boolean sameSubnet(final String ip1, final String ip2, final String netmask) {
        if (ip1 == null || ip1.isEmpty() || ip2 == null || ip2.isEmpty()) {
            return true;
        }
        String subnet1 = getSubNet(ip1, netmask);
        String subnet2 = getSubNet(ip2, netmask);

        return (subnet1.equals(subnet2));
    }
    public static String getSubNet(String ip, String netmask) {
        long ipAddr = ip2Long(ip);
        long subnet = ip2Long(netmask);
        long result = ipAddr & subnet;
        return long2Ip(result);
    }
    public static boolean isValidVlan(String vlan) {
        if (null == vlan || "".equals(vlan))
            return false;
        if (vlan.startsWith(VLAN_PREFIX))
            vlan = vlan.substring(VLAN_PREFIX_LENGTH);
        try {
            int vnet = Integer.parseInt(vlan);
            if (vnet <= 0 || vnet >= 4095) { // the valid range is 1- 4094
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private static final Logger logger = LoggerFactory.getLogger(NetUtil.class);
}

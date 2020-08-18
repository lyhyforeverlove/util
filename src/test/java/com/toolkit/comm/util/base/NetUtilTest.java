package com.toolkit.comm.util.base;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Author: dingran
 * Date: 2015/10/26
 * Description:
 */
public class NetUtilTest {

    /**
     * 测试数量
     */
    @Test
    public void testGetRangeIps() {
        String startIp = "192.168.100.250";
        String endIp = "192.168.100.254";
        String netmask = "255.255.255.0";

        List<String> ips = NetUtil.getSubnetIps(startIp, endIp, netmask);
        for (String ip : ips) {
            logger.info("ip:{}", ip);
        }
        logger.info("ips.size():{}", ips.size());
        Assert.assertTrue(ips.size() == 5);
    }

    /**
     * 测试数量
     */
    @Test
    public void testGetRangeIps2() {
        String startIp = "128.36.192.1";
        String endIp = "128.36.207.254";
        String netmask = "255.255.240.0";

        List<String> ips = NetUtil.getSubnetIps(startIp, endIp, netmask);
        for (String ip : ips) {
            logger.info("ip:{}", ip);
        }
        logger.info("ips.size():{}", ips.size());
        Assert.assertTrue(ips.size() == 4094);
    }

    /**
     * 测试不在同一子网
     */
    @Test
    public void testGetRangeIps3() {
        String startIp = "128.36.192.1";
        String endIp = "128.36.208.254";
        String netmask = "255.255.240.0";

        try {
            List<String> ips = NetUtil.getSubnetIps(startIp, endIp, netmask);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        Assert.fail();
    }

    @Test
    public void testGetMaxLease() {
        String startIp = "192.168.100.251";
        String endIp = "192.168.100.254";
        String netmask = "255.255.255.0";
        int maxLease = NetUtil.getMaxLease(startIp, endIp, netmask);
        logger.info("maxLease:{}", maxLease);
        Assert.assertTrue(maxLease == 4);
    }

    Logger logger = LoggerFactory.getLogger(this.getClass());
}

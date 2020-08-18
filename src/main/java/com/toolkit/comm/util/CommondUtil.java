package com.toolkit.comm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:验证工具类
 */
public class CommondUtil {

    public static String exec(String command) {
        Runtime runtime = Runtime.getRuntime();
        StringBuilder sb = new StringBuilder();
        try {
            String[] args;
            if ("linux".equalsIgnoreCase(System.getProperty("os.name"))) { // 判断系统
                args = new String[]{"sh", "-?/c", command};
            } else {
                args = new String[]{"cmd", "/c", command};
            }
            Process pro = runtime.exec(args);
//            Process pro = runtime.exec(command);

            //检查命令是否失败
            try {
                InputStream in;
                if (pro.waitFor() != 0) {
                    logger.error("exit value:" + pro.exitValue());
                    in = pro.getErrorStream();
                } else {
                    in = pro.getInputStream();
                }
                InputStreamReader isr = new InputStreamReader(in);

                BufferedReader br = new BufferedReader(isr);

                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                    sb.append("\n");
                }
            } catch (InterruptedException e) {
                logger.error("error Message:" + e.getMessage());
                e.printStackTrace();
            }

        } catch (IOException e) {
            logger.error("error Message:" + e.getMessage());
            e.printStackTrace();
        } finally {
            return sb.toString();
        }
    }

    private final static Logger logger = LoggerFactory.getLogger(CommondUtil.class);
}

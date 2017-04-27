package com.eeduspace.uuims.comm.util.base;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:调试工具
 */
public class DebugUtils {

    /**
     * 打印堆栈信息
     *
     * @param stacks
     * @return
     */
    public static String getStackTrace(StackTraceElement[] stacks) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stacks.length; i++) {
            sb.append("\tat " + stacks[i] + "\n");
        }
        return sb.toString();
    }

    /**
     * 打印简短堆栈信息
     *
     * @param stacks
     * @return
     */
    public static String getShortStackTrace(StackTraceElement[] stacks) {
        return getShortStackTrace(stacks, 10);
    }

    /**
     * 打印简短堆栈信息
     *
     * @param stacks
     * @return
     */
    public static String getShortStackTrace(StackTraceElement[] stacks, int topSize) {
        StringBuilder sb = new StringBuilder();
        boolean flag = false;
        for (int i = 0; i < stacks.length; i++) {
            if (i > topSize) {
                if (stacks[i].getClassName().startsWith("com.eeduspace.uuims")) {
                    sb.append("\tat " + stacks[i] + "\n");
                    flag = false;
                } else {
                    if (!flag) {
                        sb.append("\t...\n");
                    }
                    flag = true;
                }
            } else {
                sb.append("\tat " + stacks[i] + "\n");
                flag = false;
            }
        }
        return sb.toString();
    }
}

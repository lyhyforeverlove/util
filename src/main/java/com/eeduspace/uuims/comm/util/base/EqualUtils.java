package com.eeduspace.uuims.comm.util.base;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:类名称：EqualUtils <br>
 * 类描述：判断两个对象是否相等<br>
 */
public class EqualUtils {

    /**
     * 描述：    比较两个对象是否相等（包含null情况）
     *
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean isEqual(Object obj1, Object obj2) {
        if (obj1 == null) {
            if (obj2 == null) {
                return true;
            } else {
                return false;
            }
        } else if (obj2 == null) {
            return false;
        } else {
            return obj1.equals(obj2);
        }
    }

}

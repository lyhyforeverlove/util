package com.eeduspace.uuims.comm.util;

import com.eeduspace.uuims.comm.util.base.CheckUtils;
import com.eeduspace.uuims.comm.util.base.encrypt.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * Author: dingran
 * Date: 2015/10/23
 * Description:
 */
public class SerializationUtils {

    private static final String ENCODING = "ISO-8859-1";

    public static String serializeBase64(Serializable serializable) {
        CheckUtils.notEmpty(serializable, "序列化对象不能为空！");
        byte[] bytes = org.apache.commons.lang3.SerializationUtils.serialize(serializable);
        String str = null;
        try {
            str = new String(bytes, ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        String base64 = Base64.encode(str);
        return base64;
    }

    public static <T> T deserializeBase64(String base64) {
        CheckUtils.notEmpty(base64, "反序列化字符串不能为空！");
        String str = Base64.decode(base64);
        byte[] bytes = new byte[0];
        try {
            bytes = str.getBytes(ENCODING);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return (T) org.apache.commons.lang3.SerializationUtils.deserialize(bytes);
    }

    private static final Logger logger = LoggerFactory.getLogger(SerializationUtils.class);
}

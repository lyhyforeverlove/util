package com.eeduspace.uuims.comm.util.base.json;


import com.eeduspace.uuims.comm.util.JsonAccessor;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Author: dingran
 * Date: 2015/11/04
 * Description:Gson 转换
 */
public class GsonUtil {

    static Gson gson=new Gson();

    /**
     * json转换list
     * @param json
     * @param responseName
     * @param responseObjName
     * @param type
     * @return
     */
    public static <T> List<T> fromListJson(String json,String responseName,String responseObjName,Type type) throws IOException {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json);

        if(jsonElement == null) {
            throw new IOException("Cloud WS API fromListJson error : invalid JSON response");
        }

        JsonAccessor jsonAccessor = new JsonAccessor(jsonElement);

        if(jsonAccessor==null) {
            return null;
        }

        String response = responseName;
        if(StringUtils.isNotEmpty(responseObjName))
            response = responseName + "." + responseObjName;

        return gson.fromJson(jsonAccessor.eval(response),type);
    }

    /**
     * 转换对象
     * @param json
     * @param responseClz
     * @param <T>
     * @return
     */
    public static <T> T fromObjectJson(String json, String responseName, String responseObjName, Class<T> responseClz) throws IOException {
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json);

        if(jsonElement == null) {
            throw new IOException("Cloud WS API fromObjectJson error : invalid JSON response");
        }
        JsonAccessor jsonAccessor = new JsonAccessor(jsonElement);
        if(jsonAccessor==null) {
            return null;
        }
        String response = responseName;
        if(StringUtils.isNotEmpty(responseObjName))
            response = responseName + "." + responseObjName;

        return (T)gson.fromJson(jsonAccessor.eval(response),responseClz);
    }
}

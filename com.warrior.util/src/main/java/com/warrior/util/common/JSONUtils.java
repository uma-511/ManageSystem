package com.warrior.util.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warrior.util.exception.UtilException;
import org.apache.commons.lang.StringUtils;
import java.io.IOException;

/***
 * JSON转换工具
 */
public class JSONUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj){
        String json;
        try {
            json = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new UtilException("JSON转换错误！",e);
        }
        return json;
    }

    public static <T> T parseJson(String json,Class<T> t){
        if (StringUtils.isBlank(json)){
            throw new UtilException("JSON字符串不能为空！");
        }
        T obj;
        try {
            obj = mapper.readValue(json,t);
        } catch (IOException e) {
            throw new UtilException("JSON转换错误！",e);
        }
        return obj;
    }
}
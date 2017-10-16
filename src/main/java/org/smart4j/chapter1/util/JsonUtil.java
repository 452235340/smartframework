package org.smart4j.chapter1.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * JSON工具类
 * Created by qingbowu on 2017/10/16.
 */
public final class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();


    /**
     * POJO  转为 JSON
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String toJson(T obj){
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error("convert POJO to JSON failure",e);
            throw new RuntimeException(e);
        }
        return json;
    }

    public static <T> T formatJson(String josn,Class<T> cls){
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(josn, cls);
        } catch (IOException e) {
            LOGGER.error("convert JSON to POJO failure",e );
            throw new RuntimeException(e);
        }
        return pojo;
    }
}

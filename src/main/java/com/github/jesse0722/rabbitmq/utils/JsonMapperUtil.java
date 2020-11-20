package com.github.jesse0722.rabbitmq.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Lijiajun
 * @date 2020/11/10 18:00
 */
@Slf4j
public class JsonMapperUtil extends ObjectMapper {

    private static final long serialVersionUID = 1L;

    private static JsonMapperUtil mapper;

    public static JsonMapperUtil getInstance() {
        if (mapper == null) {
            mapper = new JsonMapperUtil().enableSimple();
        }
        return mapper;
    }

    public String toJson(Object object) {
        try {
            return this.writeValueAsString(object);
        } catch (IOException e) {
            log.warn("Object to json failed: " + object, e);
            return null;
        }
    }

    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return this.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.warn("Json to object failed: " + jsonString, e);
            return null;
        }
    }

    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return (T) this.readValue(jsonString, javaType);
        } catch (IOException e) {
            log.warn("Json to object failed: " + jsonString, e);
            return null;
        }
    }

    public static Map<String, Object> jsonToMap(String jsonString) {
        return JsonMapperUtil.fromJsonObject(jsonString,
                TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, Object.class));
    }

    public static <T> T fromJsonObject(String jsonString, JavaType javaType) {
        return JsonMapperUtil.getInstance().fromJson(jsonString, javaType);
    }

    public static Map<String, Object> objectToMap(Object object) {
        return jsonToMap(toJsonString(object));
    }

    public static Object fromJsonString(String jsonString, Class<?> clazz) {
        return JsonMapperUtil.getInstance().fromJson(jsonString, clazz);
    }


    public JsonMapperUtil enableSimple() {
        this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        return this;
    }

    public ObjectMapper getMapper() {
        return this;
    }


    public static String toJsonString(Object object) {
        return JsonMapperUtil.getInstance().toJson(object);
    }

}

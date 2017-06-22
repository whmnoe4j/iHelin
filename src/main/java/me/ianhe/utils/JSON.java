package me.ianhe.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSON {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(JSON.class);

    static {
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    }

    /**
     * 基本对象转JSON String
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        try {
//            OBJECT_MAPPER.writeValue(System.out, object);
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonGenerationException e) {
            LOGGER.error("encode(Object)", e);
            return null;
        } catch (JsonMappingException e) {
            LOGGER.error("encode(Object)", e);
            return null;
        } catch (IOException e) {
            LOGGER.error("encode(Object)", e);
            return null;
        }
    }

    /**
     * json反序列化
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonParseException e) {
            LOGGER.error("decode(String, Class<T>)", e);
            return null;
        } catch (JsonMappingException e) {
            LOGGER.error("decode(String, Class<T>)", e);
            return null;
        } catch (IOException e) {
            LOGGER.error("decode(String, Class<T>)", e);
            return null;
        }
    }

    public static List<HashMap> parseArrayMap(String json) {
        TypeReference<List<HashMap>> jsonTypeReference = new TypeReference<List<HashMap>>() {
        };
        return parseArray(json, jsonTypeReference);
    }

    public static Map<String, Object> parseMap(String json) {
        try {
            return OBJECT_MAPPER.readValue(json, Map.class);
        } catch (IOException e) {
            LOGGER.error("解析map异常：", e);
            return null;
        }
    }

    private static <T> T parseArray(String json, TypeReference<T> jsonTypeReference) {
        try {
            return (T) OBJECT_MAPPER.readValue(json, jsonTypeReference);
        } catch (JsonParseException e) {
            LOGGER.error("decode(String, JsonTypeReference<T>)", e);
        } catch (JsonMappingException e) {
            LOGGER.error("decode(String, JsonTypeReference<T>)", e);
        } catch (IOException e) {
            LOGGER.error("decode(String, JsonTypeReference<T>)", e);
        }
        return null;
    }

    private JSON() {
        //工具类不允许实例化
    }
}

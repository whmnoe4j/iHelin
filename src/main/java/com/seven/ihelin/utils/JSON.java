package com.seven.ihelin.utils;

import com.fasterxml.jackson.databind.JavaType;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSON {

    private static JsonMapper mapper = new JsonMapper();

    /**
     * 基本对象转JSON String
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return mapper.toJson(object);
    }

    /**
     * 还原对象
     *
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String jsonString, Class<T> clazz) {
        return mapper.fromJson(jsonString, clazz);
    }

    public static <T> List<T> parseArray(String jsonString, Class<T> clazz) {
        JavaType jt = mapper.createCollectionType(List.class, clazz);
        return mapper.fromJson(jsonString, jt);
    }

    public static <T> Set<T> parseSet(String jsonString, Class<T> clazz) {
        JavaType jt = mapper.createCollectionType(Set.class, clazz);
        return mapper.fromJson(jsonString, jt);
    }

    public static <K, V> Map<K, V> parseMap(String jsonString, Class<K> keyclz, Class<V> valclz) {
        JavaType jt = mapper.createCollectionType(Map.class, keyclz, valclz);
        return mapper.fromJson(jsonString, jt);
    }
}

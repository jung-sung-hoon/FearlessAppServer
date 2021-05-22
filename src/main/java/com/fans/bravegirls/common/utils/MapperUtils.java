package com.fans.bravegirls.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class MapperUtils {
    private MapperUtils(){
        throw new IllegalStateException("Utils class");
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 모델의 필드를 추출하여 Set<String>으로 리턴한다.
     * @param classType : 필드 추출할 모델클래스
     * @return : 필드셋
     */
    public static Set<String> getModelFieldNames(Class<?> classType){

        Set<String> fields = new HashSet<>();
        Arrays.stream(classType.getDeclaredFields()).forEach(field -> fields.add(field.getName()));

        log.info("### fields = {}", fields);
        return fields;
    }

    /**
     * 오브젝트를 json 문자열로 리턴한다.
     * @param o : 오브젝트
     * @return : json 문자열
     */
    public static String toJson(Object o) {
        String json = null;
        try {
            json = OBJECT_MAPPER.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.warn(e.getMessage());
        }
        return json;
    }

    /**
     * json 문자열을 T 오브젝트로 리턴한다.
     * @param json : json 문자열
     * @param type : T 클래스
     * @return : T 오브젝트
     */

    public static <T> T fromJson(String json, Class<T> type) {
        if(json == null || type == null) {
            return null;
        }
        T result = null;
        try {
            result = OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
        return result;
    }

    /**
     * json 문자열을 T 오브젝트로 리턴한다.
     * @param json : json 문자열
     * @param type : T 타입레퍼런스(Generic class 용도)
     * @return : T 오브젝트
     */
    public static <T> T fromJson(String json, TypeReference<T> type) {
        if(json == null || type == null) {
            return null;
        }
        T result = null;
        try {
            result = OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
        return result;
    }

}

package com.search.twitter.searcher;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSONUtils {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Convert JavaBean to JSON String
     *
     * @param obj to be converted
     * @return JSON String of obj
     */
    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.out.println("Error While Converting JavaBean to JSON: " + e.getMessage());
        }
        return null;
    }


    /**
     * Convert JSON String to JavaBean
     *
     * @param jsonStr JSON to convert
     * @param cls     Java class for target object
     * @param <T>     Java type to convert to
     * @return Java object
     */
    public static <T> T readValue(String jsonStr, Class<T> cls) {
        try {
            return objectMapper.readValue(jsonStr, cls);
        } catch (IOException e) {
            System.out.println("Error While Converting JSONString to JavaBean: " + e.getMessage() + "\n" +
                    e.getStackTrace());
        }
        return null;
    }
}


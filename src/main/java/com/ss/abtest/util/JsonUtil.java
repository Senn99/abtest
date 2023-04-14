package com.ss.abtest.util;

import com.google.gson.Gson;
import org.springframework.boot.json.GsonJsonParser;

import java.lang.reflect.Type;

/**
 * @author senn
 * @since 2023/4/4 15:56
 **/
public class JsonUtil {
    private static final Gson GSON = new Gson();

    public static String parse(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String content, Class<T> t) {
        return (T) GSON.fromJson(content, t);
    }
}

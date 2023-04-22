package com.ss.abtest.util;

import com.google.gson.*;
import org.springframework.boot.json.GsonJsonParser;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author senn
 * @since 2023/4/4 15:56
 **/
public class JsonUtil {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
            .serializeNulls()
            .create();

    public static String parse(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String content, Class<T> t) {
        return (T) GSON.fromJson(content, t);
    }
}

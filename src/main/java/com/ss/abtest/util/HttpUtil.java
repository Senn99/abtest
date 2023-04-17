package com.ss.abtest.util;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author senn
 * @since 2023/4/17 16:58
 **/
public class HttpUtil {
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json;charset=utf-8");
    public static final OkHttpClient CLIENT = new OkHttpClient()
            .newBuilder()
            .connectTimeout(1, TimeUnit.SECONDS).build();

    public static void postMethod(String url, String message) {
        try {
            Request request = new Request.Builder()
                    .post(RequestBody.create(message, MEDIA_TYPE))
                    .url(url)
                    .build();
            CLIENT.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

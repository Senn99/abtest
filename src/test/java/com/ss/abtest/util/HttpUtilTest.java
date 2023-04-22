package com.ss.abtest.util;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpUtilTest {

    @Test
    void postMethod() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        String parse = JsonUtil.parse(list);
        HttpUtil.postMethod("http://localhost:8080/log", parse);
    }
}

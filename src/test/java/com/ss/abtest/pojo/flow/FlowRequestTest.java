package com.ss.abtest.pojo.flow;

import com.ss.abtest.util.JsonUtil;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class FlowRequestTest {

    public static void main(String[] args) {
        for (int i = 100000; i < 8000000; i++) {
            String key = String.format("%s:%s", i, "test_layer");
            int hash = key.hashCode() % 1000;
            hash = hash < 0 ? -hash : hash;
            if (hash < 10) {
                System.out.println(i);
            }
        }
    }

}

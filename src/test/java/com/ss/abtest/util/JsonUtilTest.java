package com.ss.abtest.util;

import com.ss.abtest.pojo.vo.FlightTable;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class JsonUtilTest {

    @Test
    void parse() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "A");
        map.put("b", "B");
        String parse = JsonUtil.parse(map);
        System.out.println(parse);

        FlightTable table = new FlightTable();
        table.setName("1");
        table.setCreateTime(LocalDateTime.now());
        table.setUpdateTime(LocalDateTime.now());
        String s = JsonUtil.parse(table);
        System.out.println(s);
    }


    @Test
    void fromJson() {
        String s = "{\"a\":\"A\",\"b\":\"B\"}";
        Map<String, String> map = JsonUtil.fromJson(s, Map.class);
        System.out.println(map);
    }
}

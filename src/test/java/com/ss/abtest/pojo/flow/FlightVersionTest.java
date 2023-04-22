package com.ss.abtest.pojo.flow;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FlightVersionTest {

    FlightVersion flightVersion = new FlightVersion();

    {
        Map<String, String> map = new HashMap<>();
        map.put("sex", "男");
        flightVersion.setFlightFilter(map);
    }

    @Test
    void checkFilter() {
        Map<String, String> map = new HashMap<>();
        map.put("sex", "男");
        boolean b = flightVersion.checkFilter(map);
        assertTrue(b);
    }
}

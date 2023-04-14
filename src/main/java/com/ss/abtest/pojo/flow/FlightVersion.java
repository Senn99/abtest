package com.ss.abtest.pojo.flow;

import com.ss.abtest.pojo.domain.Flight;
import com.ss.abtest.pojo.domain.Version;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author senn
 * @since 2023/4/4 11:06
 **/
@Data
public class FlightVersion {
    private String flightName;
    private String flightFilter;
    private int versionSize;
    private Map<Integer, Version> versions;

    public Version getVersion(int group){
        if (versions == null) {
            versions = new HashMap<>();
        }
        return versions.getOrDefault(group, new Version());
    }

    public boolean checkFilter(Map<String, String> requestConfig) {
        return false;
    }
}

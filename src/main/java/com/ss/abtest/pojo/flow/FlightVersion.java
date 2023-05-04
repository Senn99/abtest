package com.ss.abtest.pojo.flow;

import com.ss.abtest.pojo.domain.Flight;
import com.ss.abtest.pojo.domain.Version;
import com.ss.abtest.pojo.status.FlightStatus;
import io.swagger.models.auth.In;
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
    private FlightStatus flightStatus;
    private Map<String, String> flightFilter;
    private int versionSize;
    private Map<Integer, Version> versions;

    public Version getVersion(int group){
        if (versions == null) {
            versions = new HashMap<>();
        }
        return versions.getOrDefault(group, null);
    }

    public boolean checkFilter(Map<String, String> requestConfig) {
        for (Map.Entry<String, String> stringStringEntry : flightFilter.entrySet()) {
            String key = stringStringEntry.getKey();
            String value = String.valueOf(stringStringEntry.getValue());
            if (!requestConfig.containsKey(key)) {
                return false;
            }
            String config = String.valueOf(requestConfig.get(key));
            if (!value.equals(config)) {
                return false;
            }
        }
        return true;
    }
}

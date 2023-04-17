package com.ss.abtest.pojo.flow;

import com.ss.abtest.pojo.status.FlowUnit;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author senn
 * @since 2023/4/4 10:58
 **/
@Data
public class FlowData {
    private Long layerId;
    private Long companyId;
    private String layerName;
    private boolean needTestUser;
    private Map<String, FlightVersion> testUsers;
    private FlowUnit flowUnit;
    private Map<Integer, FlightVersion> bucket;

    public boolean checkBucketExist(int bk) {
        checkBucketMapNotNull();
        return bucket.containsKey(bk);
    }

    public FlightVersion getFlightVersion(int buckey) {
        checkBucketMapNotNull();
        return bucket.getOrDefault(buckey, null);
    }
    public Map<Integer, FlightVersion> getBucket() {
        checkBucketMapNotNull();
        return bucket;
    }

    private void checkBucketMapNotNull() {
        if (bucket == null) {
            bucket = new ConcurrentHashMap<>();
        }
    }
    private void checkTestUserMapNotNull() {
        if (testUsers == null) {
            testUsers = new HashMap<>();
        }
    }

    public FlightVersion getTestUserFlight(String unitValue) {
        checkTestUserMapNotNull();
        return testUsers.getOrDefault(unitValue, null);
    }

    public boolean containTestUser(FlowRequest request) {
        checkTestUserMapNotNull();
        if (testUsers.isEmpty()) {
            return false;
        }

        return testUsers.containsKey(request.getUnitValue());
    }
}

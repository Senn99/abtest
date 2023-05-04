package com.ss.abtest.pojo.flow;

import com.ss.abtest.exception.IllegalParamException;
import com.ss.abtest.pojo.status.FlowUnit;
import com.ss.abtest.util.JsonUtil;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.util.Date;
import java.util.Map;

/**
 * @author senn
 * @since 2023/4/4 10:42
 **/
@Data
public class FlowRequest {
    private String token;
    private String flowUnit;
    private String uid;
    private String did;
    private String rid;
    private String requestConfig;
    private Date createTime;

    public String getUnitValue() {
        switch (flowUnit) {
            case "uid":
                return uid;
            case "did":
                return did;
            case "rid":
                return rid;
            default:
                return "";
        }
    }

    public void verifyParams() {
        if (Strings.isEmpty(token)) {
            throw new IllegalParamException("token is empty");
        }
        if (flowUnit == null) {
            throw new IllegalParamException("flowUnit is null");
        }
        if (uid == null && did == null && rid == null) {
            throw new IllegalParamException("flowUnit value is null uid:did:rid = " + uid + ":" + did + ":" + rid);
        }
    }

    public Map<String, String> getRequestConfig() {
        return JsonUtil.fromJson(requestConfig, Map.class);
    }
}

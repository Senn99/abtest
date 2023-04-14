package com.ss.abtest.pojo.flow;

import com.ss.abtest.exception.IllegalParamException;
import com.ss.abtest.pojo.status.FlowUnit;
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
    private FlowUnit flowUnit;
    private String uid;
    private String did;
    private String rid;
    private Map<String, String> requestConfig;
    private Date createTime;

    public String getUnitValue() {
        switch (flowUnit) {
            case UID:
                return uid;
            case DID:
                return did;
            case RID:
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
}

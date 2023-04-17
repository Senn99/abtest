package com.ss.abtest.pojo.flow;

import com.ss.abtest.util.JsonUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.print.attribute.standard.JobName;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author senn
 * @since 2023/4/4 10:43
 **/
@Data
@NoArgsConstructor
public class FlowResponse {
    private Long flowId;
    private Long companyId;
    private List<Long> vidList;
    private Map<String, String> config;
    private FlowRequest request;
    private String message;
    private LocalDateTime createTime;

    public FlowResponse(String message) {
        this.message = message;
    }

    public static FlowResponse nullResponse(String message){
        return new FlowResponse(message);
    }

    public void addVid(Long versionId) {
        if (vidList == null) {
            vidList = new ArrayList<>();
        }
        vidList.add(versionId);
    }

    public void addVersionConfig(Map<String, String> versionConfig) {
        if (config == null) {
            config = new HashMap<>();
        }
        config.putAll(versionConfig);
    }

    public String getVidList() {
        if (vidList == null || vidList.isEmpty()) {
            return "[]";
        }
        return JsonUtil.parse(vidList);
    }

    public String getRequest() {
        if (request == null) {
            return "{}";
        }
        return JsonUtil.parse(request);
    }

    public String getCreate_time() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return  dateTimeFormatter.format(createTime);
    }
}

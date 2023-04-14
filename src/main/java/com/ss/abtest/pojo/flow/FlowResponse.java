package com.ss.abtest.pojo.flow;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author senn
 * @since 2023/4/4 10:43
 **/
@Data
public class FlowResponse {
    private Long flowId;
    private List<Long> vidList;
    private Map<String, String> config;
    private FlowRequest request;
    private Date createTime;

    public static FlowResponse nullResponse(){
        return new FlowResponse();
    }

    public void addVid(Long versionId) {
        if (vidList == null) {
            vidList = new ArrayList<>();
        }
        vidList.add(versionId);
    }

    public void addVersionConfig(Map<String, String> versionConfig) {
        config.putAll(versionConfig);
    }
}

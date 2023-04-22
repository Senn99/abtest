package com.ss.abtest.pojo.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ss.abtest.util.JsonUtil;
import lombok.Data;
import org.springframework.boot.json.GsonJsonParser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Data
public class Version {
    private Long versionId;
    private String name;
    private Long flightId;
    private String config;
    private LocalDateTime createTime;

    public Map<String, String> getMapConfig() {
         return JsonUtil.fromJson(config, Map.class);
    }

    public Long getFlight_id() {
        return flightId;
    }

    public String getCreate_time() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return  dateTimeFormatter.format(createTime);
    }
}

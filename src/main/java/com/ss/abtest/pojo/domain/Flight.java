package com.ss.abtest.pojo.domain;

import com.ss.abtest.util.JsonUtil;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;
import springfox.documentation.spring.web.json.Json;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Data
public class Flight {
    private Long flightId;
    private Long ownerId;
    private Long companyId;
    private String name;
    private Integer status;
    private Integer traffic;
    private Long layerId;
    private String filter;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public String getCreate_time() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return  dateTimeFormatter.format(createTime);
    }

    public String getUpdate_time() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return  dateTimeFormatter.format(updateTime);
    }

    public Map<String, String> getFilterMap() {
        if (Strings.isEmpty(filter)) {
            return new HashMap<>();
        }

        return JsonUtil.fromJson(filter, Map.class);
    }
}

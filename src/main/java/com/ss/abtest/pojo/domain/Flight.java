package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
}

package com.ss.abtest.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author senn
 * @since 2023/4/2 20:51
 **/
@Data
@AllArgsConstructor
public class Bucket {
    private Long flightId;
    private Long layerId;
    private Integer bucket;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public Bucket() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    public Long getFlight_id() {
        return flightId;
    }

    public Long getLayer_id() {
        return layerId;
    }

    public Bucket(Long flightId, Long layerId, Integer bucket) {
        this.flightId = flightId;
        this.layerId = layerId;
        this.bucket = bucket;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    public String getCreate_time() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return  dateTimeFormatter.format(createTime);
    }

    public String getUpdate_time() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return  dateTimeFormatter.format(updateTime);
    }
}

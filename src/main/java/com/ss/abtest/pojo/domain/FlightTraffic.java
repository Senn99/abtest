package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author senn
 * @since 2023/4/16 21:06
 **/
@Data
public class FlightTraffic {
    private Long id;
    private Long flightId;
    private Integer bucket;
    private Long layerId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

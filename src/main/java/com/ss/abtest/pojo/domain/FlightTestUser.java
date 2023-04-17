package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author senn
 * @since 2023/4/16 20:48
 **/
@Data
public class FlightTestUser {
    private Long id;
    private Long flightId;
    private String flowContent;
    private Date createTime;
}

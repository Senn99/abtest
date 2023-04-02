package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Flight {
    private Long flightId;
    private String name;
    private Integer Status;
    private Integer traffic;
    private Long layerId;
    private String filter;
    private Date createTime;
    private Date updateTime;
}

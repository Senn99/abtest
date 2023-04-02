package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Version {
    private Long versionId;
    private String name;
    private Long flightId;
    private String config;
    private Date createTime;
}

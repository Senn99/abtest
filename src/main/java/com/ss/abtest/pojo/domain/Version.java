package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.util.Date;
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
    private Date createTime;
}

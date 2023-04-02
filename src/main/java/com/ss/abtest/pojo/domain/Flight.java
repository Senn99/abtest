package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.util.Date;
/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Data
public class Flight {
    private Long flightId;
    private Long companyId;
    private String name;
    private Integer Status;
    private Integer traffic;
    private Long layerId;
    private String filter;
    private Date createTime;
    private Date updateTime;
}

package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Reflux {
    private Long refluxId;
    private Long companyId;
    private String url;
    private Integer tqs;
    private Date createTime;
    private Date updateTime;
}

package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.util.Date;

@Data
public class CompanyCreateApply {
    private Long createApplyId;
    private Long userId;
    private String companyName;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}

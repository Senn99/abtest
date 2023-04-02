package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.util.Date;

@Data
public class CompanyJoinApply {
    private Long joinApplyId;
    private Long userId;
    private Long companyId;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}

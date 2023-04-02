package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.util.Date;
/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Data
public class CompanyCreateApply {
    private Long createApplyId;
    private Long userId;
    private String companyName;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}

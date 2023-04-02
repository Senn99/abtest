package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.util.Date;
/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Data
public class CompanyJoinApply {
    private Long joinApplyId;
    private Long userId;
    private Long companyId;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}

package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.util.Date;
/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Data
public class Company {
    private Long companyId;
    private String name;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}

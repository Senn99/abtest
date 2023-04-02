package com.ss.abtest.pojo.domain;

import lombok.Data;

import java.util.Date;
/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Data
public class Reflux {
    private Long refluxId;
    private Long companyId;
    private String url;
    private Integer tqs;
    private Date createTime;
    private Date updateTime;
}

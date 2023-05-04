package com.ss.abtest.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author senn
 * @since 2023/4/27 11:30
 **/
@Data
public class CompanyTable {
    private Long companyId;
    private String name;
    private String creatorName;
    private Integer status;
    private LocalDateTime createTime;
}

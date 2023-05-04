package com.ss.abtest.pojo.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author senn
 * @since 2023/4/24 20:36
 **/
@Data
public class JoinApplyTable {
    private Long joinApplyId;
    private Long userId;
    private String userName;
    private Long companyId;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

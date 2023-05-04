package com.ss.abtest.pojo.domain;

import com.ss.abtest.exception.IllegalParamException;
import lombok.Data;

import java.time.LocalDateTime;
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
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public void checkIdNotNull() {
        if (userId == null) {
            throw new IllegalParamException("userId is null..");
        }
        if (companyId == null) {
            throw new IllegalParamException("companyId is null..");
        }
        if (createTime == null) {
            createTime = LocalDateTime.now();
        }
        if (updateTime == null) {
            updateTime = LocalDateTime.now();
        }
    }
}

package com.ss.abtest.pojo.domain;

import com.ss.abtest.exception.IllegalParamException;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.util.Date;
/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
@Data
public class CompanyCreateApply {
    private Long createApplyId;
    private Long companyId;
    private Long userId;
    private String companyName;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public void checkIdNotNull() {
        if (userId == null) {
            throw new IllegalParamException("userId is null..");
        }
        if (Strings.isEmpty(companyName)) {
            throw new IllegalParamException("companyName is empty..");
        }
        if (createTime == null) {
            createTime = LocalDateTime.now();
        }
        if (updateTime == null) {
            updateTime = LocalDateTime.now();
        }
    }
}

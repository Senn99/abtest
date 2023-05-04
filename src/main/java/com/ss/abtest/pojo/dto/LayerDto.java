package com.ss.abtest.pojo.dto;

import com.ss.abtest.exception.IllegalParamException;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;

/**
 * @author senn
 * @since 2023/4/26 15:02
 **/
@Data
public class LayerDto {
    private Long layerId;
    private Long companyId;
    private String name;
    private Integer test;
    private String token;
    private String flowUnit;
    private Integer traffic;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public LayerDto() {
        createTime = LocalDateTime.now();
        updateTime = LocalDateTime.now();
    }

    public void checkCreateParamsNotNull() {
        if (companyId == null) {
            throw new IllegalParamException("companyId is null..");
        }
        if (Strings.isEmpty(name)) {
            throw new IllegalParamException("name is empty.. name: " + name);
        }
        if (Strings.isEmpty(token)) {
            throw new IllegalParamException("token is empty.. token: " + token);
        }
        if (Strings.isEmpty(flowUnit)) {
            throw new IllegalParamException("flowUnit is empty.. flowUnit: " + flowUnit);
        }
        if (createTime == null) {
            createTime = LocalDateTime.now();
        }
        if (updateTime == null) {
            updateTime = LocalDateTime.now();
        }
    }
}

package com.ss.abtest.pojo.status;

/**
 * @author senn
 * @since 2023/4/24 20:10
 **/
public enum ApplyStatus {
    CREATED(0),
    AGREE(1),
    REFUSE(2);
    int status;

    ApplyStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}

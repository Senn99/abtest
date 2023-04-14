package com.ss.abtest.pojo.status;

/**
 * @author senn
 * @since 2023/4/4 10:45
 **/
public enum FlowUnit {
    UID("uid"),
    DID("did"),
    RID("rid");
    private final String value;

    FlowUnit(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

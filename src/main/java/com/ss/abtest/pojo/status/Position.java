package com.ss.abtest.pojo.status;

/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
public enum Position {
    CREATOR(0),
    NORMAL(2),
    ADMIN(3);
    int value;

    Position(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

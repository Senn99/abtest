package com.ss.abtest.pojo.status;

/**
 * @author senn
 * @since 2023/4/26 16:10
 **/
public enum RefluxStatus {
    CREATED(0),
    TEST(1),
    NORMAL(2),
    PAUSED(3),
    END(4);
    int value;

    RefluxStatus(int value) {
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }

    public static boolean checkStatus(int status){
        return status == 0 || status == 1 || status == 2 || status == 3 || status == 4;
    }

    public static RefluxStatus getStatus(int status){
        switch (status) {
            case 0:
                return CREATED;
            case 1:
                return TEST;
            case 2:
                return NORMAL;
            case 3:
                return PAUSED;
            default:
                return null;
        }
    }
}

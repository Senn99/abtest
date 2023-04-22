package com.ss.abtest.pojo.status;

/**
 * @author senn
 * @since 2023/4/2 20:18
 **/
public enum FlightStatus {
    CREATED(0),
    TEST(1),
    NORMAL(2),
    PAUSED(3),
    END(4);
    int value;

    FlightStatus(int value) {
        this.value = value;
    }
    public int getValue(){
        return this.value;
    }
    public static FlightStatus getStatus(int status){
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

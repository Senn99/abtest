package com.ss.abtest.pojo.status;

/**
 * @author senn
 * @since 2023/4/2 20:18
 **/
public enum FlightStatus {
    CREATED(0),
    NORMAL(1),
    PAUSED(2);
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
                return NORMAL;
            case 2:
                return PAUSED;
            default:
                return null;
        }
    }
}

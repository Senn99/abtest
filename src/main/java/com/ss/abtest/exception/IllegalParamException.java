package com.ss.abtest.exception;

/**
 * @author senn
 * @since 2023/4/2 19:50
 **/
public class IllegalParamException extends RuntimeException {
    public IllegalParamException(String msg) {
        super(msg);
    }
}

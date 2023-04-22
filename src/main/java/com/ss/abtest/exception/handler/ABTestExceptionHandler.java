package com.ss.abtest.exception.handler;

import com.ss.abtest.exception.IllegalParamException;
import com.ss.abtest.pojo.RequestResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author senn
 * @since 2023/4/21 16:01
 **/
@ControllerAdvice
public class ABTestExceptionHandler {
    /**
     * 参数异常
     */
    @ExceptionHandler(value = IllegalParamException.class)
    @ResponseBody
    public String eHandler(Exception e) {
        System.out.println("请求参数异常 >>> : " + e);
        return RequestResult.requestErrorResult("请求参数异常,错误原因 >>> " + e.getMessage()) ;
    }
}

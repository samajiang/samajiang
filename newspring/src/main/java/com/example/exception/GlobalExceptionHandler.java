package com.example.exception;

import com.example.common.Result;
import com.example.controller.AdminController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
@CrossOrigin
@ControllerAdvice(basePackages = {"com.example.controller"})
public class GlobalExceptionHandler {

private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//统一异常处理@ExceptionHandler，主要用于Exceptio
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(HttpServletRequest request, Exception e){
        log.error("异常信息：",e);
        return Result.error("系统异常");
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result customError(HttpServletRequest request, CustomException e){
        return Result.error(e.getMsg());
    }
}

package com.gy.controller;

import com.gy.dto.Dto;
import com.gy.dto.DtoUtil;
import com.gy.exceptions.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public abstract class BaseController {
    //只处理ServiceException及子孙异常
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Dto<Void> handleException(Exception e) {
        //判断异常类型,并处理
        //301-参数违法异常
        if (e instanceof IllegalParamsException) {
            return DtoUtil.returnFail(e.getMessage(), 301 + "");
            //401-用户名冲突异常
        } else if (e instanceof UsernameConflictExcaption) {
            return DtoUtil.returnFail(e.getMessage(), 401 + "");
            //402-数据查询异常
        } else if (e instanceof DataNotFoundException) {
            return DtoUtil.returnFail(e.getMessage(), 402 + "");
            //403-登录异常
        } else if (e instanceof LoginFailException) {
            return DtoUtil.returnFail(e.getMessage(), 403 + "");
            //404-上传异常
        } else if (e instanceof UploadFileException ) {
            return DtoUtil.returnFail(e.getMessage(), 403 + "");
            //405-登录超时
        } else if (e instanceof LoginTimeOutException ) {
            return DtoUtil.returnFail(e.getMessage(), 403 + "");
            //501-插入数据异常
        } else if (e instanceof InsertDataException) {
            return DtoUtil.returnFail(e.getMessage(), 501 + "");
            //502-系统异常请稍后重试（IO传输异常）
        } else if (e instanceof TransmissionException) {
            return DtoUtil.returnFail(e.getMessage(), 502 + "");
        }
        return null;
    }

}

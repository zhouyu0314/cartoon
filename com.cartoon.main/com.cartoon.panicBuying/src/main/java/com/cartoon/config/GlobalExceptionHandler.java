package com.cartoon.config;

import com.cartoon.dto.Dto;
import com.cartoon.dto.DtoUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

/**
 * 全局异常处理，主要处理了上传文件超出设定的异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = MultipartException.class)
    @ResponseBody
    public Dto handleBusinessException(Exception e) {
        if (e instanceof MaxUploadSizeExceededException) {

            return DtoUtil.returnFail("文件上传异常，请检查文件大小是否超限！", 404+"");
        }


        return null;




    }
}

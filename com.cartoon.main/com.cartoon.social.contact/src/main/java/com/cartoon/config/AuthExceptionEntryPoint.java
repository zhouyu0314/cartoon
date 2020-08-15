package com.cartoon.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 无效token 异常重写
 */
@Component

public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {

    private static final int UN_LOGIN = 10002;

    private static final int INVALID_TOKEN = 302;

    private static final int UPLOADFILEEXCEPTION  = 404;

    @Override

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws ServletException {
        Map<String, Object> map = new HashMap<>();
        Throwable cause = authException.getCause();
        if (cause instanceof InvalidTokenException) {
            map.put("success", "false");
            map.put("errorCode", INVALID_TOKEN);
            map.put("msg", "身份验证错误或已过期，请重新登录");
            map.put("data", null);
        }else if(cause instanceof FileUploadBase.FileSizeLimitExceededException){
            map.put("success", "false");
            map.put("errorCode", UPLOADFILEEXCEPTION);
            map.put("msg", "上传文件过大！");
            map.put("data", null);
        }/*else {
            //不会走到这里，网关会把没有携带token的拦截
            map.put("code", INVALID_TOKEN);
            map.put("msg", "访问此资源需要完全的身份验证");
        }*/
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception e) {
            throw new ServletException();
        }
    }
}


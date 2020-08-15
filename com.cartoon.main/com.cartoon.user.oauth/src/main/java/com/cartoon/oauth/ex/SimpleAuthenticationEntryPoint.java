package com.cartoon.oauth.ex;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
  * @author dax
  * @since 2019/11/6 22:11
  */
 public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {
     private static final int UN_LOGIN = 10002;

     private static final int INVALID_TOKEN = 302;


     @Override
     public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
         Map<String, Object> map = new HashMap<>();
         Throwable cause = authException.getCause();
         if (cause instanceof InvalidTokenException) {
             map.put("success", "false");
             map.put("errorCode", INVALID_TOKEN);
             map.put("msg", "授权服务异常，请检查用户名或密码！");
             map.put("data", null);
         } else {
             map.put("success", "false");
             map.put("errorCode", INVALID_TOKEN);
             map.put("msg", "授权服务异常，请检查用户名或密码！");
             map.put("data", null);
         }
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
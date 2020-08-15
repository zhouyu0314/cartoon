package com.cartoon.controller;

import com.cartoon.dto.Dto;
import com.cartoon.dto.DtoUtil;
import com.cartoon.entity.AuthToken;
import com.cartoon.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oath/user")
@CrossOrigin
@Api(value = "授权中心", description = "授权中心")
public class AuthController {

    @Value("${auth.clientId}")
    private String clientId;
    @Value("${auth.clientSecret}")
    private String clientSecret;
    @Value("${auth.ttl}")
    private String ttl;
    @Autowired
    private AuthService authService;




    @ApiOperation(value = "用户登录", notes = "向用户颁发令牌,可能会抛出的异常：UnauthorizedException(授权异常)")
    @PostMapping(value = "/login")

    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", value = "用户手机号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true, dataType = "String", paramType = "query"),
    })
    public Dto login(String phone , String password){
        String username = phone;
        AuthToken authToken = authService.userLogin(username, password, clientId, clientSecret, ttl);

        return DtoUtil.returnSuccess("success",authToken);
    }





//    @GetMapping("/user/logout")
//    public Dto loginOut(String username){
//        authService.LoginOut(username);
//        return DtoUtil.returnSuccess("success");
//    }

}

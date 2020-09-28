package com.gy.controller;

import com.gy.dto.Dto;
import com.gy.dto.DtoUtil;
import com.gy.entity.User;
import com.gy.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@EnableCircuitBreaker
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @HystrixCommand
    @PostMapping("/getUserById")
    public Dto getUserById(@RequestBody Map<String,Object> params){
        User user = userService.getUserById(params);

        return DtoUtil.returnSuccess("用户信息",user);
    }

    @HystrixCommand
    @PostMapping("/getUserOrder")
    public Dto  getUserOrder(@RequestBody Map<String,Object> params){
        return userService.getOrderByUserID(params);
    }


    //************************服务降级*******************************

    public Dto getUserByIdError(@RequestBody Map<String,Object> params){

        return DtoUtil.returnSuccess("服务降级",new User(999L,"服务降级","服务降级"));
    }

}

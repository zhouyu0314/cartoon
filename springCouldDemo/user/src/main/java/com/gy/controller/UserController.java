package com.gy.controller;

import com.gy.dto.Dto;
import com.gy.dto.DtoUtil;
import com.gy.entity.User;
import com.gy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
@EnableCircuitBreaker
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @PostMapping("/getUserById")
    public Dto getUserById(@RequestBody Map<String,Object> params){
        User user = userService.getUserById(params);
        return DtoUtil.returnSuccess("根据id查询用户信息",user);
    }

    @PostMapping("/getUserList")
    public Dto getUserList(@RequestBody Map<String,Object> params){
        List<User> userList = userService.getUserListByMap(params);
        return DtoUtil.returnSuccess("查询范围用户信息",userList);
    }

    @PostMapping("/getUserCount")
    public Dto getUserCount(@RequestBody Map<String,Object> params){
        Integer count = userService.getUserCountByMap(params);
        return DtoUtil.returnSuccess("查询用户数量",count);
    }

    @PostMapping("/insertUser")
    public Dto insertUser(@RequestBody Map<String,Object> params){
        userService.insertUser(params);
        return DtoUtil.returnSuccess("新增用户");
    }

    @PostMapping("/updateUser")
    public Dto updateUser(@RequestBody Map<String,Object> params){
        userService.updateUser(params);
        return DtoUtil.returnSuccess("修改用户信息");
    }

}

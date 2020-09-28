package com.gy.service;

import com.gy.dto.Dto;
import com.gy.entity.User;
import com.gy.exceptions.UsernameConflictExcaption;

import java.util.Map;

public interface UserService {

    /**
     * 查询用户信息
     */
    User getUserById(Map<String,Object> params)throws UsernameConflictExcaption;

    /**
     * 查询我的订单
     */
    Dto getOrderByUserID(Map<String,Object> params);
}

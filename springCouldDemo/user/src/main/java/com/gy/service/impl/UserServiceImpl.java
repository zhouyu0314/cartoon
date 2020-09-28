package com.gy.service.impl;

import com.gy.dto.Dto;
import com.gy.entity.User;
import com.gy.exceptions.UsernameConflictExcaption;
import com.gy.feign.OrderFeignClient;
import com.gy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    private OrderFeignClient orderFeignClient;

    @Override
    public User getUserById(Map<String, Object> params)throws UsernameConflictExcaption {
        if (params.get("id") == null || Long.valueOf(params.get("id").toString()) == 1L) {
            throw new NullPointerException("自定义空指针！");
        }

        if (params.get("id") == null || Long.valueOf(params.get("id").toString()) == 2L) {
            throw new UsernameConflictExcaption("用户名冲突！");
        }
        User user = new User();
        user.setId(1L);
        user.setUsername("张三");
        user.setNickname("阿三");
        return user;
    }

    @Override
    public Dto getOrderByUserID(Map<String, Object> params) {
        Dto orderDto = orderFeignClient.getOrderByUserID(params);
        /*
        ...业务逻辑...
         */
        return orderDto;
    }
}

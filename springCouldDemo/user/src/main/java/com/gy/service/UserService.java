package com.gy.service;

import com.gy.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User getUserById(Map<String,Object> params);

    List<User> getUserListByMap(Map<String,Object> params);

    Integer getUserCountByMap(Map<String,Object> params);

    Integer insertUser(Map<String,Object> params);

    Integer updateUser(Map<String,Object> params);
}

package com.cartoon.mapper;


import com.cartoon.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    /**
     * 用户注册
     */
    /**
     * 使用手机号注册
     */
    Integer insertUser(User user);



//--------------------------------------------------

    User getUserById(@Param(value = "id") Long id);

    List<User> getUserListByMap(Map<String,Object> param);

    Integer getUserCountByMap(Map<String,Object> param);

    Integer updateUser(User user);

    @Select("select nickname,vip,gold,ticket,score,coupon,head_img as headImg from user where phone=#{phone}")
    User getUserInfo(String phone);


}
package com.gy.mapper;
import com.gy.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

	public User getUserById(@Param(value = "id") Long id);

	public List<User> getUserListByMap(Map<String, Object> param);

	public Integer getUserCountByMap(Map<String, Object> param);

	public Integer insertUser(User user);

	public Integer updateUser(User user);


}

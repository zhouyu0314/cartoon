package com.gy.mapper;


import com.gy.annotation.DataSource;
import com.gy.cons.Cons;
import com.gy.entity.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Auther: yukong
 * @Date: 2018/8/13 19:47
 * @Description: UserMapper接口
 */
@Mapper
public interface PersonMapper {

    /**
     * 新增用户
     * @param person
     * @return
     */
    @DataSource  //默认数据源
    int save(Person person);

    /**
     * 更新用户信息
     * @param person
     * @return
     */
    @DataSource("slave1")  //默认数据源
    int update(Person person);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DataSource  //默认数据源
    int deleteById(Long id);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @DataSource("slave1")  //slave1
    Person selectById(Long id);

    /**
     * 查询所有用户信息
     * @return
     */
    @DataSource(Cons.TEST)  //slave1
    List<Person> selectAll();
}

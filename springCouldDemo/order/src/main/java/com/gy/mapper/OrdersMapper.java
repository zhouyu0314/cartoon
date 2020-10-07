package com.gy.mapper;

import com.gy.annotation.DataSource;
import com.gy.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrdersMapper {
    @DataSource
    Orders getOrdersById(@Param(value = "id") Long id);
    @DataSource
    List<Orders> getOrdersListByMap(Map<String, Object> param);
    @DataSource
    Integer getOrdersCountByMap(Map<String, Object> param);
    @DataSource
    Integer insertOrders(Orders orders);
    @DataSource
    Integer updateOrders(Orders orders);


}

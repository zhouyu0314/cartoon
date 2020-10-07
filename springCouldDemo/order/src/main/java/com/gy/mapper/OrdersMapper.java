package com.gy.mapper;

import com.gy.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrdersMapper {
    Orders getOrdersById(@Param(value = "id") Long id);
    List<Orders> getOrdersListByMap(Map<String, Object> param);
    Integer getOrdersCountByMap(Map<String, Object> param);
    Integer insertOrders(Orders orders);
    Integer updateOrders(Orders orders);


}

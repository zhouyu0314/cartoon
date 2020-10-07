package com.gy.service;

import com.gy.entity.Orders;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Orders getOrdersById(Map<String,Object> params);

    List<Orders> getOrdersListByMap(Map<String,Object> params);

    Integer getOrdersCountByMap(Map<String, Object> params);

    Integer insertOrders(Map<String, Object> params);

    Integer updateOrders(Map<String,Object> params);
}

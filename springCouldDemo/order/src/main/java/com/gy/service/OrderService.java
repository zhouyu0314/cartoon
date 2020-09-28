package com.gy.service;

import com.gy.entity.Order;
import com.gy.exceptions.IllegalParamsException;

import java.util.Map;

public interface OrderService {

    /**
     * 获取用户的订单
     */
    Order getOrderByUserID(Map<String,Object> params) throws IllegalParamsException;
}

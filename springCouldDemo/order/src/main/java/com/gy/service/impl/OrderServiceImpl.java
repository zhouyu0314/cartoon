package com.gy.service.impl;

import com.gy.entity.Order;
import com.gy.exceptions.IllegalParamsException;
import com.gy.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Order getOrderByUserID(Map<String, Object> params) throws IllegalParamsException {
        if (params.get("id") == null || Long.valueOf(params.get("id").toString()) == 1L) {
            throw new IllegalParamsException("参数违法！");
        }
        Order order = new Order();
        order.setId(10001L);
        order.setUserID(1L);
        order.setTotalPrice(998.00);
        return order;
    }
}

package com.gy.service.impl;

import com.gy.entity.OrderItem;
import com.gy.mapper.OrderItemMapper;
import com.gy.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired(required = false)
    private OrderItemMapper orderItemMapper;

    @Override
    public OrderItem getOrderItemById(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<OrderItem> getOrderItemListByMap(Map<String, Object> params) {
        return null;
    }

    @Override
    public Integer getOrderItemCountByMap(Map<String, Object> params) {
        return null;
    }

    @Override
    public Integer insertOrderItem(OrderItem orderItem) {
        return this.addOrderItem(orderItem);
    }

    @Override
    public Integer updateOrderItem(Map<String, Object> params) {
        return null;
    }

    //*************************************************
    private Integer addOrderItem(OrderItem orderItem){
        return orderItemMapper.insertOrderItem(orderItem);
    }
}

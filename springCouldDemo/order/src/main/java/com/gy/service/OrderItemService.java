package com.gy.service;

import com.gy.entity.OrderItem;

import java.util.List;
import java.util.Map;

public interface OrderItemService {
    OrderItem getOrderItemById(Map<String, Object> params);

    List<OrderItem> getOrderItemListByMap(Map<String, Object> params);

    Integer getOrderItemCountByMap(Map<String, Object> params);

    Integer insertOrderItem(OrderItem orderItem);

    Integer updateOrderItem(Map<String, Object> params);
}

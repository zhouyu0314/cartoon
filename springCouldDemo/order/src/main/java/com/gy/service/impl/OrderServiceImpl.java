package com.gy.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.gy.entity.Gift;
import com.gy.entity.OrderItem;
import com.gy.entity.Orders;
import com.gy.mapper.OrdersMapper;
import com.gy.service.OrderItemService;
import com.gy.service.OrderService;
import com.gy.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired(required = false)
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderItemService orderItemService;



    @Override
    public Orders getOrdersById(Map<String, Object> params) {
        return ordersMapper.getOrdersById(Long.valueOf( params.get("id").toString()));
    }

    @Override
    public List<Orders> getOrdersListByMap(Map<String, Object> params) {
        return ordersMapper.getOrdersListByMap(params);
    }

    @Override
    public Integer getOrdersCountByMap(Map<String, Object> params) {
        return ordersMapper.getOrdersCountByMap(params);
    }

    @Override
    public Integer insertOrders(Map<String, Object> params) {
        List<Gift> gifts = JSONObject.parseArray(JSONObject.toJSONString(params.get("gifts")), Gift.class);
        Orders orders = JSONObject.parseObject(JSONObject.toJSONString(params), Orders.class);
        orders.setId(IdWorker.getId());
        //生成详情
        for (Gift gift : gifts) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrdersId(orders.getId());
            orderItem.setGiftId(gift.getId());
            orderItem.setId(IdWorker.getId());
            orderItem.setItemCount(gift.getCount());
            orderItem.setItemPrice(gift.getCount() * gift.getPrice());
            orderItemService.insertOrderItem(orderItem);
        }

        return this.addOrders(orders);
    }

    @Override
    public Integer updateOrders(Map<String, Object> params) {
        return null;
    }


    //**************************************************
    private Integer addOrders(Orders orders){
        return ordersMapper.insertOrders(orders);
    }

}

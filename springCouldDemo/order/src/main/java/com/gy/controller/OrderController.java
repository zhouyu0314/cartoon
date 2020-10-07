package com.gy.controller;

import com.gy.dto.Dto;
import com.gy.dto.DtoUtil;
import com.gy.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/order")
@EnableCircuitBreaker
@CrossOrigin
public class OrderController extends BaseController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/genOrders")
    public Dto genOrders(@RequestBody Map<String,Object> params){
        if (true) {
            throw new RuntimeException("报错了！");
        }
        orderService.insertOrders(params);
        return DtoUtil.returnSuccess("生成订单");
    }
}

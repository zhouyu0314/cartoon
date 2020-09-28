package com.gy.controller;

import com.gy.dto.Dto;
import com.gy.dto.DtoUtil;
import com.gy.entity.Order;
import com.gy.service.OrderService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand
    @PostMapping("/getOrderByUserID")
    public Dto getOrderByUserID(@RequestBody Map<String,Object> params){
        Order order = orderService.getOrderByUserID(params);
        return DtoUtil.returnSuccess("用户订单信息",order);
    }
}

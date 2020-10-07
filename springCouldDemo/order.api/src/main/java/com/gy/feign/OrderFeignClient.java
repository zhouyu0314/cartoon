package com.gy.feign;

import com.gy.dto.Dto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "com.gy.order")
public interface OrderFeignClient {

        @PostMapping("/api/order/genOrders")
        Dto genOrders(@RequestBody Map<String,Object> params);

}

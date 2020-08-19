package com.cartoon.feign;

import com.cartoon.dto.Dto;
import com.cartoon.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("com.cartoon.user")
public interface UserFeignClient {
    /**
     * oauth调用，查询用户密码
     *
     * @param phone
     * @return
     */
    @PostMapping("/api/user/serchUserByPhoneFeign")
    User serchUserByPhoneFeign(@RequestBody String phone);


    //查询用户的头像，昵称，vip,元宝,月票,积分 feign调用
    @GetMapping("/api/user/getUserInfo/{phone}")
    User getUserInfo(@PathVariable("phone") String phone);



    /**
     * oauth调用，验证用户vip合法性
     *
     * @param user
     */
    @PostMapping("/checkVip")
    void checkVip(@RequestBody User user);


    /**
     * 前端需提供
     * 1.用户phone
     * 2.记录分类type（元宝（gold）1、阅读券(coupon)2、积分(score)3、月票(ticket)4、vip5）
     * 3.记录描述recordReason
     * 4.数量count
     * 5.消费目标/获得来源target
     * @param params
     * @return
     */
    @PostMapping("/api/user/updateUsergoldOrTicketOrScoreOrCoupon")
    Dto updateUsergoldOrTicketOrScoreOrCoupon(@RequestBody Map<String, Object> params);
}

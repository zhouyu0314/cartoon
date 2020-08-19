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

    @PostMapping("/updateUsergoldOrTicketOrScoreOrCoupon")
    Dto updateUsergoldOrTicketOrScoreOrCoupon(@RequestBody Map<String, Object> params);
}

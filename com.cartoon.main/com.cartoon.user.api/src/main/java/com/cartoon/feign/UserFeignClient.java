package com.cartoon.feign;

import com.cartoon.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("com.cartoon.user")
public interface UserFeignClient {
    /**
     * oauth调用，查询用户密码
     * @param phone
     * @return
     */
    @PostMapping("/api/user/serchUserByPhoneFeign")
    User serchUserByPhoneFeign(@RequestBody String phone);


//    @PostMapping("/api/user/changeUserInfoforfeign")
//    void changeUserInfoForFeign(User user);

    //查询用户的图像，昵称，vip feign调用，目前没用 备用吧
    @GetMapping("/api/user/getUserInfo/{phone}")
    User getUserInfo(@PathVariable("phone") String phone);

    /**
     * oauth调用，验证用户vip合法性
     * @param user
     */
    @PostMapping("/checkVip")
    void checkVip(@RequestBody User user);
}

package com.cartoon.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("com.cartoon.social.contact")
public interface CommentFeignClient {

}

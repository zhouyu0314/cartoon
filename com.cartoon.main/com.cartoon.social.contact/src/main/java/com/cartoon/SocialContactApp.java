package com.cartoon;

import com.cartoon.filter.MyFeignInteceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SocialContactApp {
    public static void main(String[] args) {
        SpringApplication.run(SocialContactApp.class,args);
    }

    @Bean
    public MyFeignInteceptor createMyFeignInteceptor() {
        return new MyFeignInteceptor();
    }
}

package com.cartoon;

import com.cartoon.filter.MyFeignInteceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableAsync
@EnableFeignClients
public class PanicApp {
    public static void main(String[] args) {
        SpringApplication.run(PanicApp.class, args);
    }

    @Bean
    public MyFeignInteceptor createMyFeignInteceptor() {
        return new MyFeignInteceptor();
    }
}

package com.gy;

import com.gy.aop.DynamicDataSourceAnnotationAdvisor;
import com.gy.aop.DynamicDataSourceAnnotationInterceptor;
import com.gy.config.DynamicDataSourceRegister;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@Import(DynamicDataSourceRegister.class)
@EnableTransactionManagement
public class GiftApp {
    public static void main(String[] args) {

        SpringApplication.run(GiftApp.class,args);
    }

    @Bean
    public DynamicDataSourceAnnotationAdvisor dynamicDatasourceAnnotationAdvisor() {
        return new DynamicDataSourceAnnotationAdvisor(new DynamicDataSourceAnnotationInterceptor());
    }
}

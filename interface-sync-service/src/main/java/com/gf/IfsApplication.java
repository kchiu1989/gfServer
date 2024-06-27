package com.gf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan({"com.gf.biz.**.mapper"})
public class IfsApplication {

    public static void main(String[] args) {
        SpringApplication.run(IfsApplication.class, args);
    }
}

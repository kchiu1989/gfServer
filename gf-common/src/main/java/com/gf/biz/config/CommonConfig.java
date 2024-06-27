package com.gf.biz.config;

import com.gf.biz.common.util.SpringBeanUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Bean
    public SpringBeanUtil springUtil(){
        return new SpringBeanUtil();
    }
}

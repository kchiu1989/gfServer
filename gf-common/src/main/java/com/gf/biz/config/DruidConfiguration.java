package com.gf.biz.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;


public class DruidConfiguration {
    @Bean
    public DataSource druidDataSource() {
        // 其实这里的信息可以拿配置文件的，我这里写死了
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/gfMain?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("Yuanjuan1234.");
        // 这里就是把我们德鲁伊数据源交给seata代理
        DataSourceProxy dataSourceProxy = new DataSourceProxy(druidDataSource);
        return dataSourceProxy;
    }

}

package com.gf.cloud.config;

import com.gf.cloud.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * SpringSecurity配置
 * Created by gf on 2022/10/8.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManager();
    }

    /**
     * @return
     * @throws Exception
     * 注： 配置加载用户信息（用户名、密码、权限、角色等信息）
     */
    @Bean("userService")
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new UserService();
    }

    /**
     *  配置认证信息
     * @param auth the {@link AuthenticationManagerBuilder} to use
     * @throws Exception
     * 注： 这个配置一定不可以省略，如果省略会造成栈溢出，导致了springsecurity和oauth2之间的循环调用
     *  自定义用户认证的用户信息加载服务
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserService());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeRequests()
                //.antMatchers("/oauth/**", "/login/**", "/logout/**")
                //.permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll();
    }
}

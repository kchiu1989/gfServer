package com.gf.cloud.service;

import com.gf.cloud.domain.User;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by gf on 2022/10/01.
 */
//@Service
public class UserService implements UserDetailsService {
    private List<User> userList;

    private PasswordEncoder passwordEncoder;

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    //@PostConstruct
    public void initData() {
        passwordEncoder = new BCryptPasswordEncoder();

        String password = passwordEncoder.encode("123456");
        userList = new ArrayList<>();
        userList.add(new User("gf", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        userList.add(new User("andy", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        userList.add(new User("mark", password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //List<User> findUserList = userList.stream().filter(user -> user.getUsername().equals(username)).collect(Collectors.toList());
        /*if (!CollectionUtils.isEmpty(findUserList)) {
            return findUserList.get(0);
        } */if("gf".equals(username)){
            passwordEncoder = new BCryptPasswordEncoder();
            String password = passwordEncoder.encode("123456");
            passwordEncoder = new BCryptPasswordEncoder();
            return new User("gf", password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
        }else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
    }
}

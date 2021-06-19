package com.example.blogserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/17
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    @Qualifier("myPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    /**
     *
     * @description 此方法会在用户登录的时候执行
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据名称查出所拥有的角儿

        //根据角色查出对应的权限

        //把每一个权限构造如SimpleGrantedAuthority
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if ("account".equals(username)) {
            GrantedAuthority grantedAuthority1 = new SimpleGrantedAuthority("customer");
            GrantedAuthority grantedAuthority2 = new SimpleGrantedAuthority("all");
            grantedAuthorities.add(grantedAuthority1);
            grantedAuthorities.add(grantedAuthority2);
        } else {
            GrantedAuthority grantedAuthority3 = new SimpleGrantedAuthority("what");
            grantedAuthorities.add(grantedAuthority3);
        }

        //返回security的user对象
        User user = new User("account", passwordEncoder.encode("wushichao"), grantedAuthorities);
        return user;
    }
}

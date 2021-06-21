package com.example.blogserver.service;

import com.example.blogserver.entity.ControlAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
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
    @Autowired
    ControlAccount controlAccount;
    //账户未被锁定
    Boolean accountNonLocked = true;

    /**
     * @param username
     * @return
     * @throws UsernameNotFoundException
     * @description 此方法会在用户登录的时候执行
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //检查下当前账户是不是被锁定,三次就是锁定
        Integer num = controlAccount.getLockTable().get(username);
        if (null != num && 3 <= num.intValue()) {
            accountNonLocked = false;
        }
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
            throw new BadCredentialsException("用户不存在");
        }
        //返回security的user对象
        //this(username, password, true, true, true, true, authorities);
        User user = new User("account", passwordEncoder.encode("wushichao")
                , true, true, true, accountNonLocked, grantedAuthorities);

        return user;
    }
}

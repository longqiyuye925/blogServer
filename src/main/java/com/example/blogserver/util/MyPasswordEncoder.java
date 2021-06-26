package com.example.blogserver.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Description:密码加解密，这里为了方便观察用tostring当做一种加密算法
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/18
 */
@Component
public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}

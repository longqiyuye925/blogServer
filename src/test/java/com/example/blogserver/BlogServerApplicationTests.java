package com.example.blogserver;

import com.example.blogserver.util.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

@SpringBootTest
class BlogServerApplicationTests {
    /**
     * 生成token
     */
    @Test
    void contextLoads() {
        String token = JWTUtil.createToken("account");
        System.out.println(token);
        //AntPathMatcher pathMatcher = new AntPathMatcher();
        //assert (pathMatcher.match("*bla*/**/bla/**", "XXXblaXXXX/testing/testing/bla/testing/testing/"));
    }
    /**
     * 生成加密密码
     */
}

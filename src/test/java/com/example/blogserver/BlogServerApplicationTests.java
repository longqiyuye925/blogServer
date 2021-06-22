package com.example.blogserver;

import com.example.blogserver.util.JWTUtil;
import com.example.blogserver.util.RedisUtil;
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
     * 测试token存入redis正不正常
     */
    @Test
    void tokenInRedis(){
        String tokenStr = JWTUtil.createToken("wsc");
        System.out.println("tokenStr" + tokenStr);
        RedisUtil redisUtil = new RedisUtil();
        redisUtil.setex("jinan0621",tokenStr);
    }
}

package com.example.blogserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Description:
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 06 2021/6/21
 */
@Configuration
    public class RedisConfig {
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        RedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
        //STRING的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key的序列化采用String类型的
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //hash的key也采用String类型
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        //value采用jackson类型
        redisTemplate.setHashValueSerializer(serializer);
        return redisTemplate;
    }
}

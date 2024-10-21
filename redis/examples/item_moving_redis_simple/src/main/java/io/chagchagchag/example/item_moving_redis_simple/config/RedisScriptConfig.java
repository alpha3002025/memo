package io.chagchagchag.example.item_moving_redis_simple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RedisScriptConfig {
    @Primary
    @Bean
    public RedisScript<String> createOneScript(){
        ClassPathResource resource = new ClassPathResource("redis/cat_rpush.lua");
        return RedisScript.of(resource, String.class);
    }

    @Bean
    public RedisScript<List<String>> catMovingEndScript(){
        ClassPathResource resource = new ClassPathResource("redis/cat_moving_end.lua");
        List<String> list = new ArrayList<>();
        return RedisScript.of(resource, (Class<List<String>>) list.getClass());
    }

    @Bean
    public RedisScript<String> catMovingOneStepScript(){
        ClassPathResource resource = new ClassPathResource("redis/cat_moving_one_step.lua");
        return RedisScript.of(resource, String.class);
    }
}

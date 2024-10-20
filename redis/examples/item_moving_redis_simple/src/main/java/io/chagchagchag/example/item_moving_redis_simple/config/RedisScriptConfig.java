package io.chagchagchag.example.item_moving_redis_simple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RedisScriptConfig {
    @Bean
    public RedisScript<List<String>> catMovingEndScript(){
        ClassPathResource resource = new ClassPathResource("redis/cat_moving_end.lua");
        return RedisScript.of(resource, (Class<List<String>>) new ArrayList<String>().getClass());
    }

    @Bean
    public RedisScript<String> catMovingOneStepScript(){
        ClassPathResource resource = new ClassPathResource("redis/cat_moving_one_step.lua");
        return RedisScript.of(resource, String.class);
    }
}

package io.chagchagchag.example.redis_lua_example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.RedisScript;

@Configuration
public class RedisScriptsConfig {
    @Bean
    public RedisScript<String> catLuaScript() {
        ClassPathResource classPathResource = new ClassPathResource("redis/lua_script.lua");
        return RedisScript.of(classPathResource, String.class);
    }
}

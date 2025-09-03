package click.example.redis_lua_example.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.RedisScript;

@Configuration
public class RedisScriptsConfig {
    @Bean(name = "postActivityRedisScript")
    public RedisScript<String> postActivityRedisScript() {
        ClassPathResource resource = new ClassPathResource("redis/scripts/post-activity-redis.lua");
        return RedisScript.of(resource, String.class);
    }

    @Bean(name = "jsonAddCatList")
    public RedisScript<String> jsonAddCatRedisScript() {
        ClassPathResource resource = new ClassPathResource("redis/scripts/add-cat-json-redis.lua");
        return RedisScript.of(resource, String.class);
    }
}

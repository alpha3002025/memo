package io.chagchagchag.sample.sample_redis_lua_script.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {
  @Value("${spring.data.redis.host}")
  private String redisHost;

  @Value("${spring.data.redis.port}")
  private int redisPort;

  @Bean
  RedissonClient redissonClient(){
    Config config = new Config();

    String address = "redis://%s:%s".formatted(redisHost, redisPort);

    config.useSingleServer().setAddress(address);
    return Redisson.create();
  }
}

package io.example.feign.mvc_feign_circuit_breaker_example.config.ratelimiter;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import java.time.Duration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonPlaceholderRateLimiterConfig {
  @Bean
  public RateLimiter jsonPlaceholderRateLimiter(){
    RateLimiterConfig config = RateLimiterConfig.custom()
        .timeoutDuration(Duration.ofMillis(15)) // thread 가 permission 을 얻기까지 기다릴 default 대기 시간(timeout 개념)
        .limitForPeriod(30) // period 하나당 허용할 permission 의 개수
        .limitRefreshPeriod(Duration.ofMillis(5)) // Period 의 크기. 이 Period 동안 limitForPeriod 만큼의 Permission 들이 채워진다.
        .build();

    RateLimiterRegistry registry = RateLimiterRegistry.of(config);

    return registry.rateLimiter("jsonPlaceholder");
  }
}

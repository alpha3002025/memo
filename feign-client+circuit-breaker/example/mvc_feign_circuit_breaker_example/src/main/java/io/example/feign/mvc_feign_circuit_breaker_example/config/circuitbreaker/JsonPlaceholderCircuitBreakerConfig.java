package io.example.feign.mvc_feign_circuit_breaker_example.config.circuitbreaker;


import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeoutException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonPlaceholderCircuitBreakerConfig {

  @Bean(name = {"jsonPlaceholderCircuitBreaker"})
  public CircuitBreaker jsonPlaceholderCircuitBreaker(){
    final CircuitBreakerConfig config = CircuitBreakerConfig.custom()
        .failureRateThreshold(50)
        .slowCallRateThreshold(50)
        .waitDurationInOpenState(Duration.ofMillis(1000))
        .slowCallDurationThreshold(Duration.ofSeconds(2))
        .permittedNumberOfCallsInHalfOpenState(3)
        .minimumNumberOfCalls(10)
        .slidingWindowType(SlidingWindowType.TIME_BASED)
        .slidingWindowSize(5)
//        .recordException(e -> INTERNAL_SERVER_ERROR
//            .equals(getResponse().getStatus()))
        .recordExceptions(IOException.class, TimeoutException.class)
//        .ignoreExceptions(BusinessException.class, OtherBusinessException.class)
        .build();

    CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(config);

    return registry.circuitBreaker("jsonplaceholder");
  }
}

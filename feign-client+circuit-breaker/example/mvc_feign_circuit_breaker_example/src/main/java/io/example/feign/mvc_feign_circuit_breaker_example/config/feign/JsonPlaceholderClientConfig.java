package io.example.feign.mvc_feign_circuit_breaker_example.config.feign;

import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import io.example.feign.mvc_feign_circuit_breaker_example.external.JsonPlaceholderClient;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.feign.FeignDecorators;
import io.github.resilience4j.feign.Resilience4jFeign;
import io.github.resilience4j.ratelimiter.RateLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonPlaceholderClientConfig {
  @Bean
  public JsonPlaceholderClient jsonPlaceholderClient(
      @Qualifier("jsonPlaceholderCircuitBreaker") CircuitBreaker jsonPlaceholderCircuitBreaker,
      @Qualifier("jsonPlaceholderRateLimiter") RateLimiter jsonPlaceholderRateLimiter
  ){
    FeignDecorators feignDecorators = FeignDecorators.builder()
        .withCircuitBreaker(jsonPlaceholderCircuitBreaker)
        .withRateLimiter(jsonPlaceholderRateLimiter)
        .build();

    return Resilience4jFeign.builder(feignDecorators)
        .decoder(new GsonDecoder())
        .encoder(new GsonEncoder())
        .target(JsonPlaceholderClient.class, "https://jsonplaceholder.typicode.com/");
  }
}

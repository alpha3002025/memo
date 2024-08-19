package io.example.feign.mvc_feign_example.config.feign;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import io.example.feign.mvc_feign_example.external.JsonPlaceholderClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JsonPlaceholderClientBean {
  @Bean
  public JsonPlaceholderClient jsonPlaceholderClient(){
    return Feign.builder()
        .decoder(new GsonDecoder())
        .encoder(new GsonEncoder())
        .target(JsonPlaceholderClient.class, "https://jsonplaceholder.typicode.com/");
  }
}

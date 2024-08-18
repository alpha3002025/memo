package io.chagchagchag.example.simple_router_example.gateway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
  @Bean
  public OpenAPI openAPI(
      @Value("${springdoc.version}") String version,
      @Value("${spring.application.name}") String appName
  ){
    Info info = new Info()
        .title("SpringDoc " + appName)
        .version(version)
        .description(appName + "API Documentation")
        .contact(
            new Contact()
                .name("chagchagchag")
                .email("chagchagchag.dev@gmail.com")
        );

    return new OpenAPI().info(info).components(new Components());
  }
}

package io.chagchagchag.example.rewrite_path_router_example.gateway.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "security.jwt")
public class SecurityJwtProperties {
  private final String key;
  @ConstructorBinding
  public SecurityJwtProperties(String key){
    this.key = key;
  }
}

package io.chagchagchag.example.websocket.config.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties(prefix = "server")
public class ServerProperties {
  private final Integer port;

  @ConstructorBinding
  public ServerProperties(
      Integer port
  ){
    this.port = port;
  }
}

package io.chagchagchag.example.websocket.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Value("${broker.relay.host}")
  private String brokerRelayHost;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/websocket")
        .setAllowedOrigins("*");
    registry.addEndpoint("/sockjs")
        .setAllowedOrigins("*")
        .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
//    registry.enableStompBrokerRelay("/queue", "topic")
//        .setRelayHost(brokerRelayHost);
    registry.setApplicationDestinationPrefixes("/app");
  }
}

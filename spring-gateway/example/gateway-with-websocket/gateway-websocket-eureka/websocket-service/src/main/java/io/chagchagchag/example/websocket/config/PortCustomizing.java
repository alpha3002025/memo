package io.chagchagchag.example.websocket.config;

import java.util.Random;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

//@Component
public class PortCustomizing implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

  @Override
  public void customize(ConfigurableServletWebServerFactory factory) {
    Random random = new Random();
    var port = random.ints(9000, 9200)
        .findFirst()
        .getAsInt();

    factory.setPort(port);
  }
}

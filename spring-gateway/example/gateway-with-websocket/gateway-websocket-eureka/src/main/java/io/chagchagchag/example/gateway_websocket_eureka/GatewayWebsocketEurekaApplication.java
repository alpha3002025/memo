package io.chagchagchag.example.gateway_websocket_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class GatewayWebsocketEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayWebsocketEurekaApplication.class, args);
	}

}

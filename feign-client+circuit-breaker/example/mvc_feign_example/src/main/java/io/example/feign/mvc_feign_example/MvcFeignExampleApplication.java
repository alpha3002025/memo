package io.example.feign.mvc_feign_example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MvcFeignExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MvcFeignExampleApplication.class, args);
	}

}

package io.chagchagchag.jsonplaceholder.api_client

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JsonPlaceholderApiApplication

fun main(args: Array<String>) {
	runApplication<JsonPlaceholderApiApplication>(*args)
}

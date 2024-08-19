package io.chagchagchag.jsonplaceholder.api_client.jsonplaceholder.rest

import io.chagchagchag.jsonplaceholder.api_client.jsonplaceholder.client.PostClient
import io.chagchagchag.jsonplaceholder.api_client.jsonplaceholder.dto.Post
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class JsonPlaceholderController (
    val postClient: PostClient
){
  @GetMapping(value = ["/posts/{id}"])
  fun getPostsById(@PathVariable(value = "id") id: Int): Mono<Post> {
    return postClient.getPostById(id)
  }
}
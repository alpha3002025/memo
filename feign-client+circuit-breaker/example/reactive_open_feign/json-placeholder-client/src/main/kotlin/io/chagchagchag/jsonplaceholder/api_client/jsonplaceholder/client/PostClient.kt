package io.chagchagchag.jsonplaceholder.api_client.jsonplaceholder.client

import feign.Param
import feign.RequestLine
import io.chagchagchag.jsonplaceholder.api_client.jsonplaceholder.dto.Post
import reactor.core.publisher.Mono

interface PostClient {
  @RequestLine("GET /posts/{id}")
  fun getPostById(@Param("id") id: Int): Mono<Post>
}
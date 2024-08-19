package io.chagchagchag.jsonplaceholder.api_client.config.feign_clients

import feign.Logger.Level
import feign.RequestInterceptor
import feign.codec.ErrorDecoder
import feign.gson.GsonDecoder
import feign.reactive.ReactorDecoder
import feign.reactive.ReactorFeign
import io.chagchagchag.jsonplaceholder.api_client.openfeign.CustomErrorDecoder
import io.chagchagchag.jsonplaceholder.api_client.jsonplaceholder.client.PostClient
import org.apache.http.entity.ContentType
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JsonPlaceholderClientConfig (
        @Value("\${json-placeholder.url}") val url: String
){
  @Bean
  fun postClient(): PostClient {
    return ReactorFeign.builder()
            .logLevel(Level.FULL)
//            .encoder(GsonEncoder()) // 밖으로 보낼 일이 없으므로 주석처리
            .decoder(ReactorDecoder(GsonDecoder()))
            .errorDecoder(errorDecoder())
            .requestInterceptor(requestInterceptor())
            .target(PostClient::class.java, url)
  }

  fun errorDecoder(): ErrorDecoder = CustomErrorDecoder()

  fun requestInterceptor(): RequestInterceptor {
    return RequestInterceptor {
      it.header("Accept", ContentType.APPLICATION_JSON.mimeType)
    }
  }
}
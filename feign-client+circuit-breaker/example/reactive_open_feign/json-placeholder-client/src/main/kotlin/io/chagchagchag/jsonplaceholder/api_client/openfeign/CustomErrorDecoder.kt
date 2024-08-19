package io.chagchagchag.jsonplaceholder.api_client.openfeign

import feign.Response
import feign.codec.ErrorDecoder
import java.lang.Exception


class CustomErrorDecoder : ErrorDecoder{
  override fun decode(methodKey: String?, response: Response?): Exception {
    var status = response?.status()

    return when (status){
      400 -> IllegalArgumentException("Bad Request")
      404 -> IllegalArgumentException("Not Found")
      else -> RuntimeException("Internal Error")
    }
  }
}
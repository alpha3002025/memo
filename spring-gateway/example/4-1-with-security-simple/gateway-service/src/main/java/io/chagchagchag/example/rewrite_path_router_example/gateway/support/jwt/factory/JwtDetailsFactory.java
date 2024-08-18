package io.chagchagchag.example.rewrite_path_router_example.gateway.support.jwt.factory;

import io.chagchagchag.example.rewrite_path_router_example.gateway.support.jwt.JwtDetails;
import java.util.Date;

public class JwtDetailsFactory {
  public static JwtDetails of(String email, Date expiration){
    return new JwtDetails(
        email, expiration
    );
  }
}

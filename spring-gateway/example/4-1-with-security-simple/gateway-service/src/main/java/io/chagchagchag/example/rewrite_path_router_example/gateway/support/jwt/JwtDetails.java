package io.chagchagchag.example.rewrite_path_router_example.gateway.support.jwt;

import java.util.Date;

public record JwtDetails (
    String email,
    Date expiration
){
}

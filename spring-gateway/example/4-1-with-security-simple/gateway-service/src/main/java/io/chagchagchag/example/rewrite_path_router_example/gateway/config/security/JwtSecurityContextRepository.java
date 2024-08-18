package io.chagchagchag.example.rewrite_path_router_example.gateway.config.security;

import io.chagchagchag.example.rewrite_path_router_example.gateway.config.properties.SecurityJwtProperties;
import io.chagchagchag.example.rewrite_path_router_example.gateway.support.jwt.JwtDetails;
import io.chagchagchag.example.rewrite_path_router_example.gateway.support.jwt.JwtSupport;
import java.security.Key;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {
  private final JwtSupport jwtSupport;
  private final ReactiveUserDetailsService userDetailsService;
  private final SecurityJwtProperties properties;

  public JwtSecurityContextRepository(
      JwtSupport jwtSupport,
      ReactiveUserDetailsService userDetailsService,
      SecurityJwtProperties properties
  ){
      this.jwtSupport = jwtSupport;
      this.userDetailsService = userDetailsService;
      this.properties = properties;
  }

  @Override
  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    return Mono.empty();
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange exchange) {
    String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

    if(authHeader == null) return Mono.empty();
    if(!authHeader.startsWith("Bearer ")) return Mono.empty();

    String token = authHeader.substring("Bearer ".length());

    try{
      Key key = jwtSupport.hmacShaKeyFor(properties.getKey());
      JwtDetails jwtDetails = jwtSupport.degenerateToken(key, token);

      if(jwtDetails.email() != null && jwtSupport.checkIfExpired(jwtDetails)){
        return userDetailsService.findByUsername(jwtDetails.email())
            .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()))
            .map(SecurityContextImpl::new);
      }
    }
    catch (Exception e){
      log.error("Authentication Error : ", e);
    }

    return Mono.empty();
  }
}

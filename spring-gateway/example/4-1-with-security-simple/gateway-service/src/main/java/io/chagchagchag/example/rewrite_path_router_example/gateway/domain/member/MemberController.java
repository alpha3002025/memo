package io.chagchagchag.example.rewrite_path_router_example.gateway.domain.member;

import io.chagchagchag.example.rewrite_path_router_example.gateway.config.properties.SecurityJwtProperties;
import io.chagchagchag.example.rewrite_path_router_example.gateway.support.jwt.JwtDetails;
import io.chagchagchag.example.rewrite_path_router_example.gateway.support.jwt.JwtSupport;
import java.security.Key;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
  private final ReactiveAuthenticationManager authenticationManager;
  private final JwtSupport jwtSupport;
  private final SecurityJwtProperties properties;

  @PostMapping("/login")
  public Mono<ResponseEntity<LoginRequest>> login(@RequestBody AuthRequest authRequest){
    String email = authRequest.getEmail();
    String password = authRequest.getPassword();

    Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);

    Key key = jwtSupport.hmacShaKeyFor(properties.getKey());

    return authenticationManager
        .authenticate(authentication)
        .map(auth ->{
          JwtDetails jwtDetails = new JwtDetails(auth.getName(), new Date(1000*60*60*24));
          return ResponseEntity.status(200).body(new LoginRequest(jwtSupport.generateToken(key, jwtDetails)));
        });

  }

  @Getter
  @Builder
  @AllArgsConstructor
  static class AuthRequest{
    String email;
    String password;
  }

  record LoginRequest(
      String token
  ){}

}

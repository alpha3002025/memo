package io.chagchagchag.example.rewrite_path_router_example.gateway.config.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class JwtWebfluxAuthenticationManager implements ReactiveAuthenticationManager {
  private final ReactiveUserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  public JwtWebfluxAuthenticationManager(
      ReactiveUserDetailsService userDetailsService, PasswordEncoder passwordEncoder
  ){
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    return Mono.just(authentication)
        .flatMap(auth -> {
          // 사용자 이름과 비밀번호로 인증 과정 수행
          String username = auth.getName();
          String password = auth.getCredentials().toString();

          return userDetailsService.findByUsername(username)
              .filter(userDetails -> passwordEncoder.matches(password, userDetails.getPassword())) // 비밀번호 검증
              .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()))
              .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid Credentials")));
        });
  }
}

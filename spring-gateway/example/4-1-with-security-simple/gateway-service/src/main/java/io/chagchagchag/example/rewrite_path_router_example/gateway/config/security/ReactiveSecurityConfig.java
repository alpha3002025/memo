package io.chagchagchag.example.rewrite_path_router_example.gateway.config.security;

import io.chagchagchag.example.rewrite_path_router_example.gateway.config.properties.SecurityJwtProperties;
import io.chagchagchag.example.rewrite_path_router_example.gateway.support.jwt.JwtSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;


@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class ReactiveSecurityConfig {
  @Bean
  public SecurityWebFilterChain securityWebFilterChain(
      ServerHttpSecurity http,
      ReactiveUserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder,
      JwtSupport jwtSupport,
      SecurityJwtProperties securityJwtProperties
  ){
    ReactiveAuthenticationManager authenticationManager = new JwtWebfluxAuthenticationManager(userDetailsService, passwordEncoder);
    ServerSecurityContextRepository securityContextRepository = new JwtSecurityContextRepository(jwtSupport, userDetailsService, securityJwtProperties);

    return http
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
        .authorizeExchange(exchange ->
          exchange
              .pathMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
              .pathMatchers("/member/login").permitAll()
              .pathMatchers("/actuator/**").permitAll()
              .anyExchange().authenticated()
        )
        .authenticationManager(authenticationManager)
        .securityContextRepository(securityContextRepository)
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

  @Bean
  public ReactiveUserDetailsService userDetailsService(){
    UserDetails user1 = User.builder().username("aaa@gmail.com").password(passwordEncoder().encode("aaaaa")).build();
    UserDetails user2 = User.builder().username("bbb@gmail.com").password(passwordEncoder().encode("bbbbb")).build();
    UserDetails user3 = User.builder().username("vvv@gmail.com").password(passwordEncoder().encode("vvvvv")).build();
    return new MapReactiveUserDetailsService(user1, user2, user3);
  }
}

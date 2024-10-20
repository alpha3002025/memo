## Webflux Spring Gateway + Spring Security + Jwt

## Spring Security

### UserDetailsService

- id : `aaa@gmail.com` , password: `aaaaa` 
- id : `bbb@gmail.com` , password: `bbbbb`
- id : `vvv@gmail.com` , password: `vvvvv`

예제의 범위를 단순하게 해서 개념파악을 위해 위의 계정들을 하드코딩으로 입력해서 만든 MapReactiveUserDetailsService 를 사용합니다.<br/>

```java
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class ReactiveSecurityConfig {
  	private final ReactiveAuthenticationManager authenticationManager;
  	private final ServerSecurityContextRepository securityContextRepository;
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
        ServerHttpSecurity http
    ){
        return http.
            // ...
            .build();
    }
    
    // ... 
    
    @Bean
  	public ReactiveUserDetailsService userDetailsService(){
    	UserDetails user1 = User.builder().username("aaa@gmail.com").password(passwordEncoder().encode("aaaaa")).build();
    	UserDetails user2 = User.builder().username("bbb@gmail.com").password(passwordEncoder().encode("bbbbb")).build();
    	UserDetails user3 = User.builder().username("vvv@gmail.com").password(passwordEncoder().encode("vvvvv")).build();
    	return new MapReactiveUserDetailsService(user1, user2, user3);
  	}
}
```

<br/>



### PasswordEncoder

```java
@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class ReactiveSecurityConfig {
  	private final ReactiveAuthenticationManager authenticationManager;
  	private final ServerSecurityContextRepository securityContextRepository;
    
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(
        ServerHttpSecurity http
    ){
        return http.
            // ...
            .build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
```

<br/>



### ReactiveAuthenticationManager

AuthenticationManager 에서는 주로 authenticate(Authentication authentication) 메서드를 통해서 인증을 수행합니다. Webflux 에서는 AuthenticationManager 는 ReactiveAuthenticationManager 라고 하는 interface 를 지원합니다.

이 인증 작업을 수행할 때 UserDetailsService 또는 UserDetailsManager 를 사용합니다. 이번 예제에서는 UserDetailsService 를 사용하며 위에서 정의한 MapReactiveUserDetailsService 기반의 UserDetailsService 를 사용합니다.<br/>

> 로그인 외에도 회원가입, 수정 등을 처리하고 싶다면 UserDetailsService 말고도 UserDetailsManager 를 UserDetailsService 의 기능을 모두 사용하면서 CUD 에 해당하는 유용한 메서드들을 인터페이스 기반으로 정의할 수 있습니다.

```java
// ...

@Component
@RequiredArgsConstructor
public class JwtWebfluxAuthenticationManager implements ReactiveAuthenticationManager {
  private final ReactiveUserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
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
```

<br/>

(1) 

- UserDetailsService 를 이용해서 사용자가 존재하는지 조회 후 그 결과를 UserDetails 객체로 리턴합니다.

(2)

- PasswordEncoder 의 matches() 메서드를 통해 Request 로 전달받은 password 와 UserDetailsService 에서의 조회결과로 전달받은 UserDetails 내의 password가 일치하는지 체크합니다.

(3)

- (1) 에서 사용자 조회를 마쳤고, (2) 에서 사용자가 입력한 패스워드가 Database 내의 패스워드와 일치하는 것임이 맞았다면, (3) 에서는 UsernamePasswordAuthenticationToken 객체를 생성해서 리턴합니다.

<br/>



위에서 작성한 JwtWebfluxAuthenticationManager 의 authenticate(Authentication) 메서드는 Authentication 객체를 받아서 인증을 하며, UserDetailsService 를 외부에서 주입받아서 필요한 절차를 수행한다는 사실에 주목해주시기 바랍니다.<br/>



## Gateway






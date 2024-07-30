# AOP 기반 패스워드 암호화하기

이번 문서에서는 AOP 기반 패스워드 암호화를 하는 예제를 살펴봅니다.<br/>

예제 코드

- [github/chagchagchag/spring-security/example/password-encoder-example](https://github.com/chagchagchag/memo/tree/main/spring-security/example/password-encoder-example)

<br/>



## 의존성 추가

```kotlin
dependencies {
	// ...
	implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.apache.commons:commons-lang3")
}
```

reflection 의 경우 Java 의 라이브러리를 그대로 사용해도 되지만, apache commons lang3 를 사용하면 조금 더 재사용성이 좋고 유연한 코드를 작성가능해집니다.<br/>

<br/>



## PasswordEncoder 빈 정의

```java
package io.example.spring_security.password_encoder_example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEncoderConfig {

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
}
```

<br/>



## 커스텀 어노테이션 정의 

```java
@Target(ElementType.FIELD) // (1) 
@Retention(RetentionPolicy.RUNTIME) // (2)
public @interface EncryptPassword {
}
```

(1)

- `@Target(ElementType.FIELD)` : 필드 레벨에 지정되도록 해주었습니다.

(2)

- `@Retention(RetentionPolicy.RUNTIME)` : 런타임에 인식되도록 지정해주었습니다.

<br/>



##  Request 객체에 어노테이션 사용

MemberLoginRequest.java

```java
package io.example.spring_security.password_encoder_example.member.request;

import io.example.spring_security.password_encoder_example.support.encryption.EncryptPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "ofAll")
public class MemberLoginRequest {
  private Long id;

  @EncryptPassword // (1)
  private String password;
}
```

(1) 

- AOP 에 의해 암호화가 처리되기를 원하는 곳에 `@EncryptPassword` 를 지정해주었습니다.

<br/>



## EncryptionUtils.java

```java
package io.example.spring_security.password_encoder_example.support.encryption;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncryptionUtils {
  private final PasswordEncoder passwordEncoder;
  public String encrypt(String password){
    return passwordEncoder.encode(password);
  }
}
```

<br/>



## Aspect 정의

- 여기서부터 설명 추가 예정

```java
package io.example.spring_security.password_encoder_example.support.aspect;

import io.example.spring_security.password_encoder_example.support.encryption.EncryptPassword;
import io.example.spring_security.password_encoder_example.support.encryption.EncryptionUtils;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // (1)
@Component
@RequiredArgsConstructor
public class PasswordEncryptionAspect {
  private final EncryptionUtils encryptionUtils; // (2)

  //(3)
  @Around("@annotation(io.example.spring_security.password_encoder_example.support.encryption.EncryptPassword)")
  public Object encryptFieldAspect(ProceedingJoinPoint pjp) throws Throwable {
    // (4)
    Arrays.stream(pjp.getArgs()).forEach(this::encryptField);
    return pjp.proceed();
  }

  // (5)
  public void encryptField(Object object) {
    if(ObjectUtils.isEmpty(object)) return;

    // (6)
    FieldUtils.getAllFieldsList(object.getClass())
        .stream()
        // (7)
        .filter(field -> !(Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())))
        // (8)
        .filter(field -> field.isAnnotationPresent(EncryptPassword.class))
        .forEach(field -> {
          try{
            // (9)
            Object encryptionField = FieldUtils.readField(field, object, true);
            if(!(encryptionField instanceof String)) return;

            // (10)
            String encrypted = encryptionUtils.encrypt((String) encryptionField);
            // (11)
            FieldUtils.writeField(field, object, encrypted);
          }
          catch(Exception e){
            throw new RuntimeException(e);
          }
        });
  }


}
```

<br/>

(1)

- `@Aspect` 로 선언했습니다. 

(2)

- Aspect 내에서 위에서 정의한 EncryptionUtils 를 의존성 주입했습니다.

(3)

- `@Around` 로 지정했으며, Around 라는 것은 Advice 를 의미합니다. Advice 에는 @Around, @Before, @After 이 있으며 어느 시점에 주입받을지를 의미합니다.
- Around는 어느 시점에 실행할지를 의미하며 Preecedding
- AOP 적용할 대상은 point cut 으로 지정가능한데, 이번 예제에서는 `@annotation` 방식을 선택했습니다. 
- AOP 적용할 대상은 bean, annotation, execution 등으로 지정가능합니다.
- ProceedingJoinPoint : Advice 가 적용된 메서드 내의 파라미터에서 ProceedingJoinPoint 를 주입받습니다.

(4)

- ProceedingJoinPoint 를 통해 얻은 인자값들의 처리를 (5) 에 따로 정의해둔 함수에 넘겨줍니다.

(5)

- 단위 테스트가 가능하도록 별도의 함수로 분리해두었습니다.

(6)

- 인자값으로 전달받은 모든 인자들을 순회합니다.

(7)

- final, static 등으로 선언된 모든 필드들은 skip 하도록 지정했습니다.

(8)

- `@EncryptPassword` 가 지정된 필드인지를 필터링합니다.

(9) : `Object encryptionField = FieldUtils.readField(field, object, true);`

- reflection 을 통해 어노테이션이 적용된 필드에 저장되어 있는 값을 읽어들입니다.

(10) : `String encrypted = encryptionUtils.encrypt((String) encryptionField);`

- 암호화를 수행합니다.

(11) : `FieldUtils.writeField(field, object, encrypted);`

- reflection 을 통해 Target 필드의 값을 암호화된 값으로 새로 씁니다.

<br/>



## 테스트 코드

```java
package io.example.spring_security.password_encoder_example.support.aspect;

import static org.mockito.ArgumentMatchers.any;

import io.example.spring_security.password_encoder_example.member.request.MemberLoginRequest;
import io.example.spring_security.password_encoder_example.member.request.factory.MemberLoginRequestFactory;
import io.example.spring_security.password_encoder_example.support.encryption.EncryptionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordEncryptionAspectTest {
  PasswordEncryptionAspect aspect;

  @Mock
  EncryptionUtils encryptionUtils;

  @BeforeEach
  public void setup(){
    aspect = new PasswordEncryptionAspect(encryptionUtils);
  }

  @DisplayName("AOP_PASSWORD_ENCODER_TEST")
  @Test
  public void TEST_AOP_PASSWORD_ENCODER_TEST(){
    // given
    MemberLoginRequest request = MemberLoginRequestFactory.of(2L, "aaaaaaa");
    Mockito.when(encryptionUtils.encrypt(any()))
        .thenReturn("암호화문자열");

    // when
    aspect.encryptField(request);

    // then
    Assertions.assertEquals("암호화문자열", request.getPassword());

  }

}
```

<br/>






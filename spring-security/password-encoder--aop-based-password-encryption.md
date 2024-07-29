# AOP 기반 패스워드 암호화하기

## 의존성 추가

```kotlin
dependencies{
    // ...
    implementation("org.springframework.boot:spring-boot-starter-aop")
	implementation("org.apache.commons:commons-lang3")
}
```

reflection 의 경우 Java 의 라이브러리를 그대로 사용해도 되지만, apache commons lang3 를 사용하면 조금 더 재사용성이 좋고 유연한 코드를 작성가능해집니다.<br/>



## 커스텀 어노테이션 정의 

```java
@Target(ElementType.FIELD) // (1) 
@Retention(RetentionPolicy.RUNTIME) // (2)
public @interface PasswordEncrypt {
}
```

(1)

- `@Target(ElementType.FIELD)` : 필드 레벨에 지정되도록 해주었습니다.

(2)

- `@Retention(RetentionPolicy.RUNTIME)` : 런타임에 인식되도록 지정해주었습니다.

<br/>



## 어노테이션 지정

```java
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheckRequest{
    private Long id;
    
    @PasswordEncrypt // (1)
    private String password
}
```

(1) 

- AOP 에 의해 암호화가 처리되기를 원하는 곳에 `@PasswordEncrypt` 를 지정해주었습니다.

<br/>



## Aspect 정의

- 여기서부터 설명 추가 예정

```java
@Aspect
@Component
@RequiredArgsConstructor
public class PasswordEncryptAspect {
    private fianl EncryptUtils encryptUtils; // @Component 로 정의한 암호화 컴포넌트
    
    @Around("execution(* io.chagchagchag.example.controller..*.*(..))")
    public Object passwordEncrypt(ProceedingJoinPoint pjp) throws Throwable {
        Arrays.stream(pjp.getArgs()).forEach(this::encryptField)
        return pjp.proceed();
    }
    
    public void encryptField(Object object){
        if(ObjectUtils.isEmpty(object)) return;
        
        FieldUtils.getAllFieldList(object.getClass())
            .stream()
            .filter(field -> !(Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())))
            .filter(field -> field.isAnnotationPresent(PasswordEncrypt.class))
            .forEach(field -> {
                try{
                    Object encryptionField = FieldUtils.readField(field, object, true);
                    if(!(encryptionField instanceof String)) return;
                    
                    String encrypted = encryptUtils.encrypt((String) encryptionField);
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



## 테스트 코드

```java
@ExtendWith(MockitoExtension.class)
public class PasswordEncryptAspectTest {
    PasswordEncryptAspect aspect;
    
    @Mock
    EncryptUtils encryptUtils;
    
    @BeforeEach
    public void setup(){
        aspect = new PasswordEncryptAspect(encryptUtils);
    }
    
    @Test
    public void test(){
        // given
        HealthCheckRequest request = new HealthCheckRequest("id", "abcdefghijk");
        when(encryptUtils.encrypt(any())).thenReturn("encrypted");
        
        // when
        aspect.encryptField(request);
        
        // then
        assertThat(request.getPassword())
            .isEqualTo("encrypted");
    }
}
```

<br/>






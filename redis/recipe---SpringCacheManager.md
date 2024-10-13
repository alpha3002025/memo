# SpringCacheManager

Cacheable, CachePut, CacheEvict 는 Cache Aside 기반의 캐싱전략에 대해 Spring 의 Cache Abstraction 에서 제공하는  캐싱방식인데, spring-starter-cache 에서 이 것에 대한 기능들을 제공하고 있습니다. 자세한 내용은 생략하며, 이번 문서의 글은 편의상... 독백체로 작성합니다요...<br/>

<br/>



# 참고자료

- https://conanmoon.medium.com/%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D-%EC%9D%BC%EA%B8%B0-spring%EC%97%90%EC%84%9C%EC%9D%98-%EC%BA%90%EC%8B%B1-caching-9221631b4051

<br/>



# redis, mysql 설치

개발PC가 지저분해지는 것을 싫어해서 docker 를 자주 사용하는데, 그래서 다음의 redis, mysql 에 대한 docker-compose 를 차례로 실행시켜줬다.<br/>

**0.local-infra/local-redis/docker-compose.yml**

```yaml
version: '3.8'
services:
  redis:
    image: redis:7.0
    platform: linux/amd64
    command: redis-server --port 6379
    hostname: redis-localhost
    container_name: local-redis
    labels:
      - "name=redis"
      - "mode=standalone"
    ports:
      - "26379:6379"
    depends_on:
      - redis-commander
    links:
      - redis-commander
  redis-commander:
    image: rediscommander/redis-commander:latest
    platform: linux/amd64
    hostname: redis-commander-localhost
    restart: always
    environment:
      - REDIS_HOSTS=redis-localhost
    ports:
      - "38081:8081"
```

<br/>



**0.local-infra/local-mysql/docker-compose.yml**

```yaml
version: '3.8'
services:
  example-mysql:
    image: mysql:5.7.39-debian
    platform: linux/amd64
    restart: always
    container_name: example-mysql
    hostname: example-mysql
    ports:
      - "23306:3306"
    environment:
      - MYSQL_USER=user
      - MYSQL_USER_HOST=%
      - MYSQL_PASSWORD=test1357
      - MYSQL_DATABASE=example
      - MYSQL_ROOT_HOST=%
      - MYSQL_ROOT_PASSWORD=test1357
      - TZ=UTC
    command:
      - --character-set-server=utf8mb4
      - --collation-server=utf8mb4_unicode_ci
    volumes:
      - ./init/:/docker-entrypoint-initdb.d/
```

<br/>



# dto

BookCache.java

```java
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookCache implements Serializable {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

<br/>



# entity

```java
// ...
@Entity
@EntityListeners(value = AuditingEntityListener.class)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
```

<br/>



# 설정

spring.data

- redis

spring.datasource 

- (mysql 등 database 를 사용할 경우에만)

- url, user, password, driver-class

spring.jpa

- hibernate

<br/>



# `@Enable-` 설정

일반적으로 클래스로 따로 분리하는 경우도 있는데, 내 경우는 그냥 예제이기도 하고, 애플리케이션 진입점에 따로 모아둬도 나쁘지 않겠다 싶어서 애플리케이션 메인에 정의했다.<br/>

`@Enable-` 은 두가지 설정에 대해 `@Enable` 해주었다.

- `@EnableJpaAuditing`
- `@EnableCaching`

코드는 다음과 같다.

```java
// ... 
@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class SpringDataRedisExample implements ApplicationRunner {
	// ... 
	
  public static void main(String[] args) {
		SpringApplication.run(SpringDataRedisExample.class, args);
	}
  
  // ...
}
```

<br/>



# ObjectMapper 설정

ObjectMapper 는 두가지를 준비했는데, 그 중 이번 문서에서 사용하는 ObjectMapper 는 아래의 예제에서 `polymorphicObjectMapper` 다. <br/>

일반적으로 다음과 같이 RedisTemplate 의 종류가 많아지면, 관리가 쉽지 않아진다.

- RedisTemplate\<String, BookCache\> bookCacheRedisTemplate
- RedisTemplate\<String, BookRankingCache\> bookRankingCacheRedisTemplate
- ... 그 외 다수

이때 Value 에 대해 들어오는 값을 Object 로 Serialize/Deserialize 하도록 지정하면 어떤 객체든 Object 타입으로 Serializing, Deserializing 을 하면 되기에 개별 타입에 대한 로직이 생산되는 것으로 인한 피로도가 줄어든다.<br/>

Object 를 Value 로 하는 ObjectMapper 를 메인으로 사용할 것이기에 이 ObjectMapper 를 `@Primary` 로 설정해야 해줬다. (만약 이 설정을 안하면, `@Primary` 로 우선순위 하나 정도는 지정해달라고 에러메시지가 뜬다.) <br/>

이렇게 해서 아래 코드를 요약해보면 다음과 같다.

- PolyMorphicTypeValidator 가 적용, Object 타입에 대해 Serializing/Deserializing 이 되도록 부가적인 설정
- Java Time 관련 Serializing/Deserializing 설정
- `@Primary` 설정

```java
// ...
@Configuration
public class ObjectMapperConfig {
    @Primary
    @Bean
    public ObjectMapper polymorphicObjectMapper(){
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator
                .builder()
                .allowIfSubType(Object.class)
                .build();

        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule())
                .activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL)
                .disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
    }

    @Bean
    public ObjectMapper bookObjectMapper(){
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS);
    }
}
```

<br/>



# Redis 설정

RedisTemplate 을 설정하는 코드다. RedisTemplate 에서는 ObjectMapper 를 위에서 선언했던 `polymorphicObjectMapper` 을 사용하도록 지정해줬다.

```java
// ...
@Configuration
public class RedisConfig {
    @Primary
    @Bean
    RedisTemplate<String, Object> polymorphicRedisTemplate(
            RedisConnectionFactory redisConnectionFactory,
            @Qualifier("polymorphicObjectMapper") ObjectMapper polymorphicObjectMapper
    ){
        RedisTemplate<String, Object> polymorphicRedisTemplate = new RedisTemplate<>();
        polymorphicRedisTemplate.setConnectionFactory(redisConnectionFactory);
        polymorphicRedisTemplate.setKeySerializer(new StringRedisSerializer());
        polymorphicRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(polymorphicObjectMapper));
        return polymorphicRedisTemplate;
    }

  // ...
}
```

<br/>



# CacheManager 설정

먼저 아래의 의존성을 추가해주자. 나는 개인적으로 gradle dsl 보다 kotlin dsl 을 선호해서 build.gradle.kts 기반으로 자바 프로젝트를 구성했고 그 의존성은 다음과 같다.<br/>

**build.gradle.kts**<br/>

```kotlin
// ...

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-cache")
}

// ...
```

<br/>



**CacheManagerConfig.java**<br/>

> 개인적으로 파이썬이 더 좋아지고 있는 시점에서... 자바는 괄호 너무 많다. 너무 심하다.

`@Cacheable`, `@CachePut`, `@CacheEvict` 등을 사용하려면 RedisCacheManagerBuilderCustomizer 타입의 Bean 을 설정해야 한다.<br/>

이름이 너무 길고 카멜케이스라 스트레스를 받을 수 있는데 외우기 쉽게 네이밍 컨벤션을 다음과 같이 연상하면 된다.

- `Redis-` : Redis 에 대한
- `CacheManagerBuilder` : `CacheManager` 기능에 대한 Builder 
  - 우리는 지금 CacheManager 설정을 하고있다. 잊지 말자. 이 "CacheManager 에 대한 Builder" 라는 점을 강조해서 기억하자.
- `Customizer` : 앞의 설정을 커스터마이징하는 interface
  - 요즘 `-Customizer` 라는 네이밍 컨벤션으로 스프링의 설정들이 많이 생겨났다.
  - 이 `-Customizer` 는 함수형 인터페이스로 람다 처럼 사용할 수 있다.

```java
// ...
@Configuration
public class CacheManagerConfig {
    @Getter
    @AllArgsConstructor
    public static class CacheProperty{
        String configAlias;
        Integer ttl;
    }

    private final List<CacheProperty> cacheProperties = List.of(
            new CacheProperty("cache1", 30),
            new CacheProperty("cache2", 42)
    );

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer(
            @Qualifier("polymorphicObjectMapper") ObjectMapper polymorphicObjectMapper
    ) {
        return builder -> {
            cacheProperties.forEach(prop -> {
                RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                        .disableCachingNullValues()
                        .serializeKeysWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new StringRedisSerializer()
                                )
                        )
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair.fromSerializer(
                                        new GenericJackson2JsonRedisSerializer(polymorphicObjectMapper)
                                )
                        )
                        .entryTtl(Duration.ofSeconds(prop.getTtl()));

                builder.withCacheConfiguration(prop.getConfigAlias(), cacheConfig);
            });
        };
    }
}
```

<br/>



> `@Cacheable`, `@CachePut`, `@CacheEvict` 에 대한 기본적인 내용에 대해서는 이 문서의 하단부의 "etc" 라는 챕터에 따로 정리해둔다.

<br/>



# BookRepository

```java
// ... 
public interface BookRepository extends JpaRepository<Book, Long> {
}
```

<br/>



# BookService

캐시에 `key` 에 대해 값이 존재하지 않으면 `BookCache findBookById(Long id)` 함수를 실행한 결과 값을 캐시에 저장한다. Entity 를 바로 Redis 에 저장하지 않고 Redis 계층에서는 `BookCache` 라고 하는 별도의 자료형을 사용하도록 지정해줬다.<br/>

자세한 설명은 생략<br/>

```java
// ...
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Cacheable(cacheNames = {"book-cache"}, key = "'book:' + #id")
    public BookCache findBookById(Long id) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책 입니다."));

        return BookCache.builder()
                .id(book.getId())
                .title(book.getTitle())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }
}
```

<br/>



# BookController

간단한 예제다.

```java
// ...
@RequiredArgsConstructor
@RestController
public class BookController{
    private final BookService bookService;

    @GetMapping("/books/{id}")
    public BookCache getBook(@PathVariable Long id) {
        return bookService.findBookById(id);
    }
}
```

<br/>



# 샘플데이터 insert

메인 애플리케이션 클래스에 다음과 같은 코드를 추가해줬다.

```java
// ... 
@RequiredArgsConstructor
@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class SpringDataRedisExample implements ApplicationRunner {
	private final RedisTemplate<String, BookRankingCache> bookRankingCacheRedisTemplate;
	private final BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringDataRedisExample.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		generateBookRankingCache();
		generateBookData();
	}

	public void generateBookRankingCache(){
		for(int i=0; i<100; i++){
			BookRankingCache bookRankingCache = BookRankingCache.builder()
					.id(Long.parseLong(String.valueOf(i)))
					.ranking(Long.parseLong(String.valueOf(i)))
					.build();

			bookRankingCacheRedisTemplate.opsForZSet()
					.add("ranking:book", bookRankingCache, bookRankingCache.getRanking());
		}
	}

	@Transactional
	public void generateBookData(){
		List<Book> books = new ArrayList<>();
		for(int i=0; i<100; i++){
			Book book = Book.builder()
					.id(Long.parseLong(String.valueOf(i)))
					.title("Book#" + i)
					.build();
			books.add(book);
		}
		bookRepository.saveAll(books);
	}
}
```

<br/>



# 테스트

먼저 터미널에서 docker 에 접속해서 redis-cli 를 실행한다.

```bash
$ docker container exec -it local-redis redis-cli monitor
```

<br/>

브라우저에서 http://localhost:8080/books/1 로 접속해본다.<br/>

<br/>

이렇게 하면 처음 브라우저에서 `/books/1` 로 접속할 때에는 GET, SET 을 모두 수행하지만, 그 다음의 요청부터는 GET 만을 수행한다. 이것은 ttl 로 지정한 기간 만큼만 적용된다.<br/>

<br/>



# 성능테스트

[locust](https://github.com/locustio/locust) 또는 [vegeta](https://github.com/tsenart/vegeta) 를 사용할 예정<br/>



# Cacheable, CachePut, CacheEvict

정리 예정




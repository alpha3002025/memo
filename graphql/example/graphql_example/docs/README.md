application.yml
```yaml
spring:
  h2:
    console:
      enabled: true

  graphql:
    graphiql:
      enabled: true
      printer:
        enabled: true

  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:~/test
    username: sa
    password:

  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
```

- `graphql.graphiql.enable = true`
  - `localhost:8080/graphiql` 에 접속해서 graphql 쿼리 테스트가 가능하도록 해주는 기능
    - 운영 프로필에서는 가급적 꺼두는 것이 좋음
  - `localhost:8080/h2-console` 과 유사한 기능
- `graphql.graphiql.printer.enable = true`
  - graphql 을 출력해주는 기능
  - jpa 의 show-sql 과 유사한 기능

<br/>

# Controller 코드
```java
package io.demo.graphql.graphql_example.food.rest;

import io.demo.graphql.graphql_example.food.application.FoodService;
import io.demo.graphql.graphql_example.food.entity.FoodEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class FoodController {

    private final FoodService foodService;

    @MutationMapping
    public FoodEntity save(@Argument String name){
        return foodService.save(name);
    }

    @QueryMapping
    public FoodEntity getFood(@Argument String name){
        return foodService.getFood(name);
    }

    /**
     * @QueryMapping 은 @GetMapping 과 같은 역할을 수행
     * @return
     */
    @QueryMapping
    public List<FoodEntity> getFoods(){
        return foodService.getFoods();
    }
}
```

## @QueryMapping == @GetMapping
`@QueryMapping` 은 `@GetMapping` 과 같은 역할을 합니다.

## @MutationMapping == @PostMapping
`@MutationMapping` 은 `@PostMapping` 과 같은 역할을 합니다.
- graphql 은 endpoint 와 하나로 되어 있기에 @MutationMapping 어노테이션만 지정해주고 다른 설정은 필요없습니다. ㄷㄷㄷ

## schema.graphqls 생성
- `.graphql` 이 아닌 `.graphqls` 파일로 생성해야 합니다.
```graphql

```


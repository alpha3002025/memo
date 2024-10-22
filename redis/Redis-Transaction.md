Redis 에도 Transaction 개념이 존재합니다. <br/>

All or Nothing 전략을 통해 특정 단위의 로직 실행시 내부의 하나의 로직이 실패시 같은 단위 내의 로직이 함께 실패하도록 하는 원자성 개념을 지원하며, 트랜잭션이 순차적으로 실행될 수 있도록 순차성을 지원하기 위해 내부적으로 트랜잭션 요청을 Queue 에 쌓아두고 처리하고 있습니다.<br/>

<br/>



# Redis 의 Transaction

> 참고: 
>
> - [(redis) Transaction - 이론](https://sabarada.tistory.com/177)
> - [Spring Data Redis - Redis Template 트랜잭션](https://devel-repository.tistory.com/85)

<br/>

Redis 에서는 다음의 명령을 통해서 Transaction 을 보장합니다.<br/>

MULTI

- Redis 트랜잭션을 시작하는 커맨드
- 트랜잭션이 시작될 때 그 이후의 커맨드는 바로 실행되지 않고 Queue 에 쌓입니다.

EXEC

- 정상적으로 처리되어 Queue에 쌓여있는 명령어를 일괄적으로 실행합니다. 
- RDBMS의 Commit과 동일합니다.

DISCARD

- Queue 에 쌓여잇는 명령어를 폐기합니다.
- RDBMS의 Rollback 개념과 유사한 개념입니다.

WATCH

- Lock 을 담당하는 명령어입니다. 낙관적 락(Optimistic Lock) 기반으로 지원됩니다.
- Watch 명령어를 사용할 때 이 후 Unwatch 가 호출되기 전 까지는 Exec 또는 다른 커맨드만 허용됩니다.



# Redis 의 Transaction CLI

> todo

<br/>



# Spring Data Redis 

build.gradle.kts

```kotlin
implements("org.springframework.boot:spring-boot-starter-data-redis")
```

<br/>



application.yml

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
```

<br/>



# Spring Data Redis 에서 `@Transactional` 지원여부

Spring Data Redis 는 PlatformTransactionManager 의 구현체를 지원하고 있지 않습니다. 따라서 RDBMS 를 쓰고 있는 경우 RDBMS 의 TransactionManager 에 기생하도록 하는 등의 별도의 설정이 필요한데 여기에 대해서는 [Spring Data Redis를 이용한 Transaction 처리](https://sabarada.tistory.com/178) 문서를 참고해주시기 바립니다.<br/>

<br/>



# RedisTemplate

RedisTemplate 은 transaction 을 유지하기 위해 동일 접속 세션을 계속 유지해야 합니다. 그런데 일반적인 RedisTemplate 에서는 그렇지 않기에 SessionCallback 을 사용합니다.<br/>

e.g.

```java
List<Object> txResults = stringRedisTemplate.execute(new SessionCallback<List<Object>>() {
    public List<Object> execute(RedisOperations operations) throws DataAccessException {
        operations.multi(); // redis transaction 시작

        operations.opsForValue().set("A", "1");
        operations.opsForValue().set("B", "2");

        return operations.exec(); // redis transaction 종료
    }
});

System.out.println("return values of items : " + Arrays.toString(txResults.toArray()));
```

<br/>



e.g. 실패하는 코드 실행시

```java
List<Object> txResults = stringRedisTemplate.execute(new SessionCallback<List<Object>>() {
    public List<Object> execute(RedisOperations operations) throws DataAccessException {
        operations.multi(); // redis transaction 시작

        operations.opsForValue().set("A", "1");
        operations.opsForValue().set("B", "2");
      
      	throw new IllegalArgumentException("예외 발생");

        return operations.exec(); // redis transaction 종료
    }
});

System.out.println("return values of items : " + Arrays.toString(txResults.toArray()));
```

위의 코드를 실행해보면, A=1, B=2 데이터가 저장되지 않았음을 확인 가능합니다.<br/>

<br/>



# executePipeline

> 참고 : [한번에 여러 명령어 호출시 파이프라인 및 트랜잭션]( [Redis-Transaction.md](Redis-Transaction.md) )
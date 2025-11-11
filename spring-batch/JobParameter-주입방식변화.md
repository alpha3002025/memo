옛날
- 과거에는 Java 8 Time API를 지원하지 않아 String으로 파싱해서 사용해야 했음
- 많은 블로그가 아직도 옛날 방식(String 파싱)을 소개하고 있음

<br/>

그.. 조졸두님이 전해줬던 방식이 아직까지도 많이 사용되고 있는... 아니 뭐.. 그런것 같다. 이미 작성된 레거시들이 대부분 옛날 버전이라 batch 버전을 안올리는 회사들도 많을거고... 뭐 옛날 방식이 잘못됐다는 건 아닌데.. 나중에 새로 프로젝트를 한다거나 하면 이번 내용을 누구든 알아야 할수 있기에 정리 시작

<br/>






# 참고자료
- https://zorba91.tistory.com/356
- Claude 

<br/>


# 해결책
Spring Batch 5.0.0부터 (2022년 10월~) Java 8 Time API를 정식 지원
@DateTimeFormat 어노테이션으로 패턴 지정 가능
기본 패턴을 따르면 어노테이션 없이도 자동 변환됨

- LocalDateTime: yyyy-MM-dd'T'HH:mm
- LocalDate: yyyy-MM-dd
- LocalTime: HH:mm

# 주의사항
띄어쓰기 금지: JobParameter 값에 공백이 있으면 인식 안 됨
- ❌ 2023-09-02 09:00
- ✅ 2023-09-0209:00

Nullable 처리: 항상 파라미터를 입력하지 않는다면 nullable로 선언해야 함

# 참고
- 아래 예제는 클로드를 통해 생성했고 2025/11/11 에 작성한 버전이다. 
- 2026년도 이후로 새로운 버전 및 변경사항이 생길 경우 업데이트는 안할 예정이다. (시간이 난다면 할수도...)
- 요즘 클로드는 스프링 공식문서에 따라서 제안해줘서 나름 Best Practice 에 부합하게 제안해주기도 하고, 질문(프롬프트)시에 `Best Practice 에 부합하게` 라는 문구 등을 추가해주면 조금 더 전문적이고 세련된 제안을 해주는 것 같다.

<br/>




# e.g. (1) DefaultDateTimeJobConfiguration.java
DefaultDateTimeJobConfiguration.java
```java
package com.example.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 파라미터가 없을 때 현재 날짜/시간을 기본값으로 사용하는 배치 설정
 * 
 * 3가지 방법 제공:
 * 1. Elvis 연산자 (?:) 사용
 * 2. Nullable + Java 코드에서 기본값 처리
 * 3. JobParametersIncrementer에서 기본값 주입
 */
@Configuration
public class DefaultDateTimeJobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public DefaultDateTimeJobConfiguration(JobRepository jobRepository,
                                           PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job defaultDateTimeJob(Step defaultDateTimeStep) {
        return new JobBuilder("defaultDateTimeJob", jobRepository)
                .incrementer(new DefaultDateTimeIncrementer()) // 기본값 주입
                .start(defaultDateTimeStep)
                .build();
    }

    @Bean
    @JobScope
    public Step defaultDateTimeStep(
            ItemReader<DateTimeRecord> dateTimeReader,
            ItemWriter<DateTimeRecord> dateTimeWriter) {

        return new StepBuilder("defaultDateTimeStep", jobRepository)
                .<DateTimeRecord, DateTimeRecord>chunk(10, transactionManager)
                .reader(dateTimeReader)
                .writer(dateTimeWriter)
                .build();
    }

    /**
     * 방법 1: Elvis 연산자(?:)로 기본값 설정 (가장 간단)
     * 
     * 파라미터가 없으면 SpEL로 현재 시간 생성
     */
    @Bean
    @StepScope
    public ItemReader<DateTimeRecord> dateTimeReader(
            // 방법 1-1: Elvis 연산자로 현재 시간 설정
            @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
            @Value("#{jobParameters['startDateTime'] ?: T(java.time.LocalDateTime).now()}")
            LocalDateTime startDateTime,

            // 방법 1-2: Elvis 연산자로 현재 날짜 설정
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @Value("#{jobParameters['targetDate'] ?: T(java.time.LocalDate).now()}")
            LocalDate targetDate,

            // 방법 1-3: Elvis 연산자로 현재 시간(시분초) 설정
            @DateTimeFormat(pattern = "HH:mm:ss")
            @Value("#{jobParameters['targetTime'] ?: T(java.time.LocalTime).now()}")
            LocalTime targetTime,

            // 방법 1-4: 특정 시간으로 기본값 설정 (예: 오늘 자정)
            @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
            @Value("#{jobParameters['endDateTime'] ?: T(java.time.LocalDate).now().atStartOfDay()}")
            LocalDateTime endDateTime) {

        System.out.println("=".repeat(80));
        System.out.println("📅 Default DateTime Parameters:");
        System.out.println("-".repeat(80));
        System.out.println("  Start DateTime : " + 
            startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("  End DateTime   : " + 
            endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("  Target Date    : " + 
            targetDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("  Target Time    : " + 
            targetTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.println("=".repeat(80));

        List<DateTimeRecord> records = List.of(
                new DateTimeRecord(1, startDateTime, "Start record"),
                new DateTimeRecord(2, endDateTime, "End record"),
                new DateTimeRecord(3, targetDate.atTime(targetTime), "Target record")
        );

        return new ListItemReader<>(records);
    }

    @Bean
    @StepScope
    public ItemWriter<DateTimeRecord> dateTimeWriter() {
        return chunk -> {
            System.out.println("\n📝 Processing Records:");
            System.out.println("-".repeat(80));
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            for (DateTimeRecord record : chunk) {
                System.out.printf("  [%d] %s - %s%n",
                        record.getId(),
                        record.getDateTime().format(formatter),
                        record.getDescription());
            }
            
            System.out.println("-".repeat(80) + "\n");
        };
    }

    // Domain Class
    public static class DateTimeRecord {
        private final Integer id;
        private final LocalDateTime dateTime;
        private final String description;

        public DateTimeRecord(Integer id, LocalDateTime dateTime, String description) {
            this.id = id;
            this.dateTime = dateTime;
            this.description = description;
        }

        public Integer getId() { return id; }
        public LocalDateTime getDateTime() { return dateTime; }
        public String description() { return description; }
        public String getDescription() { return description; }
    }
}
```
<br/>

## DefaultDateTimeIncrementer
```java
package com.example.batch.config;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * JobParametersIncrementer를 사용한 기본값 주입 방법
 * 
 * 장점:
 * - 파라미터가 없으면 자동으로 현재 시간 주입
 * - 파라미터가 있으면 그대로 유지
 * - SpEL 표현식 없이도 깔끔하게 처리 가능
 */
public class DefaultDateTimeIncrementer implements JobParametersIncrementer {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss");
    
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    private static final DateTimeFormatter TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public JobParameters getNext(JobParameters parameters) {
        JobParametersBuilder builder = new JobParametersBuilder(parameters);

        // 현재 시간
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = now.toLocalDate();
        LocalTime currentTime = now.toLocalTime();

        // 1. startDateTime가 없으면 현재 시간 주입
        if (parameters.getString("startDateTime") == null) {
            builder.addString("startDateTime", now.format(DATE_TIME_FORMATTER));
        }

        // 2. endDateTime가 없으면 현재 시간 + 1시간 주입
        if (parameters.getString("endDateTime") == null) {
            LocalDateTime endTime = now.plusHours(1);
            builder.addString("endDateTime", endTime.format(DATE_TIME_FORMATTER));
        }

        // 3. targetDate가 없으면 오늘 날짜 주입
        if (parameters.getString("targetDate") == null) {
            builder.addString("targetDate", today.format(DATE_FORMATTER));
        }

        // 4. targetTime이 없으면 현재 시간 주입
        if (parameters.getString("targetTime") == null) {
            builder.addString("targetTime", currentTime.format(TIME_FORMATTER));
        }

        // 5. 매번 새로운 실행을 위한 유니크 키 (항상 업데이트)
        builder.addLong("run.id", System.currentTimeMillis());

        return builder.toJobParameters();
    }
}
```
<br/>

# e.g. (2) NullableDefaultJobConfiguration.java
## NullableDefaultJobConfiguration
```java
package com.example.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 방법 2: Nullable 파라미터 + Java 코드에서 기본값 처리
 * 
 * 장점:
 * - 복잡한 기본값 로직을 Java 코드로 처리 가능
 * - 비즈니스 로직에 맞는 기본값 설정 용이
 * - 디버깅과 테스트가 쉬움
 */
@Configuration
public class NullableDefaultJobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public NullableDefaultJobConfiguration(JobRepository jobRepository,
                                           PlatformTransactionManager transactionManager) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
    }

    @Bean
    public Job nullableDefaultJob(Step nullableDefaultStep) {
        return new JobBuilder("nullableDefaultJob", jobRepository)
                .incrementer(new CurrentTimeIncrementer())
                .start(nullableDefaultStep)
                .build();
    }

    @Bean
    @JobScope
    public Step nullableDefaultStep(
            ItemReader<String> nullableReader,
            ItemWriter<String> nullableWriter) {

        return new StepBuilder("nullableDefaultStep", jobRepository)
                .<String, String>chunk(10, transactionManager)
                .reader(nullableReader)
                .writer(nullableWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<String> nullableReader(
            // Nullable로 선언 - 파라미터 없으면 null
            @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
            @Value("#{jobParameters['startDateTime']}")
            LocalDateTime paramStartDateTime,

            @DateTimeFormat(pattern = "yyyy-MM-dd")
            @Value("#{jobParameters['targetDate']}")
            LocalDate paramTargetDate,

            @DateTimeFormat(pattern = "HH:mm:ss")
            @Value("#{jobParameters['targetTime']}")
            LocalTime paramTargetTime) {

        // Java 코드에서 null 체크 후 기본값 설정
        LocalDateTime startDateTime = (paramStartDateTime != null) 
                ? paramStartDateTime 
                : LocalDateTime.now();

        LocalDate targetDate = (paramTargetDate != null) 
                ? paramTargetDate 
                : LocalDate.now();

        LocalTime targetTime = (paramTargetTime != null) 
                ? paramTargetTime 
                : LocalTime.now();

        // 복잡한 로직도 가능
        LocalDateTime endDateTime;
        if (paramStartDateTime != null) {
            // 파라미터가 있으면 +8시간
            endDateTime = startDateTime.plusHours(8);
        } else {
            // 파라미터가 없으면 오늘 자정
            endDateTime = LocalDate.now().plusDays(1).atStartOfDay();
        }

        System.out.println("=".repeat(80));
        System.out.println("📅 Nullable Default Parameters:");
        System.out.println("-".repeat(80));
        System.out.println("  Param Start  : " + (paramStartDateTime != null ? "PROVIDED" : "NULL (using default)"));
        System.out.println("  Param Date   : " + (paramTargetDate != null ? "PROVIDED" : "NULL (using default)"));
        System.out.println("  Param Time   : " + (paramTargetTime != null ? "PROVIDED" : "NULL (using default)"));
        System.out.println();
        System.out.println("  Final Start  : " + startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("  Final End    : " + endDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("  Final Date   : " + targetDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        System.out.println("  Final Time   : " + targetTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
        System.out.println("=".repeat(80));

        List<String> items = List.of(
                "Start: " + startDateTime,
                "End: " + endDateTime,
                "Date: " + targetDate,
                "Time: " + targetTime
        );

        return new ListItemReader<>(items);
    }

    @Bean
    @StepScope
    public ItemWriter<String> nullableWriter() {
        return chunk -> {
            System.out.println("\n📝 Items:");
            chunk.forEach(item -> System.out.println("  - " + item));
            System.out.println();
        };
    }
}
```

## Incrementer
위에서 사용한 DefaultDateTimeIncrementer 를 그대로 사용<br/>



# 정리) 기본값으로 현재 날짜/시간 사용하기

파라미터가 없을 때 자동으로 현재 날짜/시간을 주입하는 3가지 방법을 소개합니다.

## 🎯 방법 비교

| 방법                        | 난이도   | 유연성   | 추천 상황            |
| --------------------------- | -------- | -------- | -------------------- |
| 1. Elvis 연산자             | ⭐ 쉬움   | ⭐⭐ 보통  | 간단한 기본값        |
| 2. JobParametersIncrementer | ⭐⭐ 보통  | ⭐⭐⭐ 높음 | 일관된 기본값 정책   |
| 3. Nullable + Java 코드     | ⭐⭐⭐ 복잡 | ⭐⭐⭐ 높음 | 복잡한 비즈니스 로직 |

<br/>



------

## 방법 1: Elvis 연산자 (?:) 사용 ⭐ 추천

**가장 간단하고 직관적인 방법**

### 코드 예시

```java
@Bean
@StepScope
public ItemReader<Order> reader(
    // 파라미터 없으면 현재 시간 사용
    @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    @Value("#{jobParameters['startDateTime'] ?: T(java.time.LocalDateTime).now()}")
    LocalDateTime startDateTime,
    
    // 파라미터 없으면 오늘 날짜 사용
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Value("#{jobParameters['targetDate'] ?: T(java.time.LocalDate).now()}")
    LocalDate targetDate,
    
    // 파라미터 없으면 현재 시간(시분초) 사용
    @DateTimeFormat(pattern = "HH:mm:ss")
    @Value("#{jobParameters['targetTime'] ?: T(java.time.LocalTime).now()}")
    LocalTime targetTime) {
    
    // startDateTime, targetDate, targetTime은 항상 값이 있음!
    return ...;
}
```

<br/>



### SpEL 표현식 설명

```java
#{jobParameters['startDateTime'] ?: T(java.time.LocalDateTime).now()}
  └────────┬────────────────────┘    └──────────┬───────────────────┘
          │                                     │
    파라미터가 있으면 사용              파라미터가 없으면 현재 시간 사용
```

<br/>



### 다양한 기본값 예시

```java
// 1. 현재 시간
@Value("#{jobParameters['dateTime'] ?: T(java.time.LocalDateTime).now()}")
LocalDateTime dateTime

// 2. 오늘 자정
@Value("#{jobParameters['midnight'] ?: T(java.time.LocalDate).now().atStartOfDay()}")
LocalDateTime midnight

// 3. 어제 날짜
@Value("#{jobParameters['yesterday'] ?: T(java.time.LocalDate).now().minusDays(1)}")
LocalDate yesterday

// 4. 한 시간 전
@Value("#{jobParameters['oneHourAgo'] ?: T(java.time.LocalDateTime).now().minusHours(1)}")
LocalDateTime oneHourAgo

// 5. 다음 달 1일
@Value("#{jobParameters['nextMonth'] ?: T(java.time.LocalDate).now().plusMonths(1).withDayOfMonth(1)}")
LocalDate nextMonth

// 6. 현재 시간의 정각 (분, 초 = 0)
@Value("#{jobParameters['hourStart'] ?: T(java.time.LocalDateTime).now().withMinute(0).withSecond(0)}")
LocalDateTime hourStart
```



### 실행 예시

```bash
# 파라미터 없이 실행 - 현재 시간 자동 주입
./gradlew bootRun --args='--job.name=defaultDateTimeJob'

# 파라미터 제공 - 제공된 값 사용
./gradlew bootRun --args='--job.name=defaultDateTimeJob startDateTime=2024-01-0109:00:00'
```

<br/>



------

## 방법 2: JobParametersIncrementer 사용

**모든 Job 실행에 일관된 기본값 정책을 적용할 때 유용**

### DefaultDateTimeIncrementer.java

```java
public class DefaultDateTimeIncrementer implements JobParametersIncrementer {

    @Override
    public JobParameters getNext(JobParameters parameters) {
        JobParametersBuilder builder = new JobParametersBuilder(parameters);
        
        LocalDateTime now = LocalDateTime.now();
        
        // 파라미터가 없으면 기본값 주입
        if (parameters.getString("startDateTime") == null) {
            builder.addString("startDateTime", 
                now.format(DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss")));
        }
        
        if (parameters.getString("endDateTime") == null) {
            builder.addString("endDateTime", 
                now.plusHours(1).format(DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss")));
        }
        
        if (parameters.getString("targetDate") == null) {
            builder.addString("targetDate", 
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }
        
        // 유니크 키 (매번 새로운 실행을 위해)
        builder.addLong("run.id", System.currentTimeMillis());
        
        return builder.toJobParameters();
    }
}
```

<br/>



### Job 설정

```java
@Bean
public Job myJob(Step myStep) {
    return new JobBuilder("myJob", jobRepository)
            .incrementer(new DefaultDateTimeIncrementer()) // ← 여기서 기본값 주입
            .start(myStep)
            .build();
}

@Bean
@StepScope
public ItemReader<Order> reader(
    // Incrementer에서 주입했으므로 항상 값이 있음
    @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    @Value("#{jobParameters['startDateTime']}")
    LocalDateTime startDateTime) {
    
    return ...;
}
```

### 장점

- ✅ 모든 파라미터를 한 곳에서 관리
- ✅ 복잡한 기본값 로직 구현 가능
- ✅ 테스트하기 쉬움

<br/>



### 실행 예시

```bash
# 파라미터 없이 실행 - Incrementer가 자동으로 현재 시간 주입
./gradlew bootRun --args='--job.name=myJob'

# 특정 파라미터만 제공 - 나머지는 Incrementer가 기본값 주입
./gradlew bootRun --args='--job.name=myJob targetDate=2024-12-25'
```

<br/>



------

## 방법 3: Nullable + Java 코드

**가장 유연하지만 코드가 길어지는 방법**

### 코드 예시

```java
@Bean
@StepScope
public ItemReader<Order> reader(
    // Nullable로 선언 - 파라미터 없으면 null
    @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    @Value("#{jobParameters['startDateTime']}")
    LocalDateTime paramStartDateTime,
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Value("#{jobParameters['targetDate']}")
    LocalDate paramTargetDate) {
    
    // Java 코드에서 null 체크 후 기본값 설정
    LocalDateTime startDateTime = (paramStartDateTime != null) 
            ? paramStartDateTime 
            : LocalDateTime.now();
    
    LocalDate targetDate = (paramTargetDate != null) 
            ? paramTargetDate 
            : LocalDate.now();
    
    // 복잡한 로직도 가능
    LocalDateTime endDateTime;
    if (paramStartDateTime != null) {
        // 파라미터가 있으면 시작 시간 + 8시간
        endDateTime = startDateTime.plusHours(8);
    } else {
        // 파라미터가 없으면 오늘 자정
        endDateTime = LocalDate.now().plusDays(1).atStartOfDay();
    }
    
    // 비즈니스 로직
    if (targetDate.getDayOfWeek() == DayOfWeek.SATURDAY || 
        targetDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
        // 주말이면 다음 월요일로 변경
        targetDate = targetDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
    }
    
    return ...;
}
```

### 장점

- ✅ 가장 유연한 방법
- ✅ 복잡한 비즈니스 로직 구현 가능
- ✅ 디버깅이 쉬움

### 단점

- ❌ 코드가 길어짐
- ❌ 각 Reader/Processor/Writer마다 중복 코드 가능성

<br/>



------

## 🚀 실전 활용 예시

### 1. 매일 자정 실행되는 배치 (어제 데이터 처리)

```java
@Bean
@StepScope
public ItemReader<Order> dailyReader(
    // 파라미터 없으면 어제 00:00:00
    @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    @Value("#{jobParameters['startDateTime'] ?: T(java.time.LocalDate).now().minusDays(1).atStartOfDay()}")
    LocalDateTime startDateTime,
    
    // 파라미터 없으면 오늘 00:00:00
    @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    @Value("#{jobParameters['endDateTime'] ?: T(java.time.LocalDate).now().atStartOfDay()}")
    LocalDateTime endDateTime) {
    
    // 어제 하루치 데이터 조회
    return repository.findByCreatedDateTimeBetween(startDateTime, endDateTime);
}
```

<br/>



### 2. 매시간 실행되는 배치 (지난 1시간 데이터 처리)

```java
@Bean
@StepScope
public ItemReader<Log> hourlyReader(
    // 파라미터 없으면 한 시간 전
    @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    @Value("#{jobParameters['startDateTime'] ?: T(java.time.LocalDateTime).now().minusHours(1).withMinute(0).withSecond(0)}")
    LocalDateTime startDateTime,
    
    // 파라미터 없으면 현재 시간의 정각
    @DateTimeFormat(pattern = "yyyy-MM-ddHH:mm:ss")
    @Value("#{jobParameters['endDateTime'] ?: T(java.time.LocalDateTime).now().withMinute(0).withSecond(0)}")
    LocalDateTime endDateTime) {
    
    // 지난 1시간 로그 조회
    return logRepository.findByTimestampBetween(startDateTime, endDateTime);
}
```

<br/>



### 3. 월말 정산 배치 (이번 달 전체 데이터)

```java
@Bean
@StepScope
public ItemReader<Transaction> monthlyReader(
    // 파라미터 없으면 이번 달 1일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Value("#{jobParameters['startDate'] ?: T(java.time.LocalDate).now().withDayOfMonth(1)}")
    LocalDate startDate,
    
    // 파라미터 없으면 다음 달 1일
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Value("#{jobParameters['endDate'] ?: T(java.time.LocalDate).now().plusMonths(1).withDayOfMonth(1)}")
    LocalDate endDate) {
    
    // 이번 달 전체 거래 조회
    return transactionRepository.findByDateBetween(startDate, endDate);
}
```

<br/>



------

## 📊 실행 결과 비교

### 파라미터 없이 실행

```bash
./gradlew bootRun --args='--job.name=defaultDateTimeJob'
```



**출력:**

```
================================================================================
📅 Default DateTime Parameters:
--------------------------------------------------------------------------------
  Start DateTime : 2024-11-11 14:30:45  ← 현재 시간 자동 주입
  End DateTime   : 2024-11-11 00:00:00  ← 오늘 자정
  Target Date    : 2024-11-11           ← 오늘 날짜
  Target Time    : 14:30:45             ← 현재 시간
================================================================================
```

<br/>



### 파라미터와 함께 실행

```bash
./gradlew bootRun --args='--job.name=defaultDateTimeJob startDateTime=2024-01-0109:00:00'
```

**출력:**

```
================================================================================
📅 Default DateTime Parameters:
--------------------------------------------------------------------------------
  Start DateTime : 2024-01-01 09:00:00  ← 제공된 값 사용
  End DateTime   : 2024-11-11 00:00:00  ← 나머지는 기본값
  Target Date    : 2024-11-11
  Target Time    : 14:30:45
================================================================================
```

<br/>



------

## ⚠️ 주의사항

### 1. Nullable 사용 시 NPE 주의

```java
// ❌ 잘못된 예 - NPE 발생 가능
@Value("#{jobParameters['startDateTime']}")
LocalDateTime startDateTime  // null이면 NPE!

// ✅ 올바른 예 1 - Elvis 연산자
@Value("#{jobParameters['startDateTime'] ?: T(java.time.LocalDateTime).now()}")
LocalDateTime startDateTime

// ✅ 올바른 예 2 - Java에서 null 체크
@Value("#{jobParameters['startDateTime']}")
LocalDateTime paramStartDateTime  // nullable
// ... Java 코드에서 null 체크
```



### 2. 타임존 고려

```java
// 서버 타임존 사용
@Value("#{jobParameters['now'] ?: T(java.time.LocalDateTime).now()}")
LocalDateTime now

// UTC 타임존 사용
@Value("#{jobParameters['nowUtc'] ?: T(java.time.Instant).now().atZone(T(java.time.ZoneId).of('UTC')).toLocalDateTime()}")
LocalDateTime nowUtc

// 특정 타임존 사용 (예: Asia/Seoul)
@Value("#{jobParameters['nowKst'] ?: T(java.time.ZonedDateTime).now(T(java.time.ZoneId).of('Asia/Seoul')).toLocalDateTime()}")
LocalDateTime nowKst
```



### 3. 테스트 시 주의

```java
@Test
void testWithNoParameters() {
    JobParameters jobParameters = new JobParametersBuilder()
            .addLong("run.id", 1L)  // 다른 파라미터만 제공
            .toJobParameters();
    
    // startDateTime은 기본값(현재 시간)이 사용됨
    JobExecution execution = jobLauncher.run(job, jobParameters);
    
    // 현재 시간이 사용되므로 시간 검증은 범위로 체크
    assertTrue(execution.getStatus() == BatchStatus.COMPLETED);
}
```

<br/>



------

## 💡 베스트 프랙티스

1. **간단한 경우**: Elvis 연산자 사용 (방법 1) ⭐ 추천
2. **일관된 정책**: JobParametersIncrementer 사용 (방법 2)
3. **복잡한 로직**: Nullable + Java 코드 (방법 3)
4. **타임존 명시**: 가능하면 UTC 또는 명시적 타임존 사용
5. **문서화**: 기본값 동작을 README에 명확히 기술
6. **로깅 추가**: 사용된 파라미터 값을 로그로 출력



<br/>



------

## 📚 참고

- [Spring Expression Language (SpEL)](https://docs.spring.io/spring-framework/reference/core/expressions.html)
- [Java Time API](https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html)
- [Spring Batch Step Scope](https://docs.spring.io/spring-batch/reference/step/late-binding.html)

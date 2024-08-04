# kotlin ObjectMapper - BigDecimal



## 참고

- https://stackoverflow.com/questions/52149589/return-bigdecimal-fields-as-json-string-values-in-java
- https://stackoverflow.com/questions/11319445/java-to-jackson-json-serialization-money-fields
- https://jsonobject.tistory.com/466

<br/>



## Pojo

```kotlin
package io.study.gosgjung.reactive_redis.study

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.math.BigDecimal

data class StockDto(

    val ticker: String = "!!!NOT-ASSIGNED",

    // (1)
    @JsonSerialize(using = BigDecimalRounding6Serializer::class)
    val lastPrice: BigDecimal = BigDecimal.valueOf(0.0)

)
```

(1) 

- `@JsonSerialize` 애노테이션을 사용했습니다. 그리고 별도로 정의해둔 `BigDecimalRounding6Serializer` 라는 클래스를 사용해 `BigDecimal` 타입의 필드에 대해 Serialize/Deserialize 하도록 애노테이션을 지정했습니다.

<br/>



## JsonSerializer

`JsonSerializer` 클래스는 jackson 모듈에서 제공하는 Serialize, Deserialize 를 위한 클래스입니다.<br>

제 경우에는 아래 BiDecimalRounding6Serializer 클래스가 JsonSerializer 클래스를 상속받아서 Serialize 하는 코드를 작성했습니다.<br/>

소숫점 6번째 자리에서 반올림하게끔 지정해주는 방식입니다.<br/>



**BigDecimalRounding6Serializer.kt**

```kotlin
package io.study.gosgjung.reactive_redis.study

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.math.BigDecimal
import java.math.RoundingMode

class BigDecimalRounding6Serializer : JsonSerializer<BigDecimal>(){

    companion object {
        const val SCALE_SIX = 6
        val ROUNDING_MODE = RoundingMode.HALF_EVEN
    }

    override fun serialize(value: BigDecimal?, gen: JsonGenerator?, serializers: SerializerProvider?) {
        gen?.writeString(value?.setScale(SCALE_SIX, ROUNDING_MODE).toString())
    }

}
```

<br/>



## 테스트

assertion 을 사용하지는 않았지만, 간단한 출력을 해보기 위한 코드입니다

```kotlin
@Test
fun test6(){
    // (1) 
    val kotlinModule = KotlinModule.Builder()
        .withReflectionCacheSize(512)
        .configure(KotlinFeature.NullToEmptyCollection, false)
        .configure(KotlinFeature.NullToEmptyMap, false)
        .configure(KotlinFeature.NullIsSameAsDefault, enabled = true)
        .configure(KotlinFeature.StrictNullChecks, false)
        .build()

    // (2)
    val mapper6 = ObjectMapper().registerModule(kotlinModule)
    
    // (3) 
    val str6 = mapper6.writeValueAsString(StockDto())
    val data6 = mapper6.readValue<StockDto>(str6, StockDto::class.java)
    logger.info("\ndata = $data6")
}
```

(1)

- Kotlin 에서 ObjectMapper 를 통해서 Serialize, Deserialize 할 때에는 NPE 관련 에러에 관련된 여러 에러에 부딪히게 되는데 여기에 대한 설정들을 KotlinModule 을 이용해서 설정하는 코드입니다.

(2)

- (1) 에서 선언한 kotlinModule 을 ObjectMapper 내에 등록합니다.

(3)

- (2) 에서 생성한 mapper 를 이용해서 객체를 직렬화하고 읽어들이는 것을 테스트 해봅니다.



출력결과

```plain
data = StockDto(ticker=!!!NOT-ASSIGNED, lastPrice=0.000000)
```

<br/>




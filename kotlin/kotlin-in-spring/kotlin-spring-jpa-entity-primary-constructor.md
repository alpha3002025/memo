# kotlin + spring 프로젝트에서 JPA Entity 내의 기본생성자 이슈

```kotlin
@Entity
class Book(
	val name: String,
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
)
```

위의 코드는 가끔 아래의 에러를 낼 수 있습니다.

```plain
Class 'Book' should have [public, protected] no-arg constructor
```

<br/>



## build.gradle.kts 에 jpa plugin 추가

```kotlin
plugins {
    // ...
	id 'org.jetbrains.kotlin.plugin.jpa' version '1.6.21'
}
```

<br/>



## build.gradle.kts 에 kotlin reflect 추가

```kotlin
dependencies {
    implementation 'org.jetbrains.kotlin:kotlin-reflect:1.6.21'
}
```

<br/>




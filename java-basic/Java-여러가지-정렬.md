# Java 여러가지 정렬

# 참고자료

Java Stream 정렬 

- https://ssamdu.tistory.com/7



# 예제로 사용할 POJO

```java
package io.chagchagchag.example.example_stream.member1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
public class Member {
    private Long id;
    private String name;
    private Integer age;
}
```

<br/>



# Collections.sort

다음의 두 방식에 대한 예제를 살펴봅니다. 자세한 설명은 생략합니다 ㅋㅋ<br/>

- Comparator 를 lambda로 직접 구현
- Comparator 에서 기본 제공하는 `comparingInt(V)`, `comparing(V)` 를 활용하는 방식

<br/>



## Comparator 를 lambda 로 직접 구현

```java
@Test
public void TEST_SORTING_COLLECTIONS_SORT__USING_COMPARATOR(){
    Member alice = Member.builder().id(1L).name("Alice").age(27).build();
    Member ben = Member.builder().id(2L).name("Ben").age(26).build();
    Member charles = Member.builder().id(3L).name("Charles").age(25).build();

    List<Member> members = Arrays.asList(alice, ben, charles);

    Collections.sort(members, (left, right) -> (int) (left.getId() - right.getId()));
    assertThat(members.get(0).getName()).isEqualTo("Alice");

    Collections.sort(members, (left, right) -> left.getAge() - right.getAge());
    assertThat(members.get(0).getName()).isEqualTo("Charles");

    Collections.sort(members, (left, right) -> left.getName().compareTo(right.getName()));
    assertThat(members.get(0).getName()).isEqualTo("Alice");
}
```

<br/>



## Comparator 내의 static 메서드 활용 (`Comparator.comparing~~~()`) 

```java
// Comparable 을 구현하고 있는 객체에 대한 비교는
// Comparator.comparing, Comparator.comparingInt 등을 활용 가능
@Test
public void TEST_SORTING_COMPARATOR__USING_COMPARATOR_COMPARING__(){
    Member alice = Member.builder().id(1L).name("Alice").age(27).build();
    Member ben = Member.builder().id(2L).name("Ben").age(26).build();
    Member charles = Member.builder().id(3L).name("Charles").age(25).build();

    List<Member> members = Arrays.asList(alice, ben, charles);

    Collections.sort(members, Comparator.comparingInt(Member::getAge));
    assertThat(members.get(0).getName()).isEqualTo("Charles");

    Collections.sort(members, Comparator.comparing(Member::getName));
    assertThat(members.get(0).getName()).isEqualTo("Alice");
}
```

<br/>



## reversed()

역순정렬은 Comparator.reversed() 메서드를 사용합니다.

```java
@Test
public void TEST_COMPARATOR_REVERSED(){
    Member alice = Member.builder().id(1L).name("Alice").age(27).build();
    Member ben = Member.builder().id(2L).name("Ben").age(26).build();
    Member charles = Member.builder().id(3L).name("Charles").age(25).build();

    List<Member> members = Arrays.asList(alice, ben, charles);

    Collections.sort(members, Comparator.comparingInt(Member::getAge).reversed());
    assertThat(members.get(0).getName()).isEqualTo("Alice");
}
```

<br/>



## thenComparaing

thenComparing 은 여러 정렬조건을 조합해서 정렬할 때 사용하는 메서드 입니다.<br/>

나이 순으로 오름차순 정렬한 결과에 대해 이름 순으로 한번 더 정렬해주는 예제입니다. 여러 조건식에 대한 정렬 조건을 추가할 때 사용합니다.

```java
@Test
public void TEST_THEN_COMPARING(){
    Member alice = Member.builder().id(1L).name("Alice").age(27).build();
    Member ben = Member.builder().id(2L).name("Ben").age(26).build();
    Member charles = Member.builder().id(3L).name("Charles").age(25).build();

    List<Member> members = Arrays.asList(alice, ben, charles);

    Collections.sort(
            members,
            Comparator
                    .comparingInt(Member::getAge)
                    .thenComparing(Member::getName)
    );

    members.forEach(System.out::println);
    assertThat(members.get(0).getName()).isEqualTo("Charles");
}
```

<br/>



# Java Stream sorted

## Comparator 를 lambda 로 직접 구현

```java
@Test
public void TEST_STREAM_SORT_TEST_COMPARATOR_USING_LAMBDA(){
    Member alice = Member.builder().id(1L).name("Alice").age(27).build();
    Member ben = Member.builder().id(2L).name("Ben").age(26).build();
    Member charles = Member.builder().id(3L).name("Charles").age(25).build();

    List<Member> members = Arrays.asList(alice, ben, charles);

    List<Member> sortedMembers1 = members.stream()
            .sorted((v1, v2) -> v1.getAge() - v2.getAge())
            .collect(Collectors.toList());
    assertThat(sortedMembers1.get(0).getName()).isEqualTo("Charles");

    List<Member> sortedMembers2 = members.stream()
            .sorted((v1, v2) -> v1.getName().compareTo(v2.getName()))
            .collect(Collectors.toList());
    assertThat(sortedMembers2.get(0).getName()).isEqualTo("Alice");
}
```

<br/>



## Comparator 내의 static 메서드를 활용 (`Comparator.comparing~~~()`)

```java
@Test
public void TEST_STREAM_SORT_TEST_COMPARATOR_COMPARING(){
    Member alice = Member.builder().id(1L).name("Alice").age(27).build();
    Member ben = Member.builder().id(2L).name("Ben").age(26).build();
    Member charles = Member.builder().id(3L).name("Charles").age(25).build();

    List<Member> members = Arrays.asList(alice, ben, charles);

    List<Member> sortedMembers = members.stream()
            .sorted(Comparator.comparing(Member::getAge))
            .collect(Collectors.toList());

    assertThat(sortedMembers.get(0).getName()).isEqualTo("Charles");
}
```

<br/>



## reversed()

```java
@Test
public void TEST_STREAM_REVERSED(){
    Member alice = Member.builder().id(1L).name("Alice").age(27).build();
    Member ben = Member.builder().id(2L).name("Ben").age(26).build();
    Member charles = Member.builder().id(3L).name("Charles").age(25).build();

    List<Member> members = Arrays.asList(alice, ben, charles);

    List<Member> sortedMembers = members.stream()
            .sorted(Comparator.comparing(Member::getAge).reversed())
            .collect(Collectors.toList());

    assertThat(sortedMembers.get(0).getName()).isEqualTo("Alice");
}
```

<br/>



## thenComparaing

```java
@Test
public void TEST_STREAM_COMPARATOR_THEN_COMPARING(){
    Member alice = Member.builder().id(1L).name("Alice").age(27).build();
    Member ben = Member.builder().id(2L).name("Ben").age(26).build();
    Member charles = Member.builder().id(3L).name("Charles").age(25).build();

    List<Member> members = Arrays.asList(alice, ben, charles);

    List<Member> sortedMembers = members.stream()
            .sorted(Comparator
                    .comparingInt(Member::getAge)
                    .thenComparing(Member::getName))
            .collect(Collectors.toList());

    assertThat(sortedMembers.get(0).getName()).isEqualTo("Charles");
}
```

<br/>



# Comparable

문서 작업 예정이에용

# PriorityQueue

다익스트라, BFS 의 악몽 ㄷㄷㄷ



# 기타

## `int []` 을 정렬된 `List<Integer>` 으로 변환

옛날에 코테에서 스트림 떠올리고 이러는게 Java 는 너무 비효율적이긴 했다. <br/>

생각나는대로 코테 때 접했던 다양한 Java 정렬 케이스들 정리 예정<br/>

<br/>

가급적이면 코테에서는 Java 는 쓰지 말자. Java 는 코테에서 사용할 언어는 절대 아니다.<br/>

코테로 Java 3년 쓰면 치매초기증상 나타남<br/>

고통스러워짐. 다른거 구현하느라 다 까먹어서  `억...내가 방금 뭐하려고 했었지?` 하는 증상이 자주 나타남<br/>

<br/>


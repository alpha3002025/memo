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



# Comparable, PriorityQueue

코테 언어로 python 을 사용하기로 마음먹은데에는 개인적으로는 여러가지 요인이 있지만 그 중 하나가 Comparable 이다. 시험장에서 이거 구현하고 있고, compareTo 구현하고 있는 것 자체가 말이 안된다. 나중에 PTSD 온다 분명히.<br/>

개인적으로는... 자바는 유지보수성 좋게 객체지향 개발할때는 좋지만 코테에서 Java 를 사용한다는 것 자체가 일단은 '불합격' 확률을 꽤 높여놓은 채로 시작하는 거라고 생각한다.<br/>

아래는 합승택시 요금이라는 프로그래머스 lv3 문제다. 사실 이게 무슨 의미의 문제인지 모를때는 너무 어렵게 느꼈었다. 지금은 어떤 의도의 문제인지 알고 있기 때문에 긴장까지는 안하지만, Java 로 풀때 필요한 문법적 지식을 떠올릴때 스트레스가 꽤 온다.<br/>

```java
import java.util.*;

class Solution {
    class Edge implements Comparable<Edge>{
        int to;
        int cost;
        
        public Edge(int to, int cost){
            this.to = to;
            this.cost = cost;
        }
        
        @Override
        public int compareTo(Edge other){
            return this.cost - other.cost;
        }
    }
    
    static List<List<Edge>> graph = new ArrayList<>();
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        initGraph(n, fares);
        
        int [] distance_s = new int [n+1];
        int [] distance_a = new int [n+1];
        int [] distance_b = new int [n+1];
        
        Arrays.fill(distance_a, Integer.MAX_VALUE);
        Arrays.fill(distance_b, Integer.MAX_VALUE);
        Arrays.fill(distance_s, Integer.MAX_VALUE);
        
        distance_s = dijkstra(s-1, distance_s);
        distance_a = dijkstra(a-1, distance_a);
        distance_b = dijkstra(b-1, distance_b);
        
        int minCost = Integer.MAX_VALUE;
        for(int i=0; i<n; i++){
            int cost = distance_a[i] + distance_b[i] + distance_s[i];
            minCost = Math.min(cost, minCost);
        }
        
        return minCost;
    }
    
    public int [] dijkstra(int start, int [] distance){
        Queue<Edge> queue = new PriorityQueue<>();
        
        for(Edge e: graph.get(start)){
            distance[e.to] = e.cost;
            queue.offer(e);
        }
        
        distance[start] = 0;
        
        while(!queue.isEmpty()){
            Edge curr = queue.poll();
            
            if(distance[curr.to] > curr.cost) continue;
            
            for(Edge neighbor : graph.get(curr.to)){
                if(distance[neighbor.to] > distance[curr.to] + neighbor.cost){
                    distance[neighbor.to] = distance[curr.to] + neighbor.cost;
                    queue.offer(new Edge(neighbor.to, distance[neighbor.to]));
                }
            }
        }
        
        return distance;
    }
    
    public void initGraph(int n, int [][] fares){
        for(int i=0; i<n; i++) 
            graph.add(new ArrayList<>());
        
        for(int fare[] : fares){
            int from = fare[0]-1;
            int to = fare[1]-1;
            int cost = fare[2];
            graph.get(from).add(new Edge(to, cost));
            graph.get(to).add(new Edge(from, cost));
        }
    }
}
```

<br/>



난 왜 옛날에 코테용으로 절.대. 안되는 자바를 잡고 있었을까 미련하게... 이런 생각을 했다. python 으로 위의 코드를 표현하면 다음과 같다. 코딩테스트 시에 Java 언어는 스크롤 내릴 때마다 앞 뒤에 안보이는 변수들 머릿속에 기억했다가 스크롤 올렸다 내렸다 하는 데에 시간 소모가 꽤 크다. 파이썬이 알고리즘 풀이에 더 적합한 언어라는 이야기에는 어느 정도 일리가 있는 말이지 않나 하고 생각할 때가 많다.<br/>

```python
import heapq

def solution(n, s, a, b, fares):

    link = [[] for _ in range(n+1)]
    for x, y, z in fares:
        link[x].append((z, y))
        link[y].append((z, x))

    def dijkstra(start):
        dist = [987654321] * (n + 1)
        dist[start] = 0
        heap = []
        heapq.heappush(heap, (0, start))
        while heap:
            value, destination = heapq.heappop(heap)
            if dist[destination] < value:
                continue

            for v, d in link[destination]:
                next_value = value + v
                if dist[d] > next_value:
                    dist[d] = next_value
                    heapq.heappush(heap, (next_value, d))
        return dist

    dp = [[]] + [dijkstra(i) for i in range(1, n+1)]
    
    answer = 987654321
    for i in range(1, n+1):
        answer = min(dp[i][a] + dp[i][b] + dp[i][s], answer)

    return answer
```

<br/>



# Comparable vs Comparator

## Comparable

`java.lang` 패키지 내에 존재하는 interface 다. 정렬을 위해 사용된다. 그런데 인자값을 하나만 받기에 값을 담아두는 용도의 클래스가 implements 해서 사용하는 방법 밖에 없다. 객체의 기본 정렬 방식을 지정하고자 할 때 사용한다.

- 인자로 들어온 값(other)보다 현재 객체의 값이 더 클때
  - return 1
- 인자로 들어온 값(other)보다 현재 객체의 값이 같을 때
  - return 0
- 인자로 들어온 값(other)보다 현재 객체의 값이 더 작을 때
  - return -1

<br/>



## Comparator

`java.util` 패키지 내에 존재하는 interface 다. 정렬을 위해 사용된다. 인자값을 두개 받는다. Comparable 이 객체에 기본정렬방식을 지정하는 데에 주로 사용되는 반면 Comparator 를 사용하면, 어떤 객체든 Comparator 를 이용해 정렬이 가능하다. <br/>

객체 내부에 지정된 기본 정렬방식을 지정할때는 Comparable 을 쓰는 반면, Comparator 는 객체에 정렬방식이 지정되어있지 않아도 어떤 객체든 left, right 와 같이 두 파라미터를 받기 때문에 객체 외부에서도 정렬 기준을 만들어서 Collection 등에 전달 할 수 있다. Comparator 가 좋은 이유는 함수의 인자로도 전달할 수 있기 때문이다. 주로 Stream API 에서 Comparator 를 많이 쓴다.<br/>

```java
import java.util.*;
import java.math.*;

public class StockComparatorExample {
    public static void main(String [] args){
        List<Stock> list = sampleFixture();

        System.out.println("");
        System.out.println("Price sort >>> ");
        Collections.sort(list);
        list.stream().forEach(System.out::println);
        /*
         * 출력결과 : 객체에 디폴트로 지정해둔 정렬 방식인 가격 낮은 순으로 오름차순 정렬
            Stock [ ticker = AMZN, price = 131.47, per = 104.34 ]
            Stock [ ticker = MSFT, price = 332.06, per = 34.23 ]
            Stock [ ticker = NVDA, price = 439.38, per = 106.39 ]
         */

         System.out.println("");
         System.out.println("P/E Ratio sort >>> ");
         Collections.sort(list, StockCompareType.PER.getComparator());
         list.stream().forEach(System.out::println);
         
         /*
          * 출력결과 : 직접 지정한 PER Comparator 로 오름차순 정렬
            P/E Ratio sort >>>
            Stock [ ticker = MSFT, price = 332.06, per = 34.23 ]
            Stock [ ticker = AMZN, price = 131.47, per = 104.34 ]
            Stock [ ticker = NVDA, price = 439.38, per = 106.39 ]
          */

        
         System.out.println("");
         System.out.println("Alphabetical sort >>> ");
         Collections.sort(list, StockCompareType.TICKER.getComparator());
         list.stream().forEach(System.out::println);
         /*
          * 출력결과 : 직접 지정한 Ticker Comparator 로 오름차순 정렬
            Alphabetical sort >>>
            Stock [ ticker = AMZN, price = 131.47, per = 104.34 ]
            Stock [ ticker = MSFT, price = 332.06, per = 34.23 ]
            Stock [ ticker = NVDA, price = 439.38, per = 106.39 ]
          */
    }   
    
    static List<Stock> sampleFixture(){
        return Arrays.asList(
            new Stock("MSFT", BigDecimal.valueOf(332.06), BigDecimal.valueOf(34.23)),
            new Stock("AMZN", BigDecimal.valueOf(131.47), BigDecimal.valueOf(104.34)),
            new Stock("NVDA", BigDecimal.valueOf(439.38), BigDecimal.valueOf(106.39))
        );
    }
}

class Stock implements Comparable<Stock>{
    String ticker;
    BigDecimal price;
    BigDecimal per;

    public Stock(String ticker, BigDecimal price, BigDecimal per){
        this.ticker = ticker;
        this.price = price;
        this.per = per;
    }

    public String getTicker(){
        return this.ticker;
    }

    public BigDecimal getPrice(){
        return this.price;
    }

    public BigDecimal getPer(){
        return this.per;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("Stock [ ")
            .append("ticker = ").append(ticker).append(", ")
            .append("price = ").append(price).append(", ")
            .append("per = ").append(per)
            .append(" ]");

        return builder.toString();
    }

    @Override
    public int compareTo(Stock other) {
        return this.price.subtract(other.price).intValue();
    }

}

enum StockCompareType{
    PER("PER") {
        @Override
        public Comparator<Stock> getComparator() {
            return (left, right) -> left.per.subtract(right.per).intValue();            
        }
    }, TICKER("TICKER") {
        @Override
        public Comparator<Stock> getComparator() {
            return (left, right) -> left.ticker.compareTo(right.ticker);
        }
    };

    private final String typeName;

    StockCompareType(String typeName){
        this.typeName = typeName;
    }

    public abstract Comparator<Stock> getComparator();
}
```





# 기타

## Stream.sorted() - Comparator::comparingInt(...) 활용

참고 : [섬 연결하기](https://school.programmers.co.kr/learn/courses/30/lessons/42861)

```java
class Solution{
  
  // ...
  
  public int solution(int n, int [][] costs){
    Edge[] arr = Arrays.stream(costs)
      .map(c -> new Edge(c[0], c[1], c[2]))
      .sorted(Comparator.comparingInt(e -> e.cost))
      .toArray(Edge[]::new);
    
		// ...
        
    return cost;
    
  }
  
  // ...
  
}
```

<br/>



## `int [][] ` 정렬

참고 : [인사고과](https://school.programmers.co.kr/learn/courses/30/lessons/152995)

```java
import java.util.*;

class Solution {
    // ...
  	
    public int [][] sort(int [][] scores){
        Arrays.sort(scores, new Comparator<int[]>() {
            @Override
            public int compare (int [] left, int [] right){
                if(left[0] < right[0]) return 1;
                
                else if(left[0] == right[0]){
                    if(left[1] > right[1]) 
                        return 1;
                    else 
                        return -1;
                }
                else return -1;
            }
        });
        return scores;
    }
}
```

<br/>



## `int []` 을 정렬된 `List<Integer>` 으로 변환

```java
@Test
public void int_arr_TO_LIST_Integer(){
    int [] array = {5,2,3,4,1};
    List<Integer> list = Arrays.stream(array)
            .mapToObj(Integer::valueOf)
            .sorted()
            .collect(Collectors.toList());

    System.out.println("list = " + list);
}
```

<br/>




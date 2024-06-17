## 배치 처리 방식 2가지

- Tasklet 을 이용한 Task 처리
- Chunk 를 이용한 chunk 기반 처리
  - ItemReader, ItemProcessor, ItemWriter
  - 대량 처리에 적합
  - e.g.
    - 10000 개의 데이터를 1000 개씩 10개의 덩어리로 수행
    - 참고) 만약 tasklet 으로 처리하면 10000개를 한번에 처리하거나 수동으로 1000개 씩 분할하는 코드를 작성해야 함

<br/>



## Reader, Processor, Writer

Reader 가 null 을 return 할 때 까지 Step 을 반복<br/>



`<Input, Output> chunk(int)`

- ItemReader : 데이터를 읽어들여서 읽어들인 데이터를 return
- ItemProcessor : Input 을 받아서 Processing 해서 Output 으로 return
- ItemWriter : List\<Output\> 을 받아서 write

<br/>



## Parameter

배치 실행시 필요한 값을 parameter 로 외부에서 주입하며, JobParameters 는 외부에서 주입된 Parameter 를 관리하는 객체입니다.

- String parameter = jobParameters.getString(key, defaultValue)
- `@Value("#{jobParameters[key]}")` 



<br/>







정리를 해둘 예정인데, 아직은 시작단계 으...<br/>

지금은 시간이 부족하고 회사에서도 점심시간마다 조금씩 시간내서 정리해나가기로 ... 으윽<br/>


계정 서비스에 이메일을 변경할 수 있도록 원하는 추가 요구사항이 발생한다면?



**2024.08.13**<br/>

일단 2024.08.13 현재의 생각은 이렇다.

- member 테이블이 있고 email 컬럼, id 컬럼 및 기타 여러 컬럼 들이 있다고 하자.
- 이때 member 테이블의 email 컬럼은 더 이상 사용안하기로 결정하고
- member\_email, member\_email\_history 테이블을 만든다.
- member\_email 테이블에 member 테이블의 email 컬럼의 값들을 모두 복사해서 이관해준다.
- member\_email 테이블에는 id, member\_id, email 컬럼이 있고, member\_id 에 대해 email을 수정하는 역할을 하며, member\_email\_history 에는 email 변경 기록(CRAETE,UPDATE,DELETE 연산 유형, 수정일 등)을 저장하거나 추가한다.

이렇게 member\_email 테이블 을 새로 추가하는 이유는 기존 테이블의 email 에 인덱스를 새로 걸거나 유니크 키를 걸 경우에 드는 작업에 드는 시간 비용보다는 새로운 테이블을 추가해서 데이터를 옮겨주는게 더 시간이 절약될 것 같았다. 



사용자 로그인 시 사용자 조회할 때에는?

- 사용자는 email, pw 로 로그인 요청을 한다.
- 서버는 member\_email 에서 email 을 조회하고, member\_id 를 찾는다
- 서버에서는 이 member\_id 를 통해서 member 테이블을 조회한다거나 ci 값 비교를 한다거나 여러가지 작업을 수행한다.
- 보통은 join을 통해서 값을 찾으며, 일치하는 값이 없을 경우 Exception 을 던진다.








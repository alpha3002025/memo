참고자료

- https://velog.io/@grr1203/%ED%95%9C-%EC%BB%B4%ED%93%A8%ED%84%B0%EC%97%90%EC%84%9C-Github-%EA%B3%84%EC%A0%95-%EC%97%AC%EB%9F%AC%EA%B0%9C-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0



(1) ssh-agent 프로세스를 킨다.

```bash
$ ssh-agent -s
...
```

<br/>



(2) ssh-key 를 생성한다.

```bash
## 먼저 ~/.ssh 로 이동한다.
$ cd ~/.ssh

## ssh-keygen 으로 ssh 키 페어 생성
$ ssh-keygen -t rsa -C "chagchagchag.dev@gmail.com" -f "id_rsa_chagchagchag.dev"
$ ssh-keygen -t rsa -C "alpha300uk@gmail.com" -f "id_rsa_alpha300uk"

... 
```

<br/>



(3) 켜둔 ssh-agent 내에 ssh-key 를 추가한다. 계정 수 만큼 만들어둔 ssh-key 에 대해 ssh-add 를 수행한다.

```bash
$ eval "$(ssh-agent -s)"

$ ssh-add ~/.ssh/id_rsa_chagchagchag.dev
$ ssh-add ~/.ssh/alpha300uk
...
```

<br/>



(4) github 계정 설정 내에 SSH Public Key 를 추가

- 우측 상단 프로필 버튼 \> Settings 버튼 클릭 \> SSH and GPG keys \> new SSH Key 버튼 클릭

- title 에 적당한 제목을 적고, 내용란에는 공개키의 내용을 복사해서 붙여넣기



(5) 모두 설치가 완료됐다.

 클론받고, 컨트리뷰트를 하거나, 푸시를 할 때에는 SSH 방식으로 하면 된다.
# DeleteMapping에서 @RequestBody 사용 가이드
이 리포지터리는 거의 사용하지 않고 obsidian 을 사용중이지만, 어떤 obsidian 에 저장할지 모르는 상황이 발생!!! 해서 여기에 정리하기로 했다. 전 직장에서 아직도 내 리포지터리를 보는 것 같아서 아직까지도 더럽고 불결하지만... 어쩌겠어. 취업 후에 깃헙 계정 또 새로 하나 파야지...<br/>


## 출처
- claude
- 질문 : DeleteMapping 은 '@RequestBody' 를 사용해도 문제가 없나요?

<br/>

## 요약

`@DeleteMapping`과 `@RequestBody`를 함께 사용하는 것은 **기술적으로는 가능**하지만 **권장되지 않습니다**.

---

## 기술적 측면

Spring Framework에서는 `@DeleteMapping`과 `@RequestBody`를 함께 사용할 수 있습니다.

### 동작 가능한 예제

```java
@DeleteMapping("/users")
public ResponseEntity<Void> deleteUser(@RequestBody UserDeleteRequest request) {
    // 정상 작동함
    userService.deleteUser(request.getUserId());
    return ResponseEntity.ok().build();
}
```

---

## 실무에서의 문제점

### 1. RESTful 원칙 위배

DELETE는 일반적으로 URI로 리소스를 식별하는 것이 표준입니다.

**권장되는 방식:**
```java
@DeleteMapping("/users/{userId}")
public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
    userService.deleteUser(userId);
    return ResponseEntity.noContent().build();
}
```

### 2. HTTP 스펙의 모호함

- **RFC 7231** 명세에서 DELETE 요청의 body는 "의미가 정의되지 않음(no defined semantics)"이라고 명시
- 일부 프록시나 방화벽에서 DELETE 요청의 body를 제거할 수 있음
- 예상치 못한 동작이 발생할 가능성 존재

### 3. 클라이언트 호환성 문제

- 일부 HTTP 클라이언트나 라이브러리는 DELETE 요청에 body를 보내는 것을 제한
- 특정 환경에서 지원되지 않을 수 있음
- 크로스 플랫폼 호환성 이슈 발생 가능

---

## 대안

여러 리소스를 삭제하거나 복잡한 삭제 로직이 필요한 경우:

### 1. POST 메서드 사용

```java
@PostMapping("/users/delete-batch")
public ResponseEntity<Void> deleteBatch(@RequestBody List<Long> userIds) {
    userService.deleteUsers(userIds);
    return ResponseEntity.ok().build();
}
```

### 2. Query Parameter 활용

```java
@DeleteMapping("/users")
public ResponseEntity<Void> deleteUsers(@RequestParam List<Long> ids) {
    userService.deleteUsers(ids);
    return ResponseEntity.noContent().build();
}

// 호출 예: DELETE /users?ids=1,2,3
```

**주의:** URI 길이 제한을 고려해야 함

### 3. 별도 엔드포인트 생성

```java
@PostMapping("/users/bulk-delete")
public ResponseEntity<BatchDeleteResult> bulkDelete(@RequestBody BatchDeleteRequest request) {
    BatchDeleteResult result = userService.bulkDelete(request);
    return ResponseEntity.ok(result);
}
```

---

## 결론

- ✅ **동작은 함**: Spring에서 기술적으로 문제없이 동작
- ⚠️ **권장하지 않음**: RESTful 설계 원칙과 HTTP 스펙 관점에서 비권장
- 🔧 **대안 사용**: 복잡한 삭제 로직이 필요한 경우 POST나 별도 엔드포인트 사용

**Best Practice**: URI로 리소스를 식별하고, 복잡한 로직이 필요한 경우 명시적인 엔드포인트를 별도로 설계하는 것이 좋습니다.

---

*작성일: 2025년 10월*

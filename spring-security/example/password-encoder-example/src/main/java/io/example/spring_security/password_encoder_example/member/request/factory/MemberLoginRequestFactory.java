package io.example.spring_security.password_encoder_example.member.request.factory;

import io.example.spring_security.password_encoder_example.member.request.MemberLoginRequest;

public class MemberLoginRequestFactory {
  private Long id;
  private String password;
  public static MemberLoginRequest of(Long id, String password){
    return MemberLoginRequest.builder()
        .id(id)
        .password(password)
        .build();
  }
}

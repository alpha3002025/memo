package io.example.spring_security.password_encoder_example.member.request;

import io.example.spring_security.password_encoder_example.support.encryption.EncryptPassword;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor(staticName = "ofAll")
public class MemberLoginRequest {
  private Long id;

  @EncryptPassword
  private String password;
}

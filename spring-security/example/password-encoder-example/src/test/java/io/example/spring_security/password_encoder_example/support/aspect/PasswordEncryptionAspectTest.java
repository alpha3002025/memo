package io.example.spring_security.password_encoder_example.support.aspect;

import static org.mockito.ArgumentMatchers.any;

import io.example.spring_security.password_encoder_example.member.request.MemberLoginRequest;
import io.example.spring_security.password_encoder_example.member.request.factory.MemberLoginRequestFactory;
import io.example.spring_security.password_encoder_example.support.encryption.EncryptionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PasswordEncryptionAspectTest {
  PasswordEncryptionAspect aspect;

  @Mock
  EncryptionUtils encryptionUtils;

  @BeforeEach
  public void setup(){
    aspect = new PasswordEncryptionAspect(encryptionUtils);
  }

  @DisplayName("AOP_PASSWORD_ENCODER_TEST")
  @Test
  public void TEST_AOP_PASSWORD_ENCODER_TEST(){
    // given
    MemberLoginRequest request = MemberLoginRequestFactory.of(2L, "aaaaaaa");
    Mockito.when(encryptionUtils.encrypt(any()))
        .thenReturn("암호화문자열");

    // when
    aspect.encryptField(request);

    // then
    Assertions.assertEquals("암호화문자열", request.getPassword());

  }

}

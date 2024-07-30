package io.example.spring_security.password_encoder_example.support.encryption;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EncryptionUtils {
  private final PasswordEncoder passwordEncoder;
  public String encrypt(String password){
    return passwordEncoder.encode(password);
  }
}

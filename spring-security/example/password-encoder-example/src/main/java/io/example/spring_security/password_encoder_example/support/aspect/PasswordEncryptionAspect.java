package io.example.spring_security.password_encoder_example.support.aspect;

import io.example.spring_security.password_encoder_example.support.encryption.EncryptPassword;
import io.example.spring_security.password_encoder_example.support.encryption.EncryptionUtils;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PasswordEncryptionAspect {
  private final EncryptionUtils encryptionUtils;

  @Around("@annotation(io.example.spring_security.password_encoder_example.support.encryption.EncryptPassword)")
  public Object encryptFieldAspect(ProceedingJoinPoint pjp) throws Throwable {
    Arrays.stream(pjp.getArgs()).forEach(this::encryptField);
    return pjp.proceed();
  }

  public void encryptField(Object object) {
    if(ObjectUtils.isEmpty(object)) return;

    FieldUtils.getAllFieldsList(object.getClass())
        .stream()
        .filter(field -> !(Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers())))
        .filter(field -> field.isAnnotationPresent(EncryptPassword.class))
        .forEach(field -> {
          try{
            Object encryptionField = FieldUtils.readField(field, object, true);
            if(!(encryptionField instanceof String)) return;

            String encrypted = encryptionUtils.encrypt((String) encryptionField);
            FieldUtils.writeField(field, object, encrypted);
          }
          catch(Exception e){
            throw new RuntimeException(e);
          }
        });
  }


}

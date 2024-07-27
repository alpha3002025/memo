package io.example.spring_security.user_details_example.member.entity.factory;

import io.example.spring_security.user_details_example.member.entity.MemberEntity;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class MemberEntityFactory {
  public MemberEntity createMember(String email, String password, String authorities){
    return MemberEntity.builder()
        .email(email)
        .password(password)
        .authorities(authorities)
        .build();
  }

  public MemberEntity createMemberWithId(Long id, String email, String password, String authorities){
    return MemberEntity.builder()
        .id(id)
        .email(email)
        .password(password)
        .authorities(authorities)
        .build();
  }
}

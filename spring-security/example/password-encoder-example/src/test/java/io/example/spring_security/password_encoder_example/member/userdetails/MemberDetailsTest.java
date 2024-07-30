package io.example.spring_security.password_encoder_example.member.userdetails;

import io.example.spring_security.password_encoder_example.member.entity.MemberEntity;
import io.example.spring_security.password_encoder_example.member.fixtures.MemberEntityFixtures;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

public class MemberDetailsTest {

  @Test
  public void test___memberdetails_의_Authority_테스트(){
    // given
    List<MemberEntity> memberEntities = MemberEntityFixtures.adminMembersForCreate1();

    List<MemberDetails> memberDetailsList = memberEntities.stream()
        .map(memberEntity -> MemberDetails.fromEntityBuilder().memberEntity(memberEntity).build())
        .collect(Collectors.toList());

    // when
    List<? extends GrantedAuthority> adminUsers = memberDetailsList.stream()
        .flatMap(memberDetails -> memberDetails.getAuthorities().stream())
        .filter(grantedAuthority -> "ROLE_ADMIN".equals(grantedAuthority.getAuthority()))
        .collect(Collectors.toList());

    // then
    Assertions.assertThat(adminUsers.size()).isEqualTo(3);
  }
}

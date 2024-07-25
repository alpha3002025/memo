package io.example.spring_security.user_details_example.member.userdetails;

import io.example.spring_security.user_details_example.member.entity.MemberEntity;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class MemberDetails implements UserDetails {
  private final MemberEntity memberEntity;
  @Builder(builderClassName = "MemberEntityBuilder", builderMethodName = "entityBuilder")
  public MemberDetails(MemberEntity memberEntity){
    this.memberEntity = memberEntity;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // TODO
    return List.of(()->"ROLE_READ", () -> "ROLE_CREATE");
  }

  @Override
  public String getPassword() {
    return memberEntity.getPassword();
  }

  // email 을 변경할 수 있는 회원시스템이라고 간주
  // 이렇게 하면 UserDetailsManager 또는 UserDetailsService 에서 할일이 더 많아지긴 한다.
  @Override
  public String getUsername() {
    return String.valueOf(memberEntity.getId());
  }

  // 사용자 제한 메서드

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

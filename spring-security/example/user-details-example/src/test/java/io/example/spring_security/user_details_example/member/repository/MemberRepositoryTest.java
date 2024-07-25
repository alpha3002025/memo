package io.example.spring_security.user_details_example.member.repository;


import io.example.spring_security.user_details_example.member.MemberRepository;
import io.example.spring_security.user_details_example.member.fixtures.MemberDataset;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({"mysql-test"})
@SpringBootTest
public class MemberRepositoryTest {
  @Autowired
  private MemberDataset dataset;
  @Autowired
  private MemberRepository memberRepository;

  @BeforeEach
  public void init(){
    dataset.init();
  }

  @AfterEach
  public void destroy(){
    dataset.truncate();
  }

  @Test
  public void test__member_repository(){

  }
}

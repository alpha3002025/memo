package io.chagchagchag.example.simple_router_example.member.application.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member-service/oauth2")
public class MemberOAuth2Controller {
  @GetMapping("/hello")
  public String hello(){
    return "hello";
  }
}

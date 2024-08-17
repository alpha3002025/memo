package io.chagchagchag.example.websocket.domain;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

  @GetMapping("hello")
  public ResponseEntity<String> hello(){
    return ResponseEntity.status(HttpStatus.OK.value()).body("hello");
  }
}

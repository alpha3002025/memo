package io.chagchagchag.example.websocket.domain;

import io.chagchagchag.example.websocket.config.properties.ServerProperties;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class WebSocketController {
  private final ServerProperties serverProperties;
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final ServletWebServerApplicationContext serverApplicationContext;

  private final Random RANDOM = new Random();

  @GetMapping("hello")
  public ResponseEntity<String> hello(){
    return ResponseEntity.status(HttpStatus.OK.value()).body("hello");
  }

  @MessageMapping("/incoming")
  @SendTo("/topic/outgoing")
  public String incoming(Message message){
    int port = serverApplicationContext.getWebServer().getPort();
    log.info(String.format("메세지 수신 : %s", message));
    return String.format("서버 port = %s, 메시지 = %s", port, message.message());
  }

  @Scheduled(fixedRate = 15000L)
  public void timed(){
    try{
      Thread.sleep(RANDOM.nextInt(10) * 1000);
      log.info("스케쥴링된 메시지 전송");
      simpMessagingTemplate.convertAndSend(
          "/topic/outgoing",
          String.format("서버 port = %s, 메시지 전송완료", serverProperties.getPort())
      );
    }
    catch (Exception e){
      log.error(String.format("exception message = %s", e.getMessage()));
    }
  }

}

package io.example.feign.mvc_feign_example.external.jsonplaceholder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "ofAll")
public class PostDto {
  private Long userId;
  private String title;
  private String body;
}

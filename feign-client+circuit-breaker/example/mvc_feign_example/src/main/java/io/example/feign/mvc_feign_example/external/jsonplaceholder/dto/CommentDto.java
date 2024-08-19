package io.example.feign.mvc_feign_example.external.jsonplaceholder.dto;

import lombok.Data;

@Data
public class CommentDto {
  private Long postId;
  private Long id;
  private String email;
  private String body;
}

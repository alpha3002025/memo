package io.chagchagchag.item_moving.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(staticName = "of")
public class Request {
  private Long productIdx;
  private Integer viewOrder;
}

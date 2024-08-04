package io.chagchagchag.item_moving.item_moving_1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(staticName = "of")
public class Product {
  private Long productIdx;
  private Integer viewOrder;
}

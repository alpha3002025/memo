package io.chagchagchag.item_moving.item_moving_1;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Direction {
  UP("UP"),
  DOWN("DOWN");
  private final String alias;
}

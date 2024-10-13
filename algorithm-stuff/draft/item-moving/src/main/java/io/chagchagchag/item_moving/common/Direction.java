package io.chagchagchag.item_moving.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Direction {
  UP("UP", -1),
  DOWN("DOWN", 1);
  private final String alias;
  private final Integer vector;
}

package io.chagchagchag.item_moving.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Direction {
  UP("UP"),
  DOWN("DOWN");
  private final String alias;
}

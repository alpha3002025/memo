package io.chagchagchag.item_moving.common;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(staticName = "of")
public class MoveRequest {
  Integer moveCnt;
  Direction direction;
  Set<Request> items;
}

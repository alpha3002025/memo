package io.chagchagchag.item_moving.item_moving_1;

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

package io.chagchagchag.item_moving.common;

import java.util.List;
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
  List<Request> items;
}

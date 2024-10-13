package io.chagchagchag.item_moving.item_moving_3.fixture;

import io.chagchagchag.item_moving.common.Direction;
import io.chagchagchag.item_moving.common.MoveRequest;
import io.chagchagchag.item_moving.common.Product;
import io.chagchagchag.item_moving.common.Request;
import java.util.List;

public class TestFixtures {
  public static Product ofProduct(Long productIdx, Integer viewOrder){
    return Product.of(productIdx, viewOrder);
  }

  public static MoveRequest ofMoveRequest(Integer moveCnt, Direction direction, List<Request> items){
    return MoveRequest.builder()
        .moveCnt(moveCnt)
        .direction(direction)
        .items(items)
        .build();
  }

  public static Request ofRequestItem(Long productIdx, Integer viewOrder){
    return Request.of(productIdx, viewOrder);
  }
}

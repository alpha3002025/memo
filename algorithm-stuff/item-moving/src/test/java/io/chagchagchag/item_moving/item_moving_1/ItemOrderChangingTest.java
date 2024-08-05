package io.chagchagchag.item_moving.item_moving_1;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.chagchagchag.item_moving.common.Direction;
import io.chagchagchag.item_moving.common.MoveRequest;
import io.chagchagchag.item_moving.common.Product;
import io.chagchagchag.item_moving.common.Request;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class ItemOrderChangingTest {
  public void assertList(List<Product> result, List<Product> expected){
    for(int i=0; i<result.size(); i++){
      assertThat(result.get(i).getProductIdx()).isEqualTo(expected.get(i).getProductIdx());
    }
  }

  public static <T> Set<T> setOf(Stream<T> stream){
    return stream.collect(Collectors.toSet());
  }

  @Test
  public void TEST___아래로_이동시킬때(){
    // given
    List<Product> testList1 = Arrays.asList(
        Product.of(1L, 1),
        Product.of(2L, 2),
        Product.of(3L, 3),
        Product.of(4L, 4),
        Product.of(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        Product.of(1L, 1),
        Product.of(4L, 2),
        Product.of(5L, 3),
        Product.of(2L, 4),
        Product.of(3L, 5)
    );

    MoveRequest moveRequest1 = MoveRequest.of(2, Direction.DOWN,
        setOf(
          Stream.of(
              Request.of(2L, 2),
              Request.of(3L, 3)
          )
        )
    );

    // when
    List<Product> result1 = moveDown(testList1, moveRequest1);

    // then
    assertList(result1, expectedList1);
  }

  public List<Product> moveDown(List<Product> input, MoveRequest request){
    List<Product> copyList = new ArrayList<>(input.subList(0, input.size()));
    List<Request> requestItems = new ArrayList<>(request.getItems());
    int moveCnt = request.getMoveCnt();

    List<Integer> processed = new ArrayList<>();

    // 1. 아이템 이동
    for(Request r : requestItems){
      int copyIndex = r.getViewOrder()-1;
      copyList.set(copyIndex + moveCnt, input.get(copyIndex));
      processed.add(copyIndex);
    }

    // 2. 밀어내기
    for(int i : processed){
      copyList.set(i, input.get(i + processed.size()));
    }

    return copyList;
  }


  @Test
  public void TEST___위로_이동시킬때(){
    // given
    List<Product> testList1 = Arrays.asList(
        Product.of(1L, 1),
        Product.of(2L, 2),
        Product.of(3L, 3),
        Product.of(4L, 4),
        Product.of(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        Product.of(3L, 1),
        Product.of(4L, 2),
        Product.of(1L, 3),
        Product.of(2L, 4),
        Product.of(5L, 5)
    );

    MoveRequest moveRequest1 = MoveRequest.of(2, Direction.UP,
        setOf(
            Stream.of(
                Request.of(3L, 3),
                Request.of(4L, 4)
            )
        )
    );

    // when
    List<Product> result1 = moveUp(testList1, moveRequest1);

    // then
    assertList(result1, expectedList1);
  }

  public List<Product> moveUp(List<Product> input, MoveRequest request){
    List<Product> copyList = new ArrayList<>(input.subList(0, input.size()));
    List<Request> requestItems = new ArrayList<>(request.getItems());
    int moveCnt = request.getMoveCnt();

    List<Integer> processed = new ArrayList<>();

    // 1. 아이템 이동
    for(Request r : requestItems){
      int copyIndex = r.getViewOrder()-1;
      copyList.set(copyIndex - moveCnt, input.get(copyIndex));
      processed.add(copyIndex);
    }

    // 2. 밀어내기
    for(int i : processed){
      copyList.set(i, input.get(i - processed.size()));
    }

    return copyList;
  }
}

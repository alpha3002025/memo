package io.chagchagchag.item_moving.item_moving_2;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.chagchagchag.item_moving.common.Direction;
import io.chagchagchag.item_moving.common.MoveRequest;
import io.chagchagchag.item_moving.common.Product;
import io.chagchagchag.item_moving.common.Request;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

public class ItemOrderChangingTest2 {
  public void assertList(List<Product> result, List<Product> expected){
    for(int i=0; i<result.size(); i++){
      assertThat(result.get(i).getProductIdx()).isEqualTo(expected.get(i).getProductIdx());
    }
  }

  public static <T> Set<T> setOf(Stream<T> stream){
    return stream.collect(Collectors.toSet());
  }

  @Test
  public void TEST___위로_이동시킬때(){
    // given
    // [2,3, 5] 를 위로 1 씩 이동
    List<Product> testList1 = Arrays.asList(
        Product.of(1L, 1),
        Product.of(2L, 2),
        Product.of(3L, 3),
        Product.of(4L, 4),
        Product.of(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        Product.of(2L, 1),
        Product.of(3L, 2),
        Product.of(1L, 3),
        Product.of(5L, 4),
        Product.of(4L, 5)
    );

    MoveRequest moveRequest1 = MoveRequest.of(1, Direction.UP,
        Arrays.asList(
            Request.of(2L, 2),
            Request.of(3L, 3),
            Request.of(5L, 5)
        )
    );

    // when
    List<Product> result1 = moveUp(testList1, moveRequest1);

    // then
    assertList(result1, expectedList1);
  }

  /**
   * moveUp 의 한계점 정의
   * - 1) moveUp 은 moveCnt = 1 에 대해서만 연산을 수행한다.
   * - 2) moveCnt = 2, 3, 4 ... 는 외부에서 moveUp을 몇번 호출하는지로 결정한다.
   */
  public List<Product> moveUp(List<Product> input, MoveRequest request){
    Map<Integer, Integer> mapStartEnd = mapStartEnd(request.getItems());
    List<Product> copy = new ArrayList<>(input.subList(0, input.size()));

    for(int start : mapStartEnd.keySet()){
      int len = mapStartEnd.get(start);
      for(int offset=0; offset<len; offset++){
        // start ~ start + len
        int curr = start + offset;
        int prev = curr - 1;
        // 예외케이스 1 (상단 한계 도달, 나머지 요소들이 위로 계속해서 이동)

        // 일반 케이스
        if(curr == start + len -1){
          copy.set(start-1, input.get(start+len-1));
          continue;
        }
        copy.set(curr, input.get(prev));
      }
    }
    return null;
  }

  public Map<Integer, Integer> mapStartEnd(List<Request> requestList){
    Map<Integer, Integer> map = new HashMap<>();
    int s = requestList.get(0).getViewOrder();
    map.put(s, 1);

    for(int i=1; i<requestList.size(); i++){
      final Request request = requestList.get(i);
      int currViewOrder = request.getViewOrder();
      if(currViewOrder - s == 1){
        map.computeIfPresent(s, (k,v) -> v+1);
      }
      else{
        s = currViewOrder;
        map.put(s, 1);
      }
    }
    return map;
  }

  @Test
  public void TEST_MAP_START_END(){
    List<Request> list = Arrays.asList(
        Request.of(2L, 2),
        Request.of(3L, 3),
        Request.of(5L, 5)
    );

    Map<Integer, Integer> map = mapStartEnd(list);
    System.out.println(map);
  }

}

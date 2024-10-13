package io.chagchagchag.item_moving.item_moving_3.helper;

import io.chagchagchag.item_moving.common.Direction;
import io.chagchagchag.item_moving.common.Product;
import io.chagchagchag.item_moving.common.Request;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHelper {
  private static final Logger logger = LoggerFactory.getLogger(TestHelper.class);

  public static Map<Integer, Integer> mapStartEnd(List<Integer> viewOrderList){
    Map<Integer, Integer> map = new HashMap<>();
    int s = viewOrderList.get(0);

    map.put(s, 1);

    for(int i=1; i<viewOrderList.size(); i++){
      final Integer currViewOrder = viewOrderList.get(i);

      if(currViewOrder - s == 1){
        map.computeIfPresent(s, (k,v) -> v+1);
      }
      else{
        s = currViewOrder;
        map.put(s, 1);
      }
      s = currViewOrder;
    }
    return map;
  }

  public static List<Product> move(Direction direction, int moveCnt, List<Product> itemResult, List<Integer> viewOrderList){;
    List<Product> processedList = new ArrayList<>();
    logger.info("arrayIndexedViewOrders = {}, itemResult = {}", viewOrderList, itemResult.stream().map(p -> p.getProductIdx()).collect(
        Collectors.toList()));

    for(int i=0; i<moveCnt; i++){
      if(Direction.UP.equals(direction)){
        processedList = moveUp(itemResult, viewOrderList);
        viewOrderList = toViewOrderListUp(viewOrderList);
      }
      else {
        processedList = moveDown(itemResult, viewOrderList);
        viewOrderList = toViewOrderListDown(viewOrderList, itemResult.size()-1);
        logger.info("viewOrders = {}", viewOrderList);
      }

      itemResult = processedList;

      logger.info(">>>");
      processedList.forEach(Product -> logger.info("productIdx = {}", Product.getProductIdx()));
    }
    return processedList;
  }

  public static List<Product> moveUp(List<Product> input, List<Integer> viewOrders){
    Map<Integer, Integer> mapStartEnd = mapStartEnd(viewOrders);
    List<Product> copy = new ArrayList<>(input.subList(0, input.size()));

    for(int start : mapStartEnd.keySet()){
      if(start <= 0) continue;

      int len = mapStartEnd.get(start);
      int last = start + len - 1;
      copy.set(last, input.get(start -1));

      for(int offset=0; offset<len; offset++){
        // start ~ start + len
        int curr = start + offset; // start, start+1, start+2, ... start+len
        int prev = curr - 1; //
        copy.set(prev, input.get(curr));
      }
    }
    return copy;
  }

  // TODO
  public static List<Product> moveDown(List<Product> input, List<Integer> viewOrders){
    Map<Integer, Integer> mapStartEnd = mapStartEnd(viewOrders);
    List<Product> copy = new ArrayList<>(input.subList(0, input.size()));

    for(int start : mapStartEnd.keySet()){
//            if(start <= 0) continue;

      int len = mapStartEnd.get(start);
      int last = start + len;

      if(last >= input.size()) continue;

      copy.set(start, input.get(last));

      ////
      for(int offset=0; offset<len; offset++){
        // start ~ start + len

        int curr = start + offset; // start, start+1, start+2, ... start+len
        int next = curr + 1; //
        copy.set(next, input.get(curr));
      }
    }
    return copy;
  }

  // viewOrder 가 1 부터 시작하는 경우, 0 부터 시작하는 경우로 요건이 변경될수 있어서 함수로 분리
  public static Function<Integer, Integer> toArrayIndex = viewOrder -> viewOrder - 1;

  public static List<Integer> arrayIndexedViewOrders(List<Request> list){
    return list.stream()
        .map(r -> toArrayIndex.apply(r.getViewOrder()))
        .collect(Collectors.toList());
  }

  // 1) -1 씩 차감한 위로 한줄 이동한 viewOrder 리스트를 반환한다.
  // 2) 더 이상 이동할 수 없을 때를 찾아서 멈춘다
  public static List<Integer> toViewOrderListUp(List<Integer> list){
    if(list.isEmpty() || list.size() == 1) return list;

    List<Integer> answer = new ArrayList<>();

    // [0,1,3] -> [0,1,2]
    // [0,1,4] -> [0,1,3]
    for(int i=0; i<list.size(); i++){
      int viewOrder = list.get(i);
      if(i == viewOrder){
        answer.add(i);
      }
      else{
        answer.add(viewOrder + (1 * Direction.UP.getVector()));
      }
    }

    return answer;
  }

  /**
   * [0,3,4] -> [1,3,4]
   * [1,3,4] -> [2,3,4]
   *
   */
  // 1) +1 씩 더한 위로 한줄 이동한 viewOrder 리스트를 반환한다.
  // 2) 더 이상 이동할 수 없을 때를 찾아서 멈춘다
  public static List<Integer> toViewOrderListDown(List<Integer> viewOrders, int lastIndex){
    if(viewOrders.isEmpty()) return viewOrders;
    List<Integer> answer = new ArrayList<>();

    // let [1,2,3,4,5]
    // [0,3,4] -> [1,3,4]
    // [1,3,4] -> [2,3,4]
    // [2,3,4] -> [2,3,4]

    for(int i=0; i<viewOrders.size(); i++){
      int curr = viewOrders.get(i);
      int diff = lastIndex-curr; // 끝에서부터의 거리
      int next = curr + (1 * Direction.DOWN.getVector());

      if(next == lastIndex){
        answer.add(curr);
      }
      else{
        answer.add(next);
      }
    }

    return answer;
  }


  @Test
  public void TEST_VIEW_ORDER_DOWN_1(){
    // let [1,2,3,4,5]
    // [0,3,4] -> [1,3,4]
    // [1,3,4] -> [2,3,4]
    // [2,3,4] -> [2,3,4]

    logger.info("{}, expected = {}", toViewOrderListDown(Arrays.asList(0,3,4),4), Arrays.asList(1,3,4));
    logger.info("{}, expected = {}", toViewOrderListDown(Arrays.asList(1,3,4),4), Arrays.asList(2,3,4));
    logger.info("{}, expected = {}", toViewOrderListDown(Arrays.asList(2,3,4),4), Arrays.asList(2,3,4));
    logger.info("{}, expected = {}", toViewOrderListDown(Arrays.asList(0,2,3),4), Arrays.asList(1,3,4));
  }
}

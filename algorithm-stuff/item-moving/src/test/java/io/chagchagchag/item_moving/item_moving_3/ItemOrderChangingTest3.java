package io.chagchagchag.item_moving.item_moving_3;


import static io.chagchagchag.item_moving.item_moving_3.fixture.TestFixtures.ofMoveRequest;
import static io.chagchagchag.item_moving.item_moving_3.fixture.TestFixtures.ofProduct;
import static io.chagchagchag.item_moving.item_moving_3.fixture.TestFixtures.ofRequestItem;
import static io.chagchagchag.item_moving.item_moving_3.helper.TestHelper.arrayIndexedViewOrders;
import static io.chagchagchag.item_moving.item_moving_3.helper.TestHelper.mapStartEnd;
import static io.chagchagchag.item_moving.item_moving_3.helper.TestHelper.move;
import static io.chagchagchag.item_moving.item_moving_3.helper.TestHelper.toViewOrderListUp;
import static org.assertj.core.api.Assertions.assertThat;

import io.chagchagchag.item_moving.common.Direction;
import io.chagchagchag.item_moving.common.MoveRequest;
import io.chagchagchag.item_moving.common.Product;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemOrderChangingTest3 {

  private static final Logger logger = LoggerFactory.getLogger(ItemOrderChangingTest3.class);

  public void assertList(List<Product> result, List<Product> expected){
    for(int i=0; i<result.size(); i++){
      assertThat(result.get(i).getProductIdx()).isEqualTo(expected.get(i).getProductIdx());
    }
  }

  public void assertIntList(List<Integer> test, List<Integer> expected){
    for(int i=0; i<test.size(); i++){
      assertThat(test.get(i)).isEqualTo(expected.get(i));
    }
  }

  /**
   * 테스트 유형 (1) // (Type 1) (moveCnt = 1,2,3, ... n)
   * [1,2,3,4,5] 에서 [1,3,4] 를 움직이는 경우
   * */
  @Test
  public void TEST_TYPE_1__1_MOVE_DOWN_1회_이동(){
    // given
    List<Product> testList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(3L, 3),
        ofProduct(4L, 4),
        ofProduct(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        ofProduct(2L, 2),
        ofProduct(1L, 1),
        ofProduct(5L, 5),
        ofProduct(3L, 3),
        ofProduct(4L, 4)
    );

    MoveRequest moveRequest1 = ofMoveRequest(
        1, Direction.DOWN,
        Arrays.asList(
            ofRequestItem(1L, 1),
            ofRequestItem(3L, 3),
            ofRequestItem(4L, 4)
        )
    );

    // when
    // viewOrderList 들을 [3,4], [2,3] 으로 만들어내야 한다.
    // moveCnt(=2) 만큼 돌릴때 [3,4],moveCnt=2 인 요청을 [3,4],[2,3] 으로 for 문 내에서 변환
    int moveCnt = moveRequest1.getMoveCnt();
    List<Integer> viewOrderList = arrayIndexedViewOrders(moveRequest1.getItems());
    List<Product> processedList = move(Direction.DOWN, moveCnt, testList1, viewOrderList);

//        // then
    assertList(processedList, expectedList1);
  }

  /**
   * 테스트 유형 (1) // (Type 1) (moveCnt = 1,2,3, ... n)
   * [1,2,3,4,5] 에서 [1,3,4] 를 움직이는 경우
   * */
  @Test
  public void TEST_TYPE_1__2_MOVE_DOWN_2회_이동() {
    // given
    List<Product> testList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(3L, 3),
        ofProduct(4L, 4),
        ofProduct(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        ofProduct(2L, 2),
        ofProduct(5L, 5),
        ofProduct(1L, 1),
        ofProduct(3L, 3),
        ofProduct(4L, 4)
    );

    MoveRequest moveRequest1 = ofMoveRequest(
        2, Direction.DOWN,
        Arrays.asList(
            ofRequestItem(1L, 1),
            ofRequestItem(3L, 3),
            ofRequestItem(4L, 4)
        )
    );

    // when
    // viewOrderList 들을 [3,4], [2,3] 으로 만들어내야 한다.
    // moveCnt(=2) 만큼 돌릴때 [3,4],moveCnt=2 인 요청을 [3,4],[2,3] 으로 for 문 내에서 변환
    int moveCnt = moveRequest1.getMoveCnt();
    List<Integer> viewOrderList = arrayIndexedViewOrders(moveRequest1.getItems());

    // then
    List<Product> processedList = move(Direction.DOWN, moveCnt, testList1, viewOrderList);
    assertList(processedList, expectedList1);
  }

  /**
   * 테스트 유형 (1) // (Type 1) (moveCnt = 1,2,3, ... n)
   * [1,2,3,4,5] 에서 [1,3,4] 를 움직이는 경우
   * */
  @Test
  public void TEST_TYPE_1__3_MOVE_DOWN_3회_이동() {
    // given
    List<Product> testList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(3L, 3),
        ofProduct(4L, 4),
        ofProduct(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        ofProduct(2L, 2),
        ofProduct(5L, 5),
        ofProduct(1L, 1),
        ofProduct(3L, 3),
        ofProduct(4L, 4)
    );

    MoveRequest moveRequest1 = ofMoveRequest(
        3, Direction.DOWN,
        Arrays.asList(
            ofRequestItem(1L, 1),
            ofRequestItem(3L, 3),
            ofRequestItem(4L, 4)
        )
    );

    // when
    // viewOrderList 들을 [3,4], [2,3] 으로 만들어내야 한다.
    // moveCnt(=2) 만큼 돌릴때 [3,4],moveCnt=2 인 요청을 [3,4],[2,3] 으로 for 문 내에서 변환
    int moveCnt = moveRequest1.getMoveCnt();
    List<Integer> viewOrderList = arrayIndexedViewOrders(moveRequest1.getItems());
    List<Product> processedList = move(Direction.DOWN, moveCnt, testList1, viewOrderList);

    // then
    assertList(processedList, expectedList1);
  }

  /**
   * 테스트 유형 (1) // (Type 1) (moveCnt = 1,2,3, ... n)
   * [1,2,3,4,5] 에서 [1,3,4] 를 움직이는 경우
   * */
  @Test
  public void TEST_TYPE_1__4_MOVE_DOWN_4회_이동() {
    // given
    List<Product> testList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(3L, 3),
        ofProduct(4L, 4),
        ofProduct(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        ofProduct(2L, 2),
        ofProduct(5L, 5),
        ofProduct(1L, 1),
        ofProduct(3L, 3),
        ofProduct(4L, 4)
    );

    MoveRequest moveRequest1 = ofMoveRequest(
        4, Direction.DOWN,
        Arrays.asList(
            ofRequestItem(1L, 1),
            ofRequestItem(3L, 3),
            ofRequestItem(4L, 4)
        )
    );

    // when
    // viewOrderList 들을 [3,4], [2,3] 으로 만들어내야 한다.
    // moveCnt(=2) 만큼 돌릴때 [3,4],moveCnt=2 인 요청을 [3,4],[2,3] 으로 for 문 내에서 변환
    int moveCnt = moveRequest1.getMoveCnt();
    List<Integer> viewOrderList = arrayIndexedViewOrders(moveRequest1.getItems());
    List<Product> processedList = move(Direction.DOWN, moveCnt, testList1, viewOrderList);

    // then
    assertList(processedList, expectedList1);
  }

  /**
   * 테스트 유형 (2) // (Type 2) (moveCnt = 1,2,3, ... n)
   * [1,2,3,4,5] 에서 [3,4] 을 움직이는 경우
   */
  @Test
  public void TEST_TYPE_2__1_MOVE_DOWN_1회_이동(){
    // given
    List<Product> testList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(3L, 3),
        ofProduct(4L, 4),
        ofProduct(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(5L, 5),
        ofProduct(3L, 3),
        ofProduct(4L, 4)
    );

    MoveRequest moveRequest1 = ofMoveRequest(
        1, Direction.DOWN,
        Arrays.asList(
            ofRequestItem(3L, 3),
            ofRequestItem(4L, 4)
        )

    );

    // when
    List<Integer> viewOrderList = arrayIndexedViewOrders(moveRequest1.getItems());
    int moveCnt = moveRequest1.getMoveCnt();
    List<Product> processedList = move(Direction.DOWN, moveCnt, testList1, viewOrderList);

    // then
    assertList(processedList, expectedList1);
  }

  /**
   * 테스트 유형 (2) // (Type 2) (moveCnt = 1,2,3, ... n)
   * [1,2,3,4,5] 에서 [3,4] 을 움직이는 경우
   */
  @Test
  public void TEST_TYPE_2__2_MOVE_DOWN_2회_이동(){
    // given
    List<Product> testList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(3L, 3),
        ofProduct(4L, 4),
        ofProduct(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(5L, 5),
        ofProduct(3L, 3),
        ofProduct(4L, 4)
    );

    MoveRequest moveRequest1 = ofMoveRequest(
        2, Direction.DOWN,
        Arrays.asList(
            ofRequestItem(3L, 3),
            ofRequestItem(4L, 4)
        )

    );

    // when
    List<Integer> viewOrderList = arrayIndexedViewOrders(moveRequest1.getItems());
    int moveCnt = moveRequest1.getMoveCnt();
    List<Product> processedList = move(Direction.DOWN, moveCnt, testList1, viewOrderList);

    // then
    assertList(processedList, expectedList1);
  }

  /**
   * 테스트 유형 (2) // (Type 2) (moveCnt = 1,2,3, ... n)
   * [1,2,3,4,5] 에서 [3,4] 을 움직이는 경우
   */
  @Test
  public void TEST_TYPE_2__3_MOVE_DOWN_3회_이동(){
    // given
    List<Product> testList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(3L, 3),
        ofProduct(4L, 4),
        ofProduct(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(5L, 5),
        ofProduct(3L, 3),
        ofProduct(4L, 4)
    );

    MoveRequest moveRequest1 = ofMoveRequest(
        3, Direction.DOWN,
        Arrays.asList(
            ofRequestItem(3L, 3),
            ofRequestItem(4L, 4)
        )

    );

    // when
    List<Integer> viewOrderList = arrayIndexedViewOrders(moveRequest1.getItems());
    int moveCnt = moveRequest1.getMoveCnt();
    List<Product> processedList = move(Direction.DOWN, moveCnt, testList1, viewOrderList);

    // then
    assertList(processedList, expectedList1);
  }

  /**
   * 테스트 유형 (2) // (Type 2) (moveCnt = 1,2,3, ... n)
   * [1,2,3,4,5] 에서 [3,4] 을 움직이는 경우
   */
  @Test
  public void TEST_TYPE_2__4_MOVE_DOWN_4회_이동(){
    // given
    List<Product> testList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(3L, 3),
        ofProduct(4L, 4),
        ofProduct(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(5L, 5),
        ofProduct(3L, 3),
        ofProduct(4L, 4)
    );

    MoveRequest moveRequest1 = ofMoveRequest(
        4, Direction.DOWN,
        Arrays.asList(
            ofRequestItem(3L, 3),
            ofRequestItem(4L, 4)
        )

    );

    // when
    List<Integer> viewOrderList = arrayIndexedViewOrders(moveRequest1.getItems());
    int moveCnt = moveRequest1.getMoveCnt();
    List<Product> processedList = move(Direction.DOWN, moveCnt, testList1, viewOrderList);

    // then
    assertList(processedList, expectedList1);
  }

  /**
   * 테스트 유형 (3) // (Type 3) (moveCnt = 1,2,3, ... n)
   * [1,2,3,4,5] 에서 [1,2,4] 를 움직이는 경우
   */
  @Test
  public void TEST_TYPE_3__1_MOVE_DOWN_1회_이동(){
    // given
    List<Product> testList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(3L, 3),
        ofProduct(4L, 4),
        ofProduct(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        ofProduct(3L, 3),
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(5L, 5),
        ofProduct(4L, 4)
    );

    MoveRequest moveRequest1 = ofMoveRequest(
        1, Direction.DOWN,
        Arrays.asList(
            ofRequestItem(1L, 1),
            ofRequestItem(2L, 2),
            ofRequestItem(4L, 4)
        )

    );

    // when
    // viewOrderList 들을 [3,4], [2,3] 으로 만들어내야 한다.
    // moveCnt(=2) 만큼 돌릴때 [3,4],moveCnt=2 인 요청을 [3,4],[2,3] 으로 for 문 내에서 변환
    int moveCnt = moveRequest1.getMoveCnt();
    List<Integer> viewOrderList = arrayIndexedViewOrders(moveRequest1.getItems());
    List<Product> processedList = move(Direction.DOWN, moveCnt, testList1, viewOrderList);

    // then
    assertList(processedList, expectedList1);
  }

  /**
   * 테스트 유형 (3) // (Type 3) (moveCnt = 1,2,3, ... n)
   * [1,2,3,4,5] 에서 [1,2,4] 를 움직이는 경우
   */
  @Test
  public void TEST_TYPE_3__2_MOVE_DOWN_2회_이동(){
    // given
    List<Product> testList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(3L, 3),
        ofProduct(4L, 4),
        ofProduct(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        ofProduct(3L, 3),
        ofProduct(5L, 5),
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(4L, 4)
    );

    MoveRequest moveRequest1 = ofMoveRequest(
        2, Direction.DOWN,
        Arrays.asList(
            ofRequestItem(1L, 1),
            ofRequestItem(2L, 2),
            ofRequestItem(4L, 4)
        )

    );

    // when
    // viewOrderList 들을 [3,4], [2,3] 으로 만들어내야 한다.
    // moveCnt(=2) 만큼 돌릴때 [3,4],moveCnt=2 인 요청을 [3,4],[2,3] 으로 for 문 내에서 변환
    int moveCnt = moveRequest1.getMoveCnt();
    List<Integer> viewOrderList = arrayIndexedViewOrders(moveRequest1.getItems());
    List<Product> processedList = move(Direction.DOWN, moveCnt, testList1, viewOrderList);

    // then
    assertList(processedList, expectedList1);
  }



  /**
   * 테스트 유형 (3) // (Type 3) (moveCnt = 1,2,3, ... n)
   * [1,2,3,4,5] 에서 [1,2,4] 를 움직이는 경우
   */
  @Test
  public void TEST_TYPE_3__3_MOVE_DOWN_3회_이동(){
    // given
    List<Product> testList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(3L, 3),
        ofProduct(4L, 4),
        ofProduct(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        ofProduct(3L, 3),
        ofProduct(5L, 5),
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(4L, 4)
    );

    MoveRequest moveRequest1 = ofMoveRequest(
        3, Direction.DOWN,
        Arrays.asList(
            ofRequestItem(1L, 1),
            ofRequestItem(2L, 2),
            ofRequestItem(4L, 4)
        )

    );

    // when
    // viewOrderList 들을 [3,4], [2,3] 으로 만들어내야 한다.
    // moveCnt(=2) 만큼 돌릴때 [3,4],moveCnt=2 인 요청을 [3,4],[2,3] 으로 for 문 내에서 변환
    int moveCnt = moveRequest1.getMoveCnt();
    List<Integer> viewOrderList = arrayIndexedViewOrders(moveRequest1.getItems());
    List<Product> processedList = move(Direction.DOWN, moveCnt, testList1, viewOrderList);

    // then
    assertList(processedList, expectedList1);
  }

  /**
   * 테스트 유형 (3) // (Type 3) (moveCnt = 1,2,3, ... n)
   * [1,2,3,4,5] 에서 [1,2,4] 를 움직이는 경우
   */
  @Test
  public void TEST_TYPE_3__4_MOVE_DOWN_4회_이동(){
    // given
    List<Product> testList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(3L, 3),
        ofProduct(4L, 4),
        ofProduct(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        ofProduct(3L, 3),
        ofProduct(5L, 5),
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(4L, 4)
    );

    MoveRequest moveRequest1 = ofMoveRequest(
        4, Direction.DOWN,
        Arrays.asList(
            ofRequestItem(1L, 1),
            ofRequestItem(2L, 2),
            ofRequestItem(4L, 4)
        )

    );

    // when
    // viewOrderList 들을 [3,4], [2,3] 으로 만들어내야 한다.
    // moveCnt(=2) 만큼 돌릴때 [3,4],moveCnt=2 인 요청을 [3,4],[2,3] 으로 for 문 내에서 변환
    int moveCnt = moveRequest1.getMoveCnt();
    List<Integer> viewOrderList = arrayIndexedViewOrders(moveRequest1.getItems());
    List<Product> processedList = move(Direction.DOWN, moveCnt, testList1, viewOrderList);

    // then
    assertList(processedList, expectedList1);
  }

  /**
   * 테스트 유형 (3) // (Type 3) (moveCnt = 1,2,3, ... n)
   * [1,2,3,4,5] 에서 [1,2,4] 를 움직이는 경우
   */
  @Test
  public void TEST_TYPE_3__5_MOVE_DOWN_5회_이동(){
    // given
    List<Product> testList1 = Arrays.asList(
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(3L, 3),
        ofProduct(4L, 4),
        ofProduct(5L, 5)
    );

    List<Product> expectedList1 = Arrays.asList(
        ofProduct(3L, 3),
        ofProduct(5L, 5),
        ofProduct(1L, 1),
        ofProduct(2L, 2),
        ofProduct(4L, 4)
    );

    MoveRequest moveRequest1 = ofMoveRequest(
        5, Direction.DOWN,
        Arrays.asList(
            ofRequestItem(1L, 1),
            ofRequestItem(2L, 2),
            ofRequestItem(4L, 4)
        )

    );

    // when
    // viewOrderList 들을 [3,4], [2,3] 으로 만들어내야 한다.
    // moveCnt(=2) 만큼 돌릴때 [3,4],moveCnt=2 인 요청을 [3,4],[2,3] 으로 for 문 내에서 변환
    int moveCnt = moveRequest1.getMoveCnt();
    List<Integer> viewOrderList = arrayIndexedViewOrders(moveRequest1.getItems());
    List<Product> processedList = move(Direction.DOWN, moveCnt, testList1, viewOrderList);

    // then
    assertList(processedList, expectedList1);
  }



  @Test
  public void TEST_VIEW_ORDER_LIST(){
    List<Integer> viewOrderList1 = toViewOrderListUp(Arrays.asList(2,4,5));
    assertIntList(viewOrderList1, Arrays.asList(1,3,4));

    List<Integer> viewOrderList11 = toViewOrderListUp(Arrays.asList(1,3,4));
    assertIntList(viewOrderList11, Arrays.asList(0,2,3));

    List<Integer> viewOrderList12 = toViewOrderListUp(Arrays.asList(0,2,3));
    assertIntList(viewOrderList12, Arrays.asList(0,1,2));

    List<Integer> viewOrderList13 = toViewOrderListUp(Arrays.asList(0,1,2));
    assertIntList(viewOrderList13, Arrays.asList(0,1,2));
  }

  @Test
  public void TEST_MAP_START_END1(){
    List<Integer> list = Arrays.asList(0, 1, 2);

    Map<Integer, Integer> map = mapStartEnd(list);
    logger.info("map = {}",map);
  }

  @Test
  public void TEST_MAP_START_END(){
    MoveRequest moveRequest1 = ofMoveRequest(
        2, Direction.UP,
        Arrays.asList(
            ofRequestItem(3L, 3),
            ofRequestItem(4L, 4)
        )

    );

    List<Integer> viewOrderList = arrayIndexedViewOrders(moveRequest1.getItems());

    Map<Integer, Integer> map = mapStartEnd(viewOrderList);

    assertThat(map.get(2)).isEqualTo(2);
  }

}

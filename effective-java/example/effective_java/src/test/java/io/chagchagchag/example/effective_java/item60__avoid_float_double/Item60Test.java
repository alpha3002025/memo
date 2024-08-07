package io.chagchagchag.example.effective_java.item60__avoid_float_double;

import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Item60Test {
  @Test
  public void 간단한_뺄셈연산(){
    Assertions.assertNotEquals(1.03 - 0.42, 0.61);
    Assertions.assertNotEquals(1.00 - 9 * 0.10, 0.1);
  }

  @Test
  public void 달러_센트_연산(){
    double dollar = 1.00;
    int itemCnt = 0;
    for(double price = 0.10; dollar >= price; price += 0.10){
      dollar -= price;
      itemCnt++;
    }

    Assertions.assertNotEquals(itemCnt, 4);
    Assertions.assertNotEquals(dollar, 0);
    System.out.println("잔돈 = " + dollar);
    System.out.println("구입한 개수 = " + itemCnt);
  }

  @Test
  public void 달러_센트_연산을_BigDecimal로_계산(){
    final BigDecimal TEN_CENTS = BigDecimal.valueOf(0.10);

    int itemCnt = 0;
    BigDecimal dollar = BigDecimal.valueOf(1.00);
    for(BigDecimal price = TEN_CENTS; dollar.compareTo(price) >= 0; price = price.add(TEN_CENTS)){
      dollar = dollar.subtract(price);
      itemCnt++;
    }

    Assertions.assertEquals(itemCnt, 4);
    Assertions.assertTrue(dollar.equals(BigDecimal.valueOf(0.00)));
    System.out.println("잔돈 = " + dollar);
    System.out.println("구입한 개수 = " + itemCnt);
  }

  @Test
  public void 정수_단위로_환산해서_계산(){
    int itemCnt = 0;
    int dollar = 100;
    for(int cent = 10; dollar >= cent; cent+=10){
      dollar -= cent;
      itemCnt++;
    }

    Assertions.assertEquals(itemCnt, 4);
    Assertions.assertTrue(dollar == 0);
    System.out.println("잔돈 = " + dollar);
    System.out.println("구입한 개수 = " + itemCnt);
  }
}

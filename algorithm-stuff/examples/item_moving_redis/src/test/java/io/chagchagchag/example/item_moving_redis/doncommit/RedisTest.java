package io.chagchagchag.example.item_moving_redis.doncommit;

import io.chagchagchag.example.item_moving_redis.product.entity.Product;
import io.chagchagchag.example.item_moving_redis.product.entity.factory.ProductFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate<String, Object> polymorphicRedisTemplate;

    final String key = "test-product-ranking";

    @Test
    public void test___add_all_data(){
        int cnt = 0;
        for(int i=(int)'A'; i<(int)'z'; i++){
            if(Character.isAlphabetic(i)){
                final int viewOrder = cnt;
                Product product = ProductFactory.newProduct(Character.toString((char) i), () -> viewOrder);
                polymorphicRedisTemplate.opsForZSet().add(key, product, cnt);
                cnt++;
            }
        }
    }

    @Test
    public void test___range(){
        Set<Object> range = polymorphicRedisTemplate.opsForZSet()
                .range(key, 0, 0);

        // 0 에서부터 읽어들이며, score 역시 0 인 곳 부터 읽어들이게 된다.
        range.forEach(v -> {
            Product p = (Product) v;
            System.out.println(p.getTitle() + ", " + p.getViewOrder());
        });
    }

    @Test
    public void test___reverseRange(){
        Set<Object> reverseTop = polymorphicRedisTemplate.opsForZSet()
                .reverseRange(key, 0, 0);

        reverseTop.forEach(v -> {
           Product p = (Product) v;
            System.out.println(p.getTitle() + ", " + p.getViewOrder());
        });
    }

    @Test
    public void test___size(){
        Long size = polymorphicRedisTemplate.opsForZSet().size(key);
        System.out.println(size);
        // sortedSet 의 사이즈를 구하는 명령어는 zcard (https://redis.io/docs/latest/commands/zcard/) 이다.
    }

    @Test
    public void test___paging(){
        int limit = 20;
        Long totalSize = polymorphicRedisTemplate.opsForZSet().size(key);

        int pageCnt = (int) (totalSize/limit);
        int remain = (int) (totalSize % limit);
        if(remain > 0){
            pageCnt++;
        }

        System.out.println("totalSize = " + totalSize + ", pageCnt = " + pageCnt + ", remain = " + remain + ", limit = " + limit);

        for(int pageNo=0; pageNo<pageCnt; pageNo++){
            int end;
            if(remain > 0 && pageNo == pageCnt-1){
                end = limit * pageNo + remain;
            }
            else{
                end = limit * pageNo + limit;
            }

            final int curr = pageNo;
            polymorphicRedisTemplate.opsForZSet()
                    .range(key, limit * pageNo, end)
                    .stream()
                    .forEach(v -> {
                        Product p = (Product) v;
                        System.out.println("page = " + curr + ", " + p.getTitle() + ", " + p.getViewOrder());
                    });
        }
    }

    @Test
    public void test___paging_with_item_moving(){

    }
}

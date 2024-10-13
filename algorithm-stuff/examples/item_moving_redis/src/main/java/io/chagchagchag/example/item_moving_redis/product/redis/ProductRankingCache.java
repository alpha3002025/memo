package io.chagchagchag.example.item_moving_redis.product.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRankingCache {
    private Long idx;
    private String title; // 테스트를 위해 추가한 필드
}

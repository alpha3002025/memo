package io.chagchagchag.example.item_moving_redis.book.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRankingCache {
    private Long id;
    private Long ranking;
}

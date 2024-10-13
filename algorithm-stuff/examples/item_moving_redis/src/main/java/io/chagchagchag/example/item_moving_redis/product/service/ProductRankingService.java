package io.chagchagchag.example.item_moving_redis.product.service;

import io.chagchagchag.example.item_moving_redis.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductRankingService {
    @Qualifier("polymorphicRedisTemplate")
    private final RedisTemplate<String, Object> polymorphicRedisTemplate;

    private static final String key = "product-ranking";

    public void add(Product product) {
//        polymorphicRedisTemplate.opsForZSet()
//                .add(key, product, top(1))
    }

    public void top(int offset){
//        return polymorphicRedisTemplate.opsForZSet()
//                .reverseRange(key, offset, offset+1)

    }
}

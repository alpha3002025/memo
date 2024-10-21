package io.chagchagchag.example.item_moving_redis_simple.cat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatCreateRedisRequest {
    private Long id;
    private String name;
}

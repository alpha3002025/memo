package io.chagchagchag.example.item_moving_redis_simple.cat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CatMovingRequest {
    private List<Cat> items;
    private Direction direction;
    int cnt;
}

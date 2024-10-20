package io.chagchagchag.example.item_moving_redis_simple.cat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CatResultCode {
    SUCCESS,
    FAIL,
    CAT_TRY_AGAIN
}

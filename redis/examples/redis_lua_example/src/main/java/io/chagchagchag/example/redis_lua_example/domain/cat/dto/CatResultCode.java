package io.chagchagchag.example.redis_lua_example.domain.cat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CatResultCode {
    DUPLICATED_ENQUEUE_REQUEST,
    SUCCESS,
    CAT_TRY_AGAIN,
    FAIL
}

package io.chagchagchag.example.redis_lua_example.domain.cat.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.chagchagchag.example.redis_lua_example.domain.cat.dto.CatResultCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatRedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisScript<String> catLuaScript;

    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper 로 어려운걸 할 게 없기에 간단하게 설정함
    private final String CACHE_SET_KEY = "cat-set";
    private final String CACHE_LIST_KEY = "cat-list";
    private final Long MAX_QUANTITY = 10L;

    public CatResultCode addCatOne(Long catId, String catName){
        try {
            String response = redisTemplate.execute(
                    catLuaScript,
                    List.of(CACHE_SET_KEY, CACHE_LIST_KEY),
                    catId == null ? "-1" : String.valueOf(catId),
                    catName == null ? "NO_NAME" : catName,
                    String.valueOf(MAX_QUANTITY),
                    objectMapper.writeValueAsString("some_payload")
            );

            CatResultCode result = CatResultCode.valueOf(response);
            log.info("result >>> {}", result);

            return result;
        } catch (JsonProcessingException e) {
            return CatResultCode.FAIL;
        }
    }

}

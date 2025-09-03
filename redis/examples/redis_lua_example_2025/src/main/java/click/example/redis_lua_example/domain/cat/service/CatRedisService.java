package click.example.redis_lua_example.domain.cat.service;

import click.example.redis_lua_example.domain.cat.dto.AddCatRequest;
import click.example.redis_lua_example.domain.cat.dto.CatResultCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatRedisService {
    private final RedisTemplate<String, String> redisTemplate;
    @Qualifier("postActivityRedisScript")
    private final RedisScript<String> catLuaScript;
    @Qualifier("jsonAddCatList")
    private final RedisScript<String> catJsonScript;

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

    // 단순히 요청 하나를 추가하는 연산 (대기열x, history 성 요청)
    public String addCatJsonOne(AddCatRequest request) {
        try {
            String response = redisTemplate.execute(
                    catJsonScript,
                    List.of("CAT_JSON_LIST"),
                    objectMapper.writeValueAsString(request)
            );

            CatResultCode result = CatResultCode.valueOf(response);
            log.info("result >>> {}", result);

            return result.name();
        } catch (JsonProcessingException e) {
            return CatResultCode.FAIL.name();
        }
    }
}

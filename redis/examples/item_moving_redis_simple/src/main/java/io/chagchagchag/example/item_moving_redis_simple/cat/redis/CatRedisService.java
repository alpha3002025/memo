package io.chagchagchag.example.item_moving_redis_simple.cat.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.chagchagchag.example.item_moving_redis_simple.cat.dto.Cat;
import io.chagchagchag.example.item_moving_redis_simple.cat.dto.CatResultCode;
import io.chagchagchag.example.item_moving_redis_simple.cat.dto.Direction;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatRedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedisScript<List<String>> catMovingEndScript;
    private final RedisScript<String> catMovingOneStepScript;

    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper 로 어려운걸 할 게 없기에 간단하게 설정함
    private final String CACHE_CAT_LIST_KEY = "cat-list";
    private final String CACHE_MEMO_LIST_KEY = "memo-list";

    public List<String> moveCatsToSide(List<Cat> requestItems, Direction direction) {
        List<Long> idList = requestItems.stream().map(cat -> cat.getId()).collect(Collectors.toList());
        final int requestSize = idList.size();

        // moving
        List<String> result = redisTemplate.execute(
                catMovingEndScript,
                List.of(CACHE_CAT_LIST_KEY, CACHE_MEMO_LIST_KEY),
                direction.name(), idList.toArray(new Object[requestSize])
        );

        return result;
    }

    public CatResultCode movingCatOneStep(List<Cat> requestItems, Direction direction, int cnt){
        // cnt 한번씩 요청을 보내면 좋겠지만 ㄷㄷㄷ cnt가 10개 이상이 오면 해킹 수준인 코드
        // 개발기한이 짧아서 뭘 어떻게 할 수 없을 때 에만 용납되는 코드
        for(int step = 0; step < cnt; step++){
            for(int i=0; i<requestItems.size(); i++){
                redisTemplate.execute(
                    catMovingOneStepScript,
                    List.of(CACHE_CAT_LIST_KEY),
                    direction.name(), requestItems.get(i).getId()
                );
            }
        }

        return CatResultCode.SUCCESS;
    }
}

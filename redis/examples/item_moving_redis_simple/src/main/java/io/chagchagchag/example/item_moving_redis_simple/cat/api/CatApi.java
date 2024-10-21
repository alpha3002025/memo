package io.chagchagchag.example.item_moving_redis_simple.cat.api;

import io.chagchagchag.example.item_moving_redis_simple.cat.dto.CatMovingRequest;
import io.chagchagchag.example.item_moving_redis_simple.cat.redis.CatRedisService;
import io.chagchagchag.example.item_moving_redis_simple.cat.service.CatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cat")
@RequiredArgsConstructor
@RestController
public class CatApi {
    private final CatRedisService catRedisService;

    @PostMapping("")
    public ResponseEntity<String> createOne(){
        return ResponseEntity.status(HttpStatus.CREATED).body("SUCCESS");
    }

    @PostMapping("/move-one-step")
    public ResponseEntity<String> moveOneStep(@RequestBody CatMovingRequest catMovingRequest){
        catRedisService.movingCatOneStep(catMovingRequest.getItems(), catMovingRequest.getDirection(), catMovingRequest.getCnt());
        return ResponseEntity.status(HttpStatus.OK).body("SUCCESS");
    }

}

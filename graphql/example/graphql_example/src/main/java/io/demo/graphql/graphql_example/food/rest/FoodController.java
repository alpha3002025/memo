package io.demo.graphql.graphql_example.food.rest;

import io.demo.graphql.graphql_example.food.application.FoodService;
import io.demo.graphql.graphql_example.food.entity.FoodEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class FoodController {

    private final FoodService foodService;

    @MutationMapping
    public FoodEntity save(@Argument String name){
        return foodService.save(name);
    }

    @QueryMapping
    public FoodEntity getFood(@Argument String name){
        return foodService.getFood(name);
    }

    /**
     * @QueryMapping 은 @GetMapping 과 같은 역할을 수행
     * @return
     */
    @QueryMapping
    public List<FoodEntity> getFoods(){
        return foodService.getFoods();
    }
}

package io.demo.graphql.graphql_example.food.application;

import io.demo.graphql.graphql_example.food.entity.FoodEntity;
import io.demo.graphql.graphql_example.food.entity.factory.FoodEntityFactory;
import io.demo.graphql.graphql_example.food.entity.repository.FoodRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final FoodEntityFactory foodEntityFactory;

    @Transactional
    public FoodEntity save(String name){
        return foodRepository.save(foodEntityFactory.newFood(name));
    }

    @Transactional(readOnly = true)
    public FoodEntity getFood(String name){
        return foodRepository
                .findByName(name)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<FoodEntity> getFoods(){
        return foodRepository.findAll();
    }
}

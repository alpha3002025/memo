package io.demo.graphql.graphql_example.food.entity.factory;

import io.demo.graphql.graphql_example.food.entity.FoodEntity;
import org.springframework.stereotype.Component;

@Component
public class FoodEntityFactory {

  public FoodEntity newFood(String name){
    return FoodEntity.ofAll(null, name);
  }
}

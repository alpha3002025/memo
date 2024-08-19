package io.demo.graphql.graphql_example.food.entity.repository;

import io.demo.graphql.graphql_example.food.entity.FoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.Optional;

@GraphQlRepository
public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
    Optional<FoodEntity> findByName(String name);
}

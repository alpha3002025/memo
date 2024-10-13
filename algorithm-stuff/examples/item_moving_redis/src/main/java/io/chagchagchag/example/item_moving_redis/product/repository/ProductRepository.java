package io.chagchagchag.example.item_moving_redis.product.repository;

import io.chagchagchag.example.item_moving_redis.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

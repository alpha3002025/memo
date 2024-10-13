package io.chagchagchag.example.item_moving_redis.book.repository;

import io.chagchagchag.example.item_moving_redis.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

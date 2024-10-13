package io.chagchagchag.example.spring_data_redis_example.book.repository;

import io.chagchagchag.example.spring_data_redis_example.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

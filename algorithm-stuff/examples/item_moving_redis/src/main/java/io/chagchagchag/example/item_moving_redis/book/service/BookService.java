package io.chagchagchag.example.item_moving_redis.book.service;

import io.chagchagchag.example.item_moving_redis.book.entity.Book;
import io.chagchagchag.example.item_moving_redis.book.redis.BookCache;
import io.chagchagchag.example.item_moving_redis.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @Cacheable(cacheNames = {"book-cache"}, key = "'book:' + #id")
    public BookCache findBookById(Long id) {
        Book book = bookRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 책 입니다."));

        return BookCache.builder()
                .id(book.getId())
                .title(book.getTitle())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }
}

package io.chagchagchag.example.spring_data_redis_example.book.api;

import io.chagchagchag.example.spring_data_redis_example.book.redis.BookCache;
import io.chagchagchag.example.spring_data_redis_example.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookController{
    private final BookService bookService;

    @GetMapping("/books/{id}")
    public BookCache getBook(@PathVariable Long id) {
        return bookService.findBookById(id);
    }
}

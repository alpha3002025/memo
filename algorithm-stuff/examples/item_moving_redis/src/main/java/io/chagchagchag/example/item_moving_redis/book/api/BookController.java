package io.chagchagchag.example.item_moving_redis.book.api;

import io.chagchagchag.example.item_moving_redis.book.redis.BookCache;
import io.chagchagchag.example.item_moving_redis.book.service.BookService;
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

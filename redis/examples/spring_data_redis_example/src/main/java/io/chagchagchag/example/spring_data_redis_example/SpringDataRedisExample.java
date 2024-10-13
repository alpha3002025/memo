package io.chagchagchag.example.spring_data_redis_example;

import io.chagchagchag.example.spring_data_redis_example.book.entity.Book;
import io.chagchagchag.example.spring_data_redis_example.book.redis.BookRankingCache;
import io.chagchagchag.example.spring_data_redis_example.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
public class SpringDataRedisExample implements ApplicationRunner {
	private final RedisTemplate<String, BookRankingCache> bookRankingCacheRedisTemplate;
	private final BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringDataRedisExample.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		generateBookRankingCache();
		generateBookData();
	}

	public void generateBookRankingCache(){
		for(int i=0; i<100; i++){
			BookRankingCache bookRankingCache = BookRankingCache.builder()
					.id(Long.parseLong(String.valueOf(i)))
					.ranking(Long.parseLong(String.valueOf(i)))
					.build();

			bookRankingCacheRedisTemplate.opsForZSet()
					.add("ranking:book", bookRankingCache, bookRankingCache.getRanking());
		}
	}

	@Transactional
	public void generateBookData(){
		List<Book> books = new ArrayList<>();
		for(int i=0; i<100; i++){
			Book book = Book.builder()
					.id(Long.parseLong(String.valueOf(i)))
					.title("Book#" + i)
					.build();
			books.add(book);
		}
		bookRepository.saveAll(books);
	}
}

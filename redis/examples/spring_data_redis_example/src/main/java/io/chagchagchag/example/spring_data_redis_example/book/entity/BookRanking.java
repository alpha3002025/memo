package io.chagchagchag.example.spring_data_redis_example.book.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRanking {
    @Id
    private Long id;
    @Column(nullable = false)
    private Long ranking;
}
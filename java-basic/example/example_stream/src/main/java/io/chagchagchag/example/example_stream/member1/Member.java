package io.chagchagchag.example.example_stream.member1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@AllArgsConstructor
public class Member {
    private Long id;
    private String name;
    private Integer age;
}

package io.chagchagchag.example.example_stream.sorting;

import io.chagchagchag.example.example_stream.member1.Member;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SortingTest {

    @Test
    public void TEST_SORTING_COLLECTIONS_SORT__USING_COMPARATOR(){
        Member alice = Member.builder().id(1L).name("Alice").age(27).build();
        Member ben = Member.builder().id(2L).name("Ben").age(26).build();
        Member charles = Member.builder().id(3L).name("Charles").age(25).build();

        List<Member> members = Arrays.asList(alice, ben, charles);

        Collections.sort(members, (left, right) -> (int) (left.getId() - right.getId()));
        assertThat(members.get(0).getName()).isEqualTo("Alice");

        Collections.sort(members, (left, right) -> left.getAge() - right.getAge());
        assertThat(members.get(0).getName()).isEqualTo("Charles");

        Collections.sort(members, (left, right) -> left.getName().compareTo(right.getName()));
        assertThat(members.get(0).getName()).isEqualTo("Alice");
    }

    // Comparable 을 구현하고 있는 객체에 대한 비교는
    // Comparator.comparing, Comparator.comparingInt 등을 활용 가능
    @Test
    public void TEST_SORTING_COMPARATOR__USING_COMPARATOR_COMPARING__(){
        Member alice = Member.builder().id(1L).name("Alice").age(27).build();
        Member ben = Member.builder().id(2L).name("Ben").age(26).build();
        Member charles = Member.builder().id(3L).name("Charles").age(25).build();

        List<Member> members = Arrays.asList(alice, ben, charles);

        Collections.sort(members, Comparator.comparingInt(Member::getAge));
        assertThat(members.get(0).getName()).isEqualTo("Charles");

        Collections.sort(members, Comparator.comparing(Member::getName));
        assertThat(members.get(0).getName()).isEqualTo("Alice");
    }

    @Test
    public void TEST_COMPARATOR_REVERSED(){
        Member alice = Member.builder().id(1L).name("Alice").age(27).build();
        Member ben = Member.builder().id(2L).name("Ben").age(26).build();
        Member charles = Member.builder().id(3L).name("Charles").age(25).build();

        List<Member> members = Arrays.asList(alice, ben, charles);

        Collections.sort(members, Comparator.comparingInt(Member::getAge).reversed());
        assertThat(members.get(0).getName()).isEqualTo("Alice");
    }

    @Test
    public void TEST_THEN_COMPARING(){
        Member alice = Member.builder().id(1L).name("Alice").age(27).build();
        Member ben = Member.builder().id(2L).name("Ben").age(26).build();
        Member charles = Member.builder().id(3L).name("Charles").age(25).build();

        List<Member> members = Arrays.asList(alice, ben, charles);

        Collections.sort(
                members,
                Comparator
                        .comparingInt(Member::getAge)
                        .thenComparing(Member::getName)
        );

        members.forEach(System.out::println);
        assertThat(members.get(0).getName()).isEqualTo("Charles");
    }

    @Test
    public void TEST_STREAM_SORT_TEST_COMPARATOR_USING_LAMBDA(){
        Member alice = Member.builder().id(1L).name("Alice").age(27).build();
        Member ben = Member.builder().id(2L).name("Ben").age(26).build();
        Member charles = Member.builder().id(3L).name("Charles").age(25).build();

        List<Member> members = Arrays.asList(alice, ben, charles);

        List<Member> sortedMembers1 = members.stream()
                .sorted((v1, v2) -> v1.getAge() - v2.getAge())
                .collect(Collectors.toList());
        assertThat(sortedMembers1.get(0).getName()).isEqualTo("Charles");

        List<Member> sortedMembers2 = members.stream()
                .sorted((v1, v2) -> v1.getName().compareTo(v2.getName()))
                .collect(Collectors.toList());
        assertThat(sortedMembers2.get(0).getName()).isEqualTo("Alice");
    }

    @Test
    public void TEST_STREAM_SORT_TEST_COMPARATOR_COMPARING(){
        Member alice = Member.builder().id(1L).name("Alice").age(27).build();
        Member ben = Member.builder().id(2L).name("Ben").age(26).build();
        Member charles = Member.builder().id(3L).name("Charles").age(25).build();

        List<Member> members = Arrays.asList(alice, ben, charles);

        List<Member> sortedMembers = members.stream()
                .sorted(Comparator.comparing(Member::getAge))
                .collect(Collectors.toList());

        assertThat(sortedMembers.get(0).getName()).isEqualTo("Charles");
    }

    @Test
    public void TEST_STREAM_REVERSED(){
        Member alice = Member.builder().id(1L).name("Alice").age(27).build();
        Member ben = Member.builder().id(2L).name("Ben").age(26).build();
        Member charles = Member.builder().id(3L).name("Charles").age(25).build();

        List<Member> members = Arrays.asList(alice, ben, charles);

        List<Member> sortedMembers = members.stream()
                .sorted(Comparator.comparing(Member::getAge).reversed())
                .collect(Collectors.toList());

        assertThat(sortedMembers.get(0).getName()).isEqualTo("Alice");
    }

    @Test
    public void TEST_STREAM_COMPARATOR_THEN_COMPARING(){
        Member alice = Member.builder().id(1L).name("Alice").age(27).build();
        Member ben = Member.builder().id(2L).name("Ben").age(26).build();
        Member charles = Member.builder().id(3L).name("Charles").age(25).build();

        List<Member> members = Arrays.asList(alice, ben, charles);

        List<Member> sortedMembers = members.stream()
                .sorted(Comparator
                        .comparingInt(Member::getAge)
                        .thenComparing(Member::getName))
                .collect(Collectors.toList());

        assertThat(sortedMembers.get(0).getName()).isEqualTo("Charles");
    }

}

package io.chagchagchag.example.item_moving_redis.doncommit;

import org.junit.jupiter.api.Test;

public class NoNameTest {
    @Test
    public void test__char(){
        char a = 'a';
        char A = 'A';
        char Z = 'Z';
        char z = 'z';
        System.out.println((int)A);
        System.out.println((int)Z);
        System.out.println((int)a);
        System.out.println((int)z);
    }

    @Test
    public void test__char_2(){
        for(int i=(int)'A'; i<(int)'z'; i++){
            if(Character.isAlphabetic(i))
                System.out.println(Character.toString((char)i));
        }
    }
}

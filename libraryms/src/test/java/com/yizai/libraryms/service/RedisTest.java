package com.yizai.libraryms.service;

import com.yizai.libraryms.model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;

@SpringBootTest
public class RedisTest {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    void test() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        // 增
        valueOperations.set("bookString", "cat");
        valueOperations.set("bookInt", 1);
        valueOperations.set("bookDouble", 3.0);
        Book book = new Book();
        book.setId(1L);
        book.setBookName("book1");
        valueOperations.set("myBook", book);
        // 查
        Object bookRel = valueOperations.get("bookString");
        Assertions.assertTrue("cat".equals((String) bookRel));
        bookRel = valueOperations.get("bookInt");
        Assertions.assertTrue(1 == (Integer) bookRel);
        bookRel = valueOperations.get("bookDouble");
        Assertions.assertTrue(3.0 == (Double) bookRel);
        System.out.println(valueOperations.get("myBook"));
        // 改
        valueOperations.set("bookString", "catcat");
        System.out.println(valueOperations.get("bookString"));
        // 删
        redisTemplate.delete("bookDouble");
    }
}

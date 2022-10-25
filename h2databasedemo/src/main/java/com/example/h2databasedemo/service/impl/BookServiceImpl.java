package com.example.h2databasedemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.h2databasedemo.mapper.BookMapper;
import com.example.h2databasedemo.model.Book;
import com.example.h2databasedemo.service.BookService;
import org.springframework.stereotype.Service;

/**
 * @author yizai
 * @since 2022/10/25 17:46
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
        implements BookService {
    @Override
    public long insertBook(String type, String name, String description) {
        Book book = new Book();
        book.setType(type);
        book.setName(name);
        book.setDescription(description);
        boolean saveResult = this.save(book);
        if (!saveResult) {
            return -1;
        }
        return book.getId();
    }
}

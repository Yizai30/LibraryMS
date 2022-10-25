package com.example.h2databasedemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.h2databasedemo.model.Book;

/**
 * @author yizai
 * @since 2022/10/25 17:43
 */
public interface BookService extends IService<Book> {
    long insertBook(String type, String name, String description);
}

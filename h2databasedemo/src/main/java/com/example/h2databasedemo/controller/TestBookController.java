package com.example.h2databasedemo.controller;

import com.example.h2databasedemo.common.BaseResponse;
import com.example.h2databasedemo.common.ErrorCode;
import com.example.h2databasedemo.common.ResultUtils;
import com.example.h2databasedemo.exception.BusinessException;
import com.example.h2databasedemo.model.Book;
import com.example.h2databasedemo.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yizai
 * @since 2022/10/25 17:18
 */
@RestController
@RequestMapping("/book")
@Slf4j
public class TestBookController {

    @Resource
    private BookService bookService;

    @PostMapping("/insert")
    public BaseResponse<Long> bookInsert(@RequestBody Book book) {
//        if (StringUtils.isAnyBlank(type, name, description)) {
//            throw new BusinessException(ErrorCode.PARAMS_ERROR);
//        }
        long result = bookService.insertBook(book.getType(), book.getName(), book.getDescription());
        return ResultUtils.success(result);
    }
}

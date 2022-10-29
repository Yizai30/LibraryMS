package com.yizai.libraryms.controller;

import com.yizai.libraryms.common.BaseResponse;
import com.yizai.libraryms.common.ErrorCode;
import com.yizai.libraryms.common.ResultUtils;
import com.yizai.libraryms.exception.BusinessException;
import com.yizai.libraryms.model.dto.BookQuery;
import com.yizai.libraryms.model.request.BookInsertRequest;
import com.yizai.libraryms.model.request.BookUpdateRequest;
import com.yizai.libraryms.model.vo.BookVO;
import com.yizai.libraryms.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yizai
 * @since 2022/10/29 00:18
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Resource
    private BookService bookService;

    @PostMapping("/insert")
    public BaseResponse<Long> bookInsert(@RequestBody BookInsertRequest bookInsertRequest, HttpServletRequest request) {
        if (bookInsertRequest == null) {
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        String bookName = bookInsertRequest.getBookName();
        String bookDescription = bookInsertRequest.getBookDescription();
        if (StringUtils.isAnyBlank(bookName, bookDescription)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "书籍名称、书籍描述均不能为空");
        }
        long result = bookService.bookInsert(bookName, bookDescription, request);
        return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> bookDelete(@RequestBody long id) {
        if (id <= 0) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR, "书籍 ID 应大于 0");
        }
        boolean result = bookService.removeById(id);
        if (!result) {
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "删除失败");
        }
        return ResultUtils.success(true);
    }

    @PostMapping("/update")
    public BaseResponse<Boolean> bookUpdate(@RequestBody BookUpdateRequest bookUpdateRequest, HttpServletRequest request) {
        if (bookUpdateRequest == null) {
            return ResultUtils.error(ErrorCode.NULL_ERROR);
        }
        boolean result = bookService.updateBook(bookUpdateRequest, request);
        if (!result) {
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "更新失败");
        }
        return ResultUtils.success(true);
    }

    @GetMapping("/list")
    public BaseResponse<List<BookVO>> listBooks(BookQuery bookQuery) {
        if (bookQuery == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        List<BookVO> bookList = bookService.listBooks(bookQuery);
        return ResultUtils.success(bookList);
    }
}

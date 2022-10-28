package com.yizai.libraryms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yizai.libraryms.model.Book;
import com.yizai.libraryms.model.dto.BookQuery;
import com.yizai.libraryms.model.request.BookUpdateRequest;
import com.yizai.libraryms.model.vo.BookVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yizai
 * @since 2022/10/29 00:19
 */
public interface BookService extends IService<Book> {
    /**
     * 插入书籍
     *
     * @param bookName        书籍名称
     * @param bookDescription 书籍描述
     * @param request         插入请求，用以鉴权
     * @return 书籍 ID
     */
    long bookInsert(String bookName, String bookDescription, HttpServletRequest request);

    /**
     * 修改书籍信息
     *
     * @param bookUpdateRequest 修改请求
     * @param request           用以鉴权
     * @return 是否修改成功
     */
    boolean updateBook(BookUpdateRequest bookUpdateRequest, HttpServletRequest request);

    /**
     * 查询书籍信息
     *
     * @param bookQuery 查询请求
     * @return 书籍信息展示实体
     */
    List<BookVO> listBooks(BookQuery bookQuery);
}

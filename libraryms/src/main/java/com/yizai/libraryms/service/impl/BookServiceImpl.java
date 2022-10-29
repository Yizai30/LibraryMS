package com.yizai.libraryms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yizai.libraryms.common.ErrorCode;
import com.yizai.libraryms.exception.BusinessException;
import com.yizai.libraryms.mapper.BookMapper;
import com.yizai.libraryms.model.Book;
import com.yizai.libraryms.model.User;
import com.yizai.libraryms.model.dto.BookQuery;
import com.yizai.libraryms.model.request.BookUpdateRequest;
import com.yizai.libraryms.model.response.UserLoginResponse;
import com.yizai.libraryms.model.vo.BookVO;
import com.yizai.libraryms.service.BookService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static com.yizai.libraryms.constant.UserConstant.ADMIN_ROLE;
import static com.yizai.libraryms.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author yizai
 * @since 2022/10/29 00:19
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>
        implements BookService {

    @Override
    public long bookInsert(String bookName, String bookDescription, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "仅管理员可添加书籍");
        }
        Book book = new Book();
        book.setBookName(bookName);
        book.setBookDescription(bookDescription);
        boolean saveResult = this.save(book);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据存储发生错误，书籍信息插入失败");
        }
        return book.getId();
    }

    @Override
    public boolean updateBook(BookUpdateRequest bookUpdateRequest, HttpServletRequest request) {
        if (bookUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = bookUpdateRequest.getId();
        if (id == null || id < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Book oldBook = this.getById(id);
        if (oldBook == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请输入存在的书籍 ID");
        }
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "仅管理员可修改书籍信息");
        }
        Book updateBook = new Book();
        BeanUtils.copyProperties(bookUpdateRequest, updateBook);
        return this.updateById(updateBook);
    }

    @Override
    public List<BookVO> listBooks(BookQuery bookQuery) {
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        // 组合查询条件
        if (bookQuery != null) {
            Long id = bookQuery.getId();
            if (id != null && id > 0) {
                queryWrapper.eq("id", id);
            }
            String bookName = bookQuery.getBookName();
            if (StringUtils.isNotBlank(bookName)) {
                queryWrapper.and(qw -> qw.like("bookName", bookName).or().like("bookDescription", bookName));
            }
            String bookDescription = bookQuery.getBookDescription();
            if (StringUtils.isNotBlank(bookDescription)) {
                queryWrapper.like("bookDescription", bookDescription);
            }
        }
        // 查询
        List<Book> bookList = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(bookList)) {
            return new ArrayList<>();
        }
        // 封装
        List<BookVO> bookVOList = new ArrayList<>();
        for (Book book : bookList) {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            bookVOList.add(bookVO);
        }
        return bookVOList;
    }

    /**
     * 检查是否为管理员请求
     *
     * @param request 插入请求，用以鉴权
     * @return 是否为管理员
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserLoginResponse safetyUser = (UserLoginResponse) userObj;
        return safetyUser != null && safetyUser.getUserRole() == ADMIN_ROLE;
    }
}

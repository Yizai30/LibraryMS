package com.yizai.libraryms.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 书籍查询封装类
 *
 * @author yizai
 * @since 2022/10/29 02:00
 */
@Data
public class BookQuery implements Serializable {
    private static final long serialVersionUID = -1432429963489994866L;
    private Long id;
    private String bookName;
    private String bookDescription;
}

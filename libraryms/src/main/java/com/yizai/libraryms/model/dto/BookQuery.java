package com.yizai.libraryms.model.dto;

import com.yizai.libraryms.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 书籍查询封装类
 *
 * @author yizai
 * @since 2022/10/29 02:00
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BookQuery extends PageRequest {
    private Long id;
    private String bookName;
    private String bookDescription;
}

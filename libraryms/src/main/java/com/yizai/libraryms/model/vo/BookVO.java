package com.yizai.libraryms.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 书籍信息展示类
 *
 * @author yizai
 * @since 2022/10/29 01:57
 */
@Data
public class BookVO implements Serializable {
    private static final long serialVersionUID = -7230542800695617325L;
    private Long id;
    private String bookName;
    private String bookDescription;
    private Date createTime;
}

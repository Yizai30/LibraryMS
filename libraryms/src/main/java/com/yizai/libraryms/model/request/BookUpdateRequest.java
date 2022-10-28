package com.yizai.libraryms.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yizai
 * @since 2022/10/29 01:13
 */
@Data
public class BookUpdateRequest implements Serializable {
    private static final long serialVersionUID = 111017370843749341L;
    private Long id;
    private String bookName;
    private String bookDescription;
}

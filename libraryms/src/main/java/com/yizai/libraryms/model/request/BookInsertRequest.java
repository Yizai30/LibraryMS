package com.yizai.libraryms.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author yizai
 * @since 2022/10/29 00:27
 */
@Data
public class BookInsertRequest implements Serializable {
    private static final long serialVersionUID = -8487773428159800308L;
    private String bookName;
    private String bookDescription;
}

package com.yizai.libraryms.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用分页请求参数
 *
 * @author Mena
 */
@Data
public class PageRequest implements Serializable {

    private static final long serialVersionUID = -549935657836818952L;

    /**
     * 页面大小
     */
    protected int pageSize = 20;

    /**
     * 当前是第几页
     */
    protected int pageNum = 1;
}
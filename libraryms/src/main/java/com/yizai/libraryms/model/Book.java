package com.yizai.libraryms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 书籍实体
 *
 * @author yizai
 * @since 2022/10/29 00:14
 */
@TableName(value = "book_info")
@Data
public class Book implements Serializable {
    private static final long serialVersionUID = -1059096062536061853L;
    /**
     * 书籍 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 书籍名称
     */
    private String bookName;
    /**
     * 书籍描述
     */
    private String bookDescription;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除
     */
    private Byte isDelete;
}

package com.example.h2databasedemo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yizai
 * @since 2022/10/25 17:35
 */
@TableName(value = "TEST_BOOK")
@Data
public class Book implements Serializable {

    private static final long serialVersionUID = 6040514332277896339L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String type;
    private String name;
    private String description;
}

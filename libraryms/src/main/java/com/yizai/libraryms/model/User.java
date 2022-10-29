package com.yizai.libraryms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体
 *
 * @author yizai
 * @since 2022/10/27 11:08
 */
@TableName(value = "user_info")
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -7437453171803267843L;
    /**
     * 用户 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户角色：0 - 普通用户，1 - 管理员
     */
    private Integer userRole;

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

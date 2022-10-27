package com.yizai.libraryms.model.response;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yizai
 * @since 2022/10/27 20:10
 */
@Data
public class UserLoginResponse implements Serializable {
    private static final long serialVersionUID = -4354151634149645304L;
    private Long id;
    private String userAccount;
    private Integer userRole;
    private Date createTime;
}

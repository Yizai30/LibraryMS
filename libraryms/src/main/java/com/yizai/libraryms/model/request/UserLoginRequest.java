package com.yizai.libraryms.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author yizai
 * @since 2022/10/27 18:51
 */
@Data
public class UserLoginRequest implements Serializable {
    private static final long serialVersionUID = -8229370100948078347L;
    private String userAccount;
    private String userPassword;
}

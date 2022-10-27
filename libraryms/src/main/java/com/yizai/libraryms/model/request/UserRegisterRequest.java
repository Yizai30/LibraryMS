package com.yizai.libraryms.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author yizai
 * @since 2022/10/27 16:58
 */
@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 437868081049165293L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}

package com.yizai.libraryms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yizai.libraryms.model.User;
import com.yizai.libraryms.model.response.UserLoginResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yizai
 * @since 2022/10/27 11:19
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册服务
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 确认密码
     * @return 新用户 ID
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 用户登录服务
     * @param userAccount 账号
     * @param userPassword 密码
     * @param request 用于记录用户登录态
     * @return 用户对象
     */
    UserLoginResponse userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser 用户原信息
     * @return 脱敏后的用户信息
     */
    UserLoginResponse getSafetyUser(User originUser);
}

package com.yizai.libraryms.service;

import com.yizai.libraryms.model.response.UserLoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void userRegister() {
        // 非空
        String userAccount = "yizai";
        String userPassword = "";
        String checkPassword = "";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);
        // 用户账号不少于 4 位
        userAccount = "yiz";
        userPassword = "yizaiyizai";
        checkPassword = "yizaiyizai";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);
        // 用户密码为 8 ～ 16 位
        userAccount = "yizai";
        userPassword = "yizai";
        checkPassword = "yizai";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);
        // 用户账号只能包含下划线、数字和字母
        userPassword = "yizaiyizai";
        checkPassword = "yizaiyizai";
        userAccount = "yi zai";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);
        // 密码和校验密码必须一致
        userAccount = "yizai";
        userPassword = "yizaiyiz";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        assertEquals(-1, result);
        // 不可重复注册（首次注册成功，再次以已有信息注册则失败）
        userPassword = "yizaiyizai";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
//        assertTrue(result > 0);
        assertEquals(-1, result);
    }

    @Test
    void userLogin() {
        // 请求参数非空
        String userAccount = "";
        String userPassword = "";
//        UserLoginResponse result = userService.userLogin(userAccount, userPassword);
//        assertNull(result);
        // 用户账号不少于 4 位
        userAccount = "adm";
        userPassword = "adminadmin";
//        result = userService.userLogin(userAccount, userPassword);
//        assertNull(result);
        // 用户密码为 8 ～ 16 位
        userAccount = "admin";
        userPassword = "admin";
//        result = userService.userLogin(userAccount, userPassword);
//        assertNull(result);
        // 用户账号只能包含数字、字母和下划线
        userAccount = "adm in";
        userPassword = "adminadmin";
//        result = userService.userLogin(userAccount, userPassword);
//        assertNull(result);
        // 用户需存在
        userAccount = "admina";
//        result = userService.userLogin(userAccount, userPassword);
//        assertNull(result);
        userAccount = "admin";
//        result = userService.userLogin(userAccount, userPassword);
//        assertNotNull(result);
    }
}
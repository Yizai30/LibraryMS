package com.yizai.libraryms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yizai.libraryms.common.ErrorCode;
import com.yizai.libraryms.exception.BusinessException;
import com.yizai.libraryms.mapper.UserMapper;
import com.yizai.libraryms.model.User;
import com.yizai.libraryms.model.response.UserLoginResponse;
import com.yizai.libraryms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yizai.libraryms.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author yizai
 * @since 2022/10/27 11:19
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    UserMapper userMapper;

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "yizai";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
//            return -1;
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
//            return -1;
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号应不少于 4 位");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8
                || userPassword.length() > 16 || checkPassword.length() > 16) {
//            return -1;
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码应为 8 ~ 16 位");
        }
        // 用户账号只能包含数字、字母和下划线
        String invalidPattern = "[^_0-9a-zA-Z]";
        Matcher matcher = Pattern.compile(invalidPattern).matcher(userAccount);
        if (matcher.find()) {
//            return -1;
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号只能包含数字、字母和下划线");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
//            return -1;
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码和校验密码应保持相同");
        }
        // 用户账号不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("isDelete", 0);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
//            return -1;
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号已注册");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public UserLoginResponse userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
//            return null;
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号应不少于 4 位");
//            return null;
        }
        if (userPassword.length() < 8 || userPassword.length() > 16) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码应为 8 ~ 16 位");
//            return null;
        }
        // 用户账号只能包含数字、字母和下划线
        String invalidPattern = "[^_0-9a-zA-Z]";
        Matcher matcher = Pattern.compile(invalidPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号只能包含数字、字母和下划线");
//            return null;
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        queryWrapper.eq("isDelete", 0);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.LOGIN_FAILED, "用户账号或密码错误");
//            return null;
        }
        // 3. 用户脱敏
        UserLoginResponse safetyUser = getSafetyUser(user);
        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    @Override
    public UserLoginResponse getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        UserLoginResponse safetyUser = new UserLoginResponse();
        safetyUser.setId(originUser.getId());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }

}

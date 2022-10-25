package com.example.h2databasedemo.common;

/**
 * 返回工具类（封装返回结果）
 *
 * @author Mena
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data 响应数据
     * @param <T> 泛型
     * @return 通用返回对象
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode 自定义异常状态码
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }

    /**
     * 失败
     *
     * @param code 状态码
     * @param message 报错信息
     * @param description 详细描述
     * @return 通用返回对象
     */
    public static BaseResponse error(int code, String message, String description) {
        return new BaseResponse(code, null, message, description);
    }

    /**
     * 失败
     *
     * @param errorCode 自定义异常状态码
     * @param message 报错信息
     * @param description 详细描述
     * @return 通用返回对象
     */
    public static BaseResponse error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse(errorCode.getCode(), null, message, description);
    }

    /**
     * 失败
     *
     * @param errorCode 自定义异常状态码
     * @param description 详细描述
     * @return 通用返回对象
     */
    public static BaseResponse error(ErrorCode errorCode, String description) {
        return new BaseResponse(errorCode.getCode(), null, errorCode.getMessage(), description);
    }
}
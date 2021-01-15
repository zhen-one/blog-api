package com.blog.api.common.response;

import javax.servlet.http.HttpServletResponse;

public class ResponseUtil<T> {

    /**
     * 成功时候的调用
     *
     * @return
     */
    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult<T>(data);
    }

    /**
     * 失败时候的调用
     *
     * @return
     */
    public static <T> ResponseResult<T> fail(ResultCode rs) {
        return new ResponseResult<T>(rs);
    }

    public static <T> ResponseResult<T> fail(String msg) {
        return new ResponseResult<T>(500, msg);
    }
    /**
     * 失败时候的调用
     *
     * @return
     */
    public static <T> ResponseResult<T> fail(int code, String msg) {
        return new ResponseResult<T>(code, msg);
    }

    public static <T> ResponseResult<T> fail() {
        return fail(ResultCode.FAILED);
    }



    /**
     * 参数验证失败返回结果
     */
    public static <T> ResponseResult<T> validateFailed() {
        return fail(ResultCode.VALIDATE_FAILED);
    }

    /**
     * 参数验证失败返回结果
     */
    public static <T> ResponseResult<T> validateFailed(String msg) {
        return fail(ResultCode.VALIDATE_FAILED.getCode(), ResultCode.VALIDATE_FAILED.getMessage()+"---"+msg);
    }

    /**
     * 认证失败
     */
    public static <T> ResponseResult<T> unAuthorized() {
        return fail(ResultCode.UNAUTHORIZED);
    }

    /**
     * 认证失败
     */
    public static <T> ResponseResult<T> unAuthorized(String msg) {
        return fail(ResultCode.UNAUTHORIZED.getCode(),ResultCode.UNAUTHORIZED.getMessage()+"---"+msg);
    }


    /**
     * 没有权限
     */
    public static <T> ResponseResult<T> forbidden() {
        return fail(ResultCode.FORBIDDEN);
    }

    /**
     * 没有权限
     */
    public static <T> ResponseResult<T> forbidden(String msg) {
        return fail(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage()+"---"+msg);
    }

}

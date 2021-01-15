package com.blog.api.common.response;


import cn.hutool.json.JSONUtil;
import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Data
public class ResponseResult<T> {

    T data;

    String msg;

    int retCode;

    boolean success;

    ResponseResult(T data) {
        this(200, "操作成功", data);
    }

    ResponseResult(ResultCode rs) {
        this(rs.getCode(), rs.getMessage());
    }


    ResponseResult(int code, String msg) {
        this(code, msg, null);
    }

    ResponseResult(int code, String msg, T data) {
        this.retCode = code;
        this.msg = msg;
        this.data = data;
        this.success=code==200;
    }
    public void toJson(HttpServletResponse response) throws IOException {

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write( JSONUtil.toJsonStr(this));
    }

}

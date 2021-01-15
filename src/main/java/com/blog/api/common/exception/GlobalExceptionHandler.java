package com.blog.api.common.exception;


import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.common.response.ResultCode;
import com.blog.api.security.AuthExceptionHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     *
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public ResponseResult bizExceptionHandler(HttpServletRequest req, BizException e) {
        //业务逻辑不需要记录日志
        //logger.error("发生业务异常！原因是：{}",e.getErrorMsg());
        return ResponseUtil.fail(e.getErrorMsg());
    }

    @ExceptionHandler(value= MethodArgumentNotValidException.class)
    public ResponseResult validatedFailedHandler(MethodArgumentNotValidException e){
        BindingResult result = e.getBindingResult();
        String msg="";
        //多个错误 读取第一个
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();
            if (errors != null) {
                if (errors.size() > 0) {
                    FieldError fieldError = (FieldError) errors.get(0);
                    msg = fieldError.getDefaultMessage();
                }
            }
        }
        return ResponseUtil.fail(msg);

    }
    /**
     * 其他异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseResult exceptionHandler(Exception e) {


        logger.error("application error", e);
        return ResponseUtil.fail(e.getMessage());
    }

    /**
     * 认证异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseResult exceptionHandler(AuthenticationException e) {

        logger.error("认证异常 ", e);
        return ResponseUtil.fail( AuthExceptionHelper.GetMessage(e));
    }



}

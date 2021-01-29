package com.blog.api.security;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AuthExceptionHelper {

    public static String GetMessage(AuthenticationException ex){


        if(ex instanceof UsernameNotFoundException ||ex instanceof BadCredentialsException){
            return "用户名或密码无效";
        }
        else if(ex instanceof DisabledException){
            return "账户已被禁用";
        }
        else if(ex instanceof LockedException){
            return "账户已锁定";
        }
        else if(ex instanceof AccountExpiredException){
            return "账户已经过期";
        }
        else if(ex instanceof AuthenticationCredentialsNotFoundException){
            return "未登陆";
        }
        else{
            return  ex.getMessage();
        }

    }
}

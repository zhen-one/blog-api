package com.blog.api.security.jwt;

import cn.hutool.json.JSONUtil;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.security.AuthExceptionHelper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * 认证失败
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        var msg= AuthExceptionHelper.GetMessage(authException);
        ResponseUtil.unAuthorized(msg).toJson(response);

    }
}
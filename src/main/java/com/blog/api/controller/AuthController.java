package com.blog.api.controller;
import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.dto.TokenDto;
import com.blog.api.dto.LoginDto;
import com.blog.api.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.constraints.NotNull;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private SysUserService authService;

    @PermitAll()
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult<TokenDto> createToken(
            @Validated  @RequestBody LoginDto loginDto
    ) throws AuthenticationException {


//        String username=JSONUtil.parseObj(param).get("username").toString();
//        String password=JSONUtil.parseObj(param).get("password").toString();

        //  @RequestBody JwtAuthenticationRequest authenticationRequest
        final TokenDto tokenDto = authService.login(loginDto.getAccount(), loginDto.getPassword());

        // Return the token
        return ResponseUtil.success(tokenDto);
    }

    @PermitAll()
    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseResult<TokenDto> refreshToken(
            String token
    ) throws AuthenticationException {
        //  @RequestBody JwtAuthenticationRequest authenticationRequest
        final TokenDto tokenDto = authService.refreshToken(token);

        // Return the token
        return ResponseUtil.success(tokenDto);
    }


}
package com.blog.api.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.blog.api.common.exception.BizException;
import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.dto.TokenDto;
import com.blog.api.dto.UserDto;
import com.blog.api.model.OauthUser;
import com.blog.api.model.SysUser;
import com.blog.api.model.UserRole;
import com.blog.api.repo.OauthUserRepository;
import com.blog.api.repo.SysUserRepository;
import com.blog.api.security.AuthExceptionHelper;
import com.blog.api.security.SecurityUser;

import com.blog.api.security.jwt.JwtTokenUtils;
import com.blog.api.service.base.BaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SysUserService extends BaseService<SysUser, Integer> {

    SysUserRepository dal;

    @Autowired
    OauthUserRepository oauthUserRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtils jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    public SysUserService(SysUserRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }


    public SysUser getByName(String userName) {
        return this.dal.getUser(userName);
    }

    public TokenDto login(String userName, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userName, password);
        // Perform the security

        try {
            final Authentication authentication = authenticationManager.authenticate(upToken);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//           var context= SecurityContextHolder.getContext();
            // Reload password post-security so we can generate token
            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            final TokenDto tokenDto = jwtTokenUtil.accessToken((SecurityUser) userDetails);
            return tokenDto;
        } catch (Exception ex) {
            throw ex;
        }
//        return null;

    }

    public TokenDto refreshToken(String token) {
        return jwtTokenUtil.refreshToken(token);
    }

    private String randomPassword() {
        char[] chars = new char[8];
        Random rnd = new Random();
        for (int i = 0; i < 6; i++) {
            chars[i] = (char) ('0' + rnd.nextInt(10));
        }
        return new String(chars);
    }



    public TokenDto oauthLogin(OauthUser authUser) {
        OauthUser findOne = oauthUserRepository.getUser(authUser.getUuid(), authUser.getSource());
        SysUser sysUser = null;
        if (findOne == null) {

            sysUser = new SysUser();
            sysUser.setAccount(authUser.getUsername());
            sysUser.setPassword(randomPassword());
            sysUser.setNickName(authUser.getNickname());
            sysUser.setAvatar(authUser.getAvatar());
            var res = this.dal.save(sysUser);
            authUser.setSysUserId(Integer.valueOf(res.getId()).toString());
            this.oauthUserRepository.save(authUser);
        } else {
            sysUser = this.dal.findById(Integer.valueOf(findOne.getSysUserId())).get();
            authUser.setId(findOne.getId());
            authUser.setSysUserId(Integer.valueOf(sysUser.getId()).toString());
            this.oauthUserRepository.saveAndFlush(authUser);
        }
        return this.login(sysUser.getAccount(),sysUser.getPassword());


    }


}

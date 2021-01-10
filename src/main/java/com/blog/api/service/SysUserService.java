package com.blog.api.service;

import com.blog.api.model.SysUser;
import com.blog.api.repo.SysUserRepository;
import com.blog.api.repo.base.BaseRepository;
import com.blog.api.security.SecurityUser;
import com.blog.api.security.jwt.JwtTokenUtils;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SysUserService extends BaseService<SysUser,Long> {

    SysUserRepository dal;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtils jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public SysUserService(SysUserRepository dal){//这里必须要使用构造注入。
        super.dal=dal;
        this.dal=dal;

    }


    public SysUser getByName(String userName){
        return this.dal.getUser(userName);
    }

    public String  login(String userName,String password){
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userName, password);
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

        final String token = jwtTokenUtil.createToken((SecurityUser) userDetails);
        return token;
    }


}

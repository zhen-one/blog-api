package com.blog.api.security;

import com.blog.api.model.Permission;
import com.blog.api.model.Role;
import com.blog.api.model.SysUser;
import com.blog.api.service.PermissionService;
import com.blog.api.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

//    @Autowired
//    private RedisTemplateHelper redisTemplate;

    @Autowired
    private SysUserService userService;


    @Autowired
    private PermissionService permissionService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        SysUser user = userService.getByName(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在", username));
        }

        //利用set去重
        Set<Permission> permissions = new HashSet<>();
        for (Role role : user.getRoles()) {
            permissions.addAll(role.getPermissions());
        }

        var customAuthorities = permissions.stream().map(n -> new CustomAuthority(n)).collect(Collectors.toList());

        return new SecurityUser(user, customAuthorities);


    }
}
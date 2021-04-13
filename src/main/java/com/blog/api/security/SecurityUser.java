package com.blog.api.security;

import com.blog.api.model.Role;
import com.blog.api.model.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

@Data
public class SecurityUser implements UserDetails {


    private boolean isAdmin;

    private int userid;

    public int getUserid() {
        return userid;
    }


    private String userName;


    private String password;

    private String avatar;

    private String nickName;
    private Collection<? extends GrantedAuthority> authorities;


    public SecurityUser(SysUser user, List<? extends GrantedAuthority> authorities) {

        if (null != user) {
            this.setUserName(user.getAccount());
            this.setPassword(user.getPassword());
            this.setAvatar(user.getAvatar());
            this.nickName=user.getNickName();
            this.setAuthorities(authorities);
            if (user.getRoles() != null) {
                for (Role role : user.getRoles()) {
                    if (role.getRoleName().equals("超级管理员")) {
                        this.isAdmin = true;
                        continue;
                    }
                }
            }
        }
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getAvatar() {
        return this.avatar;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }


    public String getNickname() {
        return this.nickName;
    }


    //账户是否过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    //账户锁
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //密码过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //是否启用

    @Override
    public boolean isEnabled() {
        return true;
    }
}

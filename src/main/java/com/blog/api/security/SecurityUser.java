package com.blog.api.security;

import com.blog.api.model.SysUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class SecurityUser implements UserDetails {



    private long userid;

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    private String userName;


    private String password;
    private Collection<? extends GrantedAuthority> authorities;


    public SecurityUser(SysUser user) {


        if (null != user) {
            this.setUserName(user.getAccount());
            this.setPassword(user.getPassword());
//            this.setStatus(user.getStatus());
//            this.setRoles(user.getRoles());
//            this.setPermissions(user.getPermissions());
        }
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
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

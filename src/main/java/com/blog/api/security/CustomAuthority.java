package com.blog.api.security;

import com.blog.api.model.Permission;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


@Data
public class CustomAuthority  implements GrantedAuthority {

    private Permission permission;

    @Override
    public String getAuthority() {
        return this.permission.getPermissionName();
    }

    public CustomAuthority(Permission permission){
        this.permission=permission;
    }



}

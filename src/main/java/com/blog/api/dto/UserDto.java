package com.blog.api.dto;

import com.blog.api.model.Role;
import com.blog.api.model.base.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto extends BaseDto {

    @NotBlank(message = "账号不允许为空")
    private String account;

    @NotBlank(message = "昵称不允许为空")
    private String nickName;

    @NotBlank(message = "密码不允许为空")
    private String password;

    private String email;

    private String avatar;

    private int gender;

    private int[] roleIds;

    private Role[] roles;
}

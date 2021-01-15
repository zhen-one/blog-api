package com.blog.api.dto;

import com.blog.api.model.base.BaseModel;
import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto extends BaseModel {

    @NotBlank(message = "账号不允许为空")
    private String account;

    @NotBlank(message = "昵称不允许为空")
    private String nickName;

    @NotBlank(message = "密码不允许为空")
    private String password;

    private String email;


    private int gender;
}

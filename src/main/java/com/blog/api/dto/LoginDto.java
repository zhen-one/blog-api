package com.blog.api.dto;

import com.blog.api.model.base.BaseModel;
import lombok.Data;


import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NotNull
public class LoginDto extends BaseModel {

    @NotBlank(message = "账号不允许为空")
    private String account;

    private String nickName;

    @NotBlank(message = "密码不允许为空")
    private String password;

    private String email;

    private String gender;

}

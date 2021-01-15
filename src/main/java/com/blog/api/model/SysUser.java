package com.blog.api.model;

import com.blog.api.model.base.BaseModel;
import lombok.Data;

import javax.persistence.*;



/*
 * 系统表
 * */
@Data
@Table
@Entity
public class SysUser extends BaseModel {



    @Column
    private String account;

    @Column
    private String nickName;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private int gender;




}

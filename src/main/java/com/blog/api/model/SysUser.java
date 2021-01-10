package com.blog.api.model;

import com.blog.api.model.base.BaseModel;

import javax.persistence.*;


@Table
@Entity
public class SysUser extends BaseModel {

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column
    private String account;

    @Column
    private String nickName;

    @Column
    private String password;

    @Column
    private String email;




}

package com.blog.api.model;

import com.blog.api.model.base.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.LifecycleState;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/*
 * 系统表
 * */
@Setter
@Getter
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


    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name="user_role_relation",joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles=new HashSet<>();






}

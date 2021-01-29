package com.blog.api.model;

import com.blog.api.common.anotation.Field;
import com.blog.api.model.base.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.LifecycleState;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@DynamicUpdate
@DynamicInsert
public class SysUser extends BaseModel {



    @Field(description = "账户",unique = true,query = true,queryOp = Field.Operator.like)
    @Column
    private String account;

    @Field(description = "昵称",unique = true,query = true,queryOp = Field.Operator.like)
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

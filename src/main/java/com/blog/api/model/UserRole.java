package com.blog.api.model;

import com.blog.api.model.base.BaseModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/*
* 用户角色表
*/
@Data
@Table
@Entity
public class UserRole extends BaseModel {




    private long userId;

    private long roleId;

    public UserRole(long userId,long roleId){
        this.userId=userId;
        this.roleId=roleId;

    }
}

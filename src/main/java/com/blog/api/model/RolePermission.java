package com.blog.api.model;

import com.blog.api.model.base.BaseModel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Table
@Entity
/**
 *  角色权限关系表
 */
public class RolePermission extends BaseModel {


    /*
     * 角色id
     * */
    private long roleId;


    /*
     * 权限id
     * */
    private long permissionId;

}

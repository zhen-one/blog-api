package com.blog.api.dto;

import com.blog.api.model.Permission;
import com.blog.api.model.base.BaseModel;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;


@Data
public class RoleDto extends BaseDto {



    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String description;


    /**
     * 排序
     */
    private int sortOrder;

    private int[] permissionIds;

    private Permission[] permissions;

}

package com.blog.api.dto;

import com.blog.api.model.base.BaseModel;
import lombok.Data;


@Data
public class RoleDto extends BaseModel {



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
}

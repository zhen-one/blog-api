package com.blog.api.dto;

import com.blog.api.model.Permission;
import com.blog.api.model.Role;
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

    @Override
    public boolean equals(Object obj) {
      return super.equals(obj);
    }

    /**
     * 重写hashcode方法，返回的hashCode一样才再去比较每个属性的值
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

}

package com.blog.api.dto;

import com.blog.api.model.Api;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

public class PermissionDto extends  BaseDto{


    //权限名
    private String permissionName;

    //前端路由
    private String path;

    //路由对应组件
    private String component;

    //图标
    private String icon;

    //是否隐藏
    private boolean isHidden;

    //是否按钮
    private boolean isButton;

    //排序
    private int sort;

    //上级ID
    private int parentId;

    private ApiDto api;
}

package com.blog.api.dto;

import com.blog.api.model.Api;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.List;

@Data
public class PermissionDto extends TreeNode {

    //权限名
    private String name;

    //前端路由
    private String path;

    //路由对应组件
    private String component;

    //图标
    private String icon;

    //权限类型 1导航 2菜单 3按钮
    private int type;

    /**
     * 按钮无权限状态 1隐藏 2禁用 当type=3时生效
     */
    private int denyStatus;
    //排序
    private int sorting;



    private boolean isOutlink;


    /**
     * 描述
     */
    private String description;


    private int apiId;
    private Api api;


    private boolean checked;
//    //上级ID
//    private int parentId;
//
////    private List<PermissionDto> children;
}

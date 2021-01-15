package com.blog.api.model;

import com.blog.api.model.base.BaseModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/*
权限表（菜单|按钮）
* */
@Data
@Table
@Entity
public class Permission extends BaseModel {


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

    //接口url 默认 一个权限对应一个接口
    private String functionUrl;


}

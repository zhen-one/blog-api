package com.blog.api.model;

import com.blog.api.common.anotation.Field;
import com.blog.api.model.base.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/*
权限表（菜单|按钮）
* */
@Setter
@Getter
@Table
@Entity
@DynamicUpdate
@DynamicInsert
public class Permission extends BaseModel {


    @Field(description = "权限名称",unique = true,query = true,queryOp = Field.Operator.like)
    //权限名
    private String name;

    //英文名
    private String code;


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
    private int sortOrder;

    //上级ID
    private int parentId;

    private int isOutlink;

    /**
     * 描述
     */
    private String description;


    @OneToOne
    @JoinColumn(name = "api_id", referencedColumnName = "id")
    private Api api;


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

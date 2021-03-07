package com.blog.api.model;

import com.blog.api.common.anotation.Field;
import com.blog.api.model.base.BaseModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table
@Entity
@DynamicUpdate
@DynamicInsert
@Data
public class Category extends BaseModel {



    @Field(description = "分类名称",unique = true,query = true,queryOp = Field.Operator.like)
    @Column
    private String categoryName;


    @Column
    private String description;

}

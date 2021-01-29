package com.blog.api.model;

import com.blog.api.common.anotation.Field;
import com.blog.api.model.base.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Table
@Entity
@DynamicUpdate
@DynamicInsert
public class Api extends BaseModel {

    public Api(){

    }
    public Api(String url){
        this.url=url;

    }

    @Field(description = "接口名称",unique = true,query = true,queryOp = Field.Operator.like)
    @Column
    private String name;

    @Field(description = "接口url",unique = true,query = true,queryOp = Field.Operator.like)
    @Column
    private String url;

    @Field(description = "接口编码",unique = true,query = true,queryOp = Field.Operator.like)
    @Column
    private String code;

    @Column
    private String description;
}

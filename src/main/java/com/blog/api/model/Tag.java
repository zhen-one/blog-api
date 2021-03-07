package com.blog.api.model;

import com.blog.api.common.anotation.Field;
import com.blog.api.model.base.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@Table
@Entity
public class Tag extends BaseModel {

    @Field(description = "标签名称",unique = true,query = true,queryOp = Field.Operator.like)
    @Column
    private String tagName;


    @Column
    private String description;

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

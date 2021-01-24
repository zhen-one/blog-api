package com.blog.api.model;

import com.blog.api.model.base.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Table
@Entity
public class Api extends BaseModel {

    public Api(String url){
        this.url=url;

    }

    @Column
    private String name;

    @Column
    private String url;

    @Column
    private String code;

    @Column
    private String description;
}

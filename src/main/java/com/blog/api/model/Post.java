package com.blog.api.model;


import com.blog.api.common.anotation.Field;
import com.blog.api.enums.PublishState;
import com.blog.api.model.base.BaseModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.util.Date;


@Table
@Entity
@DynamicUpdate
@DynamicInsert
@Data
public class Post extends BaseModel {


    @Field(description = "发布状态",query = true,queryOp = Field.Operator.like)
    private PublishState publishState;

    private String author;

    //封面图片
    private String coverImg;

    @Field(description = "文章标题",unique = true,query = true,queryOp = Field.Operator.like)
    private String title;

    @Field(description = "文章内容",query = true,queryOp = Field.Operator.like)
    private String content;

    //简介
    private String degest;

    private String category;

    private long categoryId;

    private int likes;


    private int viewNum;

    private int commentNum;

}


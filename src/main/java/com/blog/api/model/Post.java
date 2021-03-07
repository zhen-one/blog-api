package com.blog.api.model;


import com.blog.api.common.anotation.Field;
import com.blog.api.enums.PostRenderType;
import com.blog.api.enums.PublishState;
import com.blog.api.model.base.BaseModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Table
@Entity
@DynamicUpdate
@DynamicInsert
@Data
public class Post extends BaseModel {

    @Enumerated(EnumType.STRING)
    @Field(description = "发布状态",query = true,queryOp = Field.Operator.equal)
    private PublishState publishState=PublishState.Draft;

    @Enumerated(EnumType.STRING)
    @Field(description = "文章类型",query = true,queryOp = Field.Operator.equal)
    private PostRenderType postRenderType;

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

    @Field(description = "分类")
    private long categoryId;

    private int likes;


    private int viewNum;

    private int commentNum;


    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name="post_tag_rel",joinColumns = @JoinColumn(name ="tag_id"),
            inverseJoinColumns = @JoinColumn(name="post_id"))
    private Set<Tag> tags=new HashSet<>();

}


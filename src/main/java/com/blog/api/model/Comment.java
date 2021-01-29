package com.blog.api.model;


import com.blog.api.enums.ComemntModuleType;
import com.blog.api.enums.PublishState;
import com.blog.api.model.base.BaseModel;
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
public class Comment extends BaseModel {


    /**
     * 所属模块类型
     */
    private ComemntModuleType comemntModuleType;

    //模块id
    private long moduleId;

    /**
     * 决定在哪一层
     */
    private long parentId;

    /**
     * 决定是评论的回复 还是回复的回复
     */
    private long quoteId;

    private String content;

    private String author;

    private String email;

    private boolean isSysUserCreated;

    private long userId;


    private String site;

    private boolean isTop;

    private boolean isDirty;

    private boolean isPrivate;

    private int likes;

    private String ip;

    private PublishState publishState;

    private String ip_location;


}

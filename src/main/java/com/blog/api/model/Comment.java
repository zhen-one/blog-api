package com.blog.api.model;


import com.blog.api.common.anotation.Field;
import com.blog.api.enums.ComemntModuleType;
import com.blog.api.enums.PublishState;
import com.blog.api.model.base.BaseModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;


@Table
@Entity
@DynamicUpdate
@DynamicInsert
@Data
public class Comment extends BaseModel {


    /**
     * 所属模块类型
     */
    @Enumerated(EnumType.STRING)
    private ComemntModuleType comemntModuleType;

    @Enumerated(EnumType.STRING)
    private PublishState publishState=PublishState.Published;
    //模块id
    private int moduleId;

    /**
     * 0 文章的評論 !0 評論/回復
     */
    @Field(query = true,queryOp = Field.Operator.equal)
    private int parentId;

    /**
     * 0評論的回復  !0回復的回復
     */
    private int quoteId;

    /*回复人*/
    private String replyName;

    private String content;

    private String author;

    private String avatar;

    private String email;

    private boolean isSysUserCreated;

    private long userId;


    private String site;

    private boolean isTop;

    private boolean isDirty;

    private boolean isPrivate;

    private int likes;

    private String ip;



    private String ip_location;

    private String equipment;


}

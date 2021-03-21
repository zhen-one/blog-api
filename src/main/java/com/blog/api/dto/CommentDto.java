package com.blog.api.dto;

import com.blog.api.enums.ComemntModuleType;
import com.blog.api.enums.PublishState;
import lombok.Data;

@Data
public class CommentDto extends BaseDto{

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

    private PublishState publishState;

    private String ip_location;

    private String equipment;
}

package com.blog.api.dto;

import com.blog.api.enums.ComemntModuleType;
import com.blog.api.enums.PublishState;
import com.blog.api.model.Comment;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CommentDto extends BaseDto{


    @NotBlank
    /**
     * 所属模块类型
     */
    private ComemntModuleType comemntModuleType;

    //模块id
    private long moduleId;

    /**
     * 决定在哪一层
     */
    private int parentId;

    /**
     * 决定是评论的回复 还是回复的回复
     */
    private int quoteId;

    /*回复人*/
    private String replyName;

    @NotBlank
    private String content;

    @NotBlank
    private String author;

    private String avatar;

    private String email;

    private boolean isSysUserCreated;

    private int userId;


    private String site;

    private boolean isTop;

    private boolean isDirty;

    private boolean isPrivate;

    private int likes;

    @NotBlank
    private String ip;

    @NotBlank
    private PublishState publishState=PublishState.Published;;

    @NotBlank
    private String ip_location;

    @NotBlank
    private String equipment;

    private List<Comment> sub;
}

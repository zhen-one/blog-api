package com.blog.api.dto;

import com.blog.api.common.anotation.Field;
import com.blog.api.enums.PostRenderType;
import com.blog.api.enums.PublishState;
import com.blog.api.model.Post;
import com.blog.api.model.Tag;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class PostDto extends BaseDto{


    @NotBlank(message = "发布状态不允许为空")
    private PublishState publishState;

    @NotBlank(message = "文章渲染类型不允许为空")
    private PostRenderType postRenderType;

    private String author;

    //封面图片
    private String coverImg;

    @NotBlank(message = "文章标题不允许为空")
    private String title;


    @NotBlank(message = "文章内容不允许为空")
    private String content;

    //简介
    private String degest;


    private String category;

    @NotBlank(message = "分类不允许为空")
    private int categoryId;

    private int likes;


    private int viewNum;

    private int commentNum;

    private int[] tagIds;

    private List<Tag> tags;

    private Post prev;

    private Post next;
}

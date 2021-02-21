package com.blog.api.dto;

import com.blog.api.common.anotation.Field;
import com.blog.api.enums.PublishState;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto extends BaseDto{


    private PublishState publishState;

    private String author;

    //封面图片
    private String coverImg;

    private String title;

    private String content;

    //简介
    private String degest;

    private String category;

    private long categoryId;

    private int likes;


    private int viewNum;

    private int commentNum;
}

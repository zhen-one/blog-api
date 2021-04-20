package com.blog.api.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostListDto {

    private Object id;
    private Object createdAt;
    private Object category;
    private Object view_num;
    private Object commentNum;
    private Object degest;
    private Object likes;
    private Object title;
    private Object coverImg;
    private List<String> tagNames;

}

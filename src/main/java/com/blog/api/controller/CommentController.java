package com.blog.api.controller;

import com.blog.api.common.response.PageResult;
import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.dto.CommentDto;
import com.blog.api.dto.PostDto;
import com.blog.api.model.Comment;
import com.blog.api.model.Post;
import com.blog.api.model.Tag;
import com.blog.api.service.CategoryService;
import com.blog.api.service.CommentService;
import com.blog.api.service.PostService;
import com.blog.api.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

@io.swagger.annotations.Api(value = "文章管理")
@RestController
@RequestMapping("/api/comment")
public class CommentController extends BaseController<CommentDto, Comment> {


    @Autowired
    private CommentService commentService;


    @Autowired
    public CommentController(CommentService commentService) {
        super.baseService = commentService;
    }



}

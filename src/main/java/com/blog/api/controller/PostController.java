package com.blog.api.controller;

import com.blog.api.common.response.PageResult;
import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.dto.ApiDto;
import com.blog.api.dto.PermissionDto;
import com.blog.api.dto.PostDto;
import com.blog.api.model.Api;
import com.blog.api.model.Post;
import com.blog.api.service.ApiService;
import com.blog.api.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@io.swagger.annotations.Api(value = "文章管理")
@RestController
@RequestMapping("/api/post")
public class PostController extends BaseController<PostDto, Post> {


    @Autowired
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        super.baseService = postService;
    }


    @GetMapping("/publicList")
    public ResponseResult<PageResult<PostDto>> getPublicPosts(
            @PageableDefault(page = 0, value = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC)
                    Pageable pageRequest, @RequestParam Map<String, Object> dto) {


        var newPage =
                PageRequest.of(Math.max(pageRequest.getPageNumber() - 1, 0), pageRequest.getPageSize(), pageRequest.getSort());

        dto.put("publishState", "Published");

        var page = postService.getPageList(dto, newPage);

        var res = PageResult.toPageResult(page.map(n -> (PostDto) dozerMapper.map(n, PostDto.class)));

        return ResponseUtil.success(res);
    }


}

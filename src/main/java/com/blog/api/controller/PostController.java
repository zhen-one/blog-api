package com.blog.api.controller;

import com.blog.api.common.response.PageResult;
import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.dto.ApiDto;
import com.blog.api.dto.PermissionDto;
import com.blog.api.dto.PostDto;
import com.blog.api.dto.RoleDto;
import com.blog.api.enums.PublishState;
import com.blog.api.model.Api;
import com.blog.api.model.Post;
import com.blog.api.model.Role;
import com.blog.api.model.Tag;
import com.blog.api.service.ApiService;
import com.blog.api.service.CategoryService;
import com.blog.api.service.PostService;
import com.blog.api.service.TagService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.*;

@io.swagger.annotations.Api(value = "文章管理")
@RestController
@RequestMapping("/api/post")
public class PostController extends BaseController<PostDto, Post> {


    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    public PostController(PostService postService) {
        super.baseService = postService;
    }


    /**
     * 获取文章详情
     *
     * @param id
     * @return
     * @throws NotFoundException
     */
    @Override
    public ResponseResult<PostDto> get(@PathVariable("id") int id) throws NotFoundException {
        var post = postService.getById(id);
        post.setViewNum(post.getViewNum()+1);
        postService.edit(post);
        PostDto postDto = dozerMapper.map(post, PostDto.class);
        var prev = postService.getPrev(id);
        var next = postService.getNext(id);
        postDto.setPrev(prev);
        postDto.setNext(next);
        return ResponseUtil.success(postDto);

    }

    @GetMapping("/public/list")
    public ResponseResult<PageResult<PostDto>> getPublicPosts(
            @PageableDefault(page = 0, value = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC)
                    Pageable pageRequest, @RequestParam Map<String, Object> dto) {


        var newPage =
                PageRequest.of(Math.max(pageRequest.getPageNumber() - 1, 0), pageRequest.getPageSize(), pageRequest.getSort());

        dto.put("publishState", PublishState.Published);

        var page = postService.getPageList(dto, newPage);

        var res = PageResult.toPageResult(page.map(n -> (PostDto) dozerMapper.map(n, PostDto.class)));

        return ResponseUtil.success(res);
    }


    @Override
    @Transactional
    @PostMapping("/add")
    public ResponseResult<PostDto> add(@RequestBody @NotNull PostDto dto) {

        var category = categoryService.getById(dto.getCategoryId());
        dto.setCategory(category.getCategoryName());
        var post = dozerMapper.map(dto, Post.class);

        post.setTags(new HashSet<>());

        if (dto.getTagIds() != null && dto.getTagIds().length > 0) {
            var tags = new ArrayList<Tag>();
            var tagIds = dto.getTagIds();
            for (int tagId : tagIds) {
                var tag = tagService.getById(tagId);
                post.getTags().add(tag);
            }
        }
        return ResponseUtil.success(dozerMapper.map(postService.add(post), PostDto.class));

    }

    @Override
    @Transactional
    @PostMapping("/edit")
    public ResponseResult<PostDto> edit(@RequestBody @NotNull PostDto dto) {

        var category = categoryService.getById(dto.getCategoryId());
        dto.setCategory(category.getCategoryName());
        var post = dozerMapper.map(dto, Post.class);

        post.setTags(new HashSet<>());

        if (dto.getTagIds() != null && dto.getTagIds().length > 0) {
            var tags = new ArrayList<Tag>();
            var tagIds = dto.getTagIds();
            for (int tagId : tagIds) {
                var tag = tagService.getById(tagId);
                post.getTags().add(tag);
            }
        }
        return ResponseUtil.success(dozerMapper.map(postService.edit(post), PostDto.class));

    }

}

package com.blog.api.controller;

import com.blog.api.common.response.PageResult;
import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.dto.*;
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
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

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
//        this.postService=postService;
    }


    /**
     * 获取文章详情
     *
     * @param id
     * @return
     * @throws NotFoundException
     */
    @GetMapping("public/view/{id}")
    public ResponseResult<PostDto> view(@PathVariable("id") int id) throws NotFoundException {
        var post = postService.getById(id);
        post.setViewNum(post.getViewNum() + 1);
        postService.edit(post);
        PostDto postDto = dozerMapper.map(post, PostDto.class);
        var prev = postService.getPrev(id);
        var next = postService.getNext(id);
        postDto.setPrev(prev);
        postDto.setNext(next);
        return ResponseUtil.success(postDto);

    }

    @GetMapping("/getNew/{id}")
    public ResponseResult<PostDto> getNew(@PathVariable("id") int id) throws NotFoundException {
        var post = postService.getById(id);
        post.setViewNum(post.getViewNum() + 1);
        postService.edit(post);
        PostDto postDto = dozerMapper.map(post, PostDto.class);
        var prev = postService.getPrev(id);
        var next = postService.getNext(id);
        postDto.setPrev(prev);
        postDto.setNext(next);
        return ResponseUtil.success(postDto);

    }


    /*
     * 公开文章 时间倒叙
     * */
    @GetMapping("/public/list")
    public ResponseResult<PageResult<PostListDto>> getPublicPosts(
            @PageableDefault(page = 0, value = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC)
                    Pageable pageRequest, @RequestParam Map<String, Object> dto) {


        var newPage =
                PageRequest.of(Math.max(pageRequest.getPageNumber() - 1, 0), pageRequest.getPageSize(), pageRequest.getSort());

        dto.put("publishState", PublishState.Published);

        var page = postService.publicList(newPage);

        var res = PageResult.toPageResult(page);

        return ResponseUtil.success(res);
    }

    /*
     * 分类分章
     * */
    @GetMapping("/public/listByCategory/{category}")
    public ResponseResult<PageResult<PostListDto>> getPublicPostsByCategory(
            @PageableDefault(page = 0, value = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC)
                    Pageable pageRequest, @PathVariable String category) {

        var newPage =
                PageRequest.of(Math.max(pageRequest.getPageNumber() - 1, 0), pageRequest.getPageSize(), pageRequest.getSort());

        var page = postService.publicListByCategory(newPage, category);

        var res = PageResult.toPageResult(page);

        return ResponseUtil.success(res);
    }

    /*
     * 标签文章
     * */
    @GetMapping("/public/listByTag/{tag}")
    public ResponseResult<PageResult<PostListDto>> getPublicPostsByTag(
            @PageableDefault(page = 0, value = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC)
                    Pageable pageRequest, @PathVariable String tag) {

        var newPage =
                PageRequest.of(Math.max(pageRequest.getPageNumber() - 1, 0), pageRequest.getPageSize(), pageRequest.getSort());
        var page = postService.publicListByTag(newPage,tag);

        var res = PageResult.toPageResult(page);

        return ResponseUtil.success(res);
    }


    /*
     * 文章归档
     * */
    @GetMapping("/public/archive")
    public ResponseResult<Map<String, Object>> archive() {

        List<PostDto> list = postService.getArchive();
        List<CategoryDto> categorys = categoryService.getCategories();
        List<TagDto> tags = tagService.getTags();
        Integer postCount = postService.getPostCount();

        Map<Integer, List<PostDto>> yearGroup = list.stream()
                .sorted((n1, n2) -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(n1.getCreatedAt());
                    int year1 = cal.get(Calendar.YEAR);
                    cal.setTime(n2.getCreatedAt());
                    int year2 = cal.get(Calendar.YEAR);
                    return Integer.valueOf(year2).compareTo(Integer.valueOf(year1));
                })
                .collect(Collectors.groupingBy(n -> {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(n.getCreatedAt());
                    int year = cal.get(Calendar.YEAR);
                    return Integer.valueOf(year);
                }));
        List<Map<Integer, Map<Integer, List<PostDto>>>> yearList = new ArrayList<>();
        List<Map<String, Object>> data = new ArrayList<>();

        for (var key : yearGroup.keySet()) {
            List<PostDto> yearDtos = yearGroup.get(key);
            Map<Integer, Map<Integer, List<PostDto>>> yearMonthGroup = new HashMap<>();
            Map<String, Object> dataYear = new HashMap<>();
            Map<Integer, List<PostDto>> monthGroup = yearDtos.stream()
                    .sorted((n1, n2) -> {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(n1.getCreatedAt());
                        int month1 = cal.get(Calendar.MONTH);
                        cal.setTime(n2.getCreatedAt());
                        int month2 = cal.get(Calendar.MONTH);
                        return Integer.valueOf(month2).compareTo(Integer.valueOf(month1));
                    })
                    .collect(Collectors.groupingBy(n -> {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(n.getCreatedAt());
                        int month = cal.get(Calendar.MONTH) + 1;
                        return Integer.valueOf(month);
                    }));

            List<Map<String, Object>> monthList = new ArrayList<>();
            for (var monthKey : monthGroup.keySet()) {
                List<PostDto> monthDtos = monthGroup.get(monthKey);
                Map<String, Object> dataMonth = new HashMap<>();
                dataMonth.put("month", monthKey);
                dataMonth.put("data", monthDtos);
                monthList.add(dataMonth);
            }
            dataYear.put("year", key);
            dataYear.put("data", monthList);
            yearMonthGroup.put(key, monthGroup);
            yearList.add(yearMonthGroup);
            data.add(dataYear);

        }
        Map<String, Object> resultMap = new HashMap<>();

        resultMap.put("archive", data);
        resultMap.put("categories", categorys);
        resultMap.put("tags", tags);
        resultMap.put("postCount", postCount);
        return ResponseUtil.success(resultMap);
    }

    @Override
    @PostMapping("/add")
    public ResponseResult<PostDto> add(@RequestBody @NotNull PostDto dto) {

        var category = categoryService.getById(dto.getCategoryId());
        dto.setCategory(category.getCategoryName());
        var post = dozerMapper.map(dto, Post.class);
        post=postService.add(post);
        List<String> tagNames = new ArrayList<>();
        tagService.clearPostTag(post.getId());
        if (dto.getTagIds() != null && dto.getTagIds().length > 0) {
            var tags = new ArrayList<Tag>();
            var tagIds = dto.getTagIds();
            for (int tagId : tagIds) {
                tagService.bindPostTag(tagId, post.getId());
                var tag = tagService.getById(tagId);
                tagNames.add(tag.getTagName());
            }
        }
        post.setTagNames(String.join(",", tagNames));
        return ResponseUtil.success(dozerMapper.map(postService.edit(post), PostDto.class));

    }

    @Override
    @PostMapping("/edit")
    public ResponseResult<PostDto> edit(@RequestBody @NotNull PostDto dto) {

        var category = categoryService.getById(dto.getCategoryId());
        dto.setCategory(category.getCategoryName());
        var post = dozerMapper.map(dto, Post.class);
        tagService.clearPostTag(post.getId());
        List<String> tagNames = new ArrayList<>();

        if (dto.getTagIds() != null && dto.getTagIds().length > 0) {
            var tags = new ArrayList<Tag>();
            var tagIds = dto.getTagIds();
            for (int tagId : tagIds) {
                var tag = tagService.getById(tagId);
                tagService.bindPostTag(tagId, post.getId());
                tagNames.add(tag.getTagName());
            }
        }
        post.setTagNames(String.join(",", tagNames));
        return ResponseUtil.success(dozerMapper.map(postService.edit(post), PostDto.class));

    }


    @PostMapping("public/{id}/like")
    public ResponseResult<Number> likeComment(@PathVariable("id") int id) {
        var post = postService.getById(id);
        if (post != null) {
            post.setLikes(post.getLikes() + 1);
            int likes = postService.like(id);
            return ResponseUtil.success(likes);
        } else {
            return ResponseUtil.fail("文章已不存在！");
        }
    }

}

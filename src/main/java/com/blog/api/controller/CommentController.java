package com.blog.api.controller;

import com.blog.api.common.response.PageResult;
import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.common.util.IPUtil;
import com.blog.api.dto.CommentDto;
import com.blog.api.enums.PublishState;
import com.blog.api.model.Comment;
import com.blog.api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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


    @Override
    public ResponseResult<CommentDto> add(@RequestBody @NotNull CommentDto dto) {
        if (getCurrentUser() != null) {
            dto.setSysUserCreated(true);
        }
        dto.setIp(super.getRemortIP());
        dto.setIp_location(IPUtil.parseIP(dto.getIp()));
        String userAgent = super.getRequest().getHeader("User-Agent");
        dto.setEquipment(IPUtil.parseUserAgent(userAgent).toString());
        var newComment = commentService.addNewComment(dozerMapper.map(dto, Comment.class));
        return ResponseUtil.success(dozerMapper.map(newComment, CommentDto.class));
    }


    @GetMapping("/public/list")
    public ResponseResult<PageResult<CommentDto>> getPublicPosts(
            @PageableDefault(page = 0, value = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC)
                    Pageable pageRequest, @RequestParam Map<String, Object> dto) {


        var newPage =
                PageRequest.of(Math.max(pageRequest.getPageNumber() - 1, 0), pageRequest.getPageSize(), pageRequest.getSort());

        dto.put("publishState", PublishState.Published);

        dto.put("parentId", 0);

        var page = commentService.getPageList(dto, newPage);


        var commentDtos = page.map(n -> (CommentDto) dozerMapper.map(n, CommentDto.class));

        commentDtos.forEach(comment -> {
            var subList = commentService.getSubList(comment.getId());
            comment.setSub(subList);
        });
        var res = PageResult.toPageResult(commentDtos);

        return ResponseUtil.success(res);
    }

    @PostMapping("public/{id}/like")
    public ResponseResult<Number> likeComment(@PathVariable("id") int id) {
        var comment = commentService.getById(id);
        if (comment != null) {
            int likes = commentService.like(id);
            return ResponseUtil.success(likes);
        } else {
            return ResponseUtil.fail("评论已不存在！");
        }
    }


}

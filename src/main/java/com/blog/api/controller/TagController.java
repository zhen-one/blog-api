package com.blog.api.controller;

import com.blog.api.common.response.ResponseResult;
import com.blog.api.dto.ApiDto;
import com.blog.api.dto.CategoryDto;
import com.blog.api.dto.TagDto;
import com.blog.api.model.Api;
import com.blog.api.model.Tag;
import com.blog.api.service.ApiService;
import com.blog.api.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@io.swagger.annotations.Api(value = "接口管理")
@RestController
@RequestMapping("/api/tag")
public class TagController extends BaseController<TagDto, Tag>{


    @Autowired
    private TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        super.baseService=tagService;
    }



    @RequestMapping(value = "all", method = RequestMethod.GET)
    @Override
    public ResponseResult<List<TagDto>> getAll() {
        return super.getAll();
    }

}

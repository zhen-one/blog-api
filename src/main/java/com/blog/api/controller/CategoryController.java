package com.blog.api.controller;

import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.dto.CategoryDto;
import com.blog.api.dto.TagDto;
import com.blog.api.model.Category;
import com.blog.api.model.Comment;
import com.blog.api.model.Tag;
import com.blog.api.service.CategoryService;
import com.blog.api.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@io.swagger.annotations.Api(value = "接口管理")
@RestController
@RequestMapping("/api/category")
public class CategoryController extends BaseController<CategoryDto, Category> {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        super.baseService = categoryService;
    }


    @RequestMapping(value = "all", method = RequestMethod.GET)
    @Override
    public ResponseResult<List<CategoryDto>> getAll() {

        return super.getAll();
    }


    @RequestMapping(value = "names", method = RequestMethod.GET)
    public ResponseResult<List<String>> getNames() {

        return ResponseUtil.success(
                baseService.getEnabledList().stream().map(n -> n.getCategoryName()).collect(Collectors.toList()));
    }


    @RequestMapping(value = "public/collect", method = RequestMethod.GET)
    public ResponseResult<List<CategoryDto>> categoryCollect() {

        return ResponseUtil.success(
                categoryService.getCategories());
    }

}

package com.blog.api.service;

import com.blog.api.dto.CategoryDto;
import com.blog.api.model.Category;
import com.blog.api.repo.CategoryRepository;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService extends BaseService<Category, Integer> {

    private CategoryRepository dal;

    @Autowired
    public CategoryService(CategoryRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }


    public List<CategoryDto> getCategories() {
        var list = dal.getCategories();
        return list.stream().map(n -> {
            var dto = new CategoryDto();
            dto.setId(Integer.valueOf(n.get("id").toString()));
            dto.setCategoryName(n.get("category_name").toString());
            dto.setPostCount(Integer.valueOf(n.get("post_count").toString()));
            return dto;
        }).collect(Collectors.toList());
    }


}

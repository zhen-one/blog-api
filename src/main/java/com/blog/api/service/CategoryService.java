package com.blog.api.service;

import com.blog.api.model.Category;
import com.blog.api.repo.CategoryRepository;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoryService extends BaseService<Category, Integer> {

    private CategoryRepository dal;

    @Autowired
    public CategoryService(CategoryRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }

   




}

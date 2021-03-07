package com.blog.api.service;

import cn.hutool.core.util.StrUtil;
import com.blog.api.model.Api;
import com.blog.api.model.Tag;
import com.blog.api.repo.ApiRepository;
import com.blog.api.repo.TagRepository;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Service
public class TagService extends BaseService<Tag, Integer> {

    private TagRepository dal;

    @Autowired
    public TagService(TagRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }








}

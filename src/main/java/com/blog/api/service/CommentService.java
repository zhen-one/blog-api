package com.blog.api.service;

import cn.hutool.core.util.StrUtil;
import com.blog.api.model.Api;
import com.blog.api.model.Comment;
import com.blog.api.repo.ApiRepository;
import com.blog.api.repo.CommentRepository;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Service
public class CommentService extends BaseService<Comment, Integer> {

    private CommentRepository dal;

    @Autowired
    public CommentService(CommentRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }




}

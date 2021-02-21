package com.blog.api.service;

import cn.hutool.core.util.StrUtil;
import com.blog.api.model.Api;
import com.blog.api.model.Post;
import com.blog.api.repo.ApiRepository;
import com.blog.api.repo.PostRepository;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Service
public class PostService extends BaseService<Post, Integer> {

    private PostRepository dal;

    @Autowired
    public PostService(PostRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }

//    public List<Api> getPublicPosts(Post params, Pageable pageable) {
//        Specification<Api> specification = (Specification<Api>) (root, criteriaQuery, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况
//
//            if (!StrUtil.isBlank(url))
//                predicates.add(cb.like(root.get("url"), "%" + url + "%"));
//
//            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
//        };
//        return super.getlistByWhere(specification);
//    }




}

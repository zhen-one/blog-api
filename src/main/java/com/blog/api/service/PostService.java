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


    public Post getPrev(int id){
        return dal.getPrev(id);
    }

    public Post getNext(int id){
        return dal.getNext(id);
    }



}

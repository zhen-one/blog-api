package com.blog.api.service;

import cn.hutool.core.util.StrUtil;
import com.blog.api.enums.ComemntModuleType;
import com.blog.api.model.Api;
import com.blog.api.model.Comment;
import com.blog.api.model.Post;
import com.blog.api.repo.ApiRepository;
import com.blog.api.repo.CommentRepository;
import com.blog.api.repo.PostRepository;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class CommentService extends BaseService<Comment, Integer> {

    private CommentRepository dal;

    @Autowired
    private PostRepository postRepository;


    @Autowired
    public CommentService(CommentRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }

    public List<Comment> getSubList(int id){
        return  dal.getSubList(id);
    }


    @Transactional
    public Comment AddNewComment(Comment comment){
        super.add(comment);
        if(comment.getComemntModuleType()== ComemntModuleType.post){
            Post post=postRepository.getOne(comment.getModuleId());
            post.setCommentNum(post.getCommentNum()+1);
            postRepository.save(post);
        }
       return comment;
    }




}

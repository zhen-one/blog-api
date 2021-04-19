package com.blog.api.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.blog.api.dto.PostDto;
import com.blog.api.model.Api;
import com.blog.api.model.Post;
import com.blog.api.repo.ApiRepository;
import com.blog.api.repo.PostRepository;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostService extends BaseService<Post, Integer> {

    private PostRepository dal;

    @Autowired
    public PostService(PostRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }


    public Post getPrev(int id) {
        return dal.getPrev(id);
    }

    public Post getNext(int id) {
        return dal.getNext(id);
    }

    @Transactional
    public int like(int id) {
        dal.like(id);
        return dal.findById(id).get().getLikes();
    }

//    @PersistenceContext
//    EntityManager manager;
//
//    List<Post> query(Set<Integer> deptIds) {
//        CriteriaBuilder cb = manager.getCriteriaBuilder();
//        CriteriaQuery<User> q = cb.createQuery(User.class);
//        Root<User> r = q.from(User.class);
//        Join<User, Department> j = r.join("departments", JoinType.LEFT);
//        q.select(r).where(j.get("id").in(deptIds)).orderBy(cb.asc(r.get("time")));
//        return manager.createQuery(q).getResultList();
//    }


    public Page<Post> listByTag(String tagName, Pageable pageable) {
        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = pageable.getPageNumber() + 1 * pageable.getPageSize();
        List<Integer> postIds = dal.getPostIdsByTag(start, end, tagName);
        var list = postIds.stream().map(n -> super.getById(n)).collect(Collectors.toList());
//        List<Post> list = dal.getListByids(postIds);
        var page = new PageImpl<Post>(list, pageable, 10);
        return page;
    }

    public List<PostDto> getArchive() {
        var maps = this.dal.getArchive();
        return maps.stream().map(n -> {
            var dto = new PostDto();
            dto.setId(Convert.toInt(n.get("id")));
            dto.setCreatedAt(Convert.toDate(n.get("created_at")));
            dto.setTitle(n.get("title"));
            dto.setCoverImg(n.get("cover_img"));
            return dto;
        }).collect(Collectors.toList());
    }


    public Integer getPostCount() {
        return dal.getPostCount();
    }
}

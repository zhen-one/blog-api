package com.blog.api.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.blog.api.dto.PostDto;
import com.blog.api.dto.PostListDto;
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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class PostService extends BaseService<Post, Integer> {

    @Autowired
    private PostRepository dal;

    @Autowired
    public PostService(PostRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }


    public PostListDto getPrev(int id) {

        var map = dal.getPrev(id);
        if(map.size()==0)return null;
        PostListDto postListDto = new PostListDto();
        postListDto.setId(map.get("id"));
        postListDto.setCreatedAt(map.get("created_at"));
        postListDto.setCategory(map.get("category"));
        postListDto.setCommentNum(map.get("comment_num"));
        postListDto.setCoverImg(map.get("cover_img"));
        postListDto.setTitle(map.get("title"));
        postListDto.setView_num(map.get("view_num"));
        postListDto.setLikes(map.get("likes"));
        postListDto.setTitle(map.get("title"));
        postListDto.setDegest(map.get("degest"));
        postListDto.setTagNames(Arrays.asList(map.get("tag_names").toString().split(",")));
        return postListDto;
    }

    public PostListDto getNext(int id) {
        var map = dal.getNext(id);
        if(map.size()==0)return null;
        PostListDto postListDto = new PostListDto();
        postListDto.setId(map.get("id"));
        postListDto.setCreatedAt(map.get("created_at"));
        postListDto.setCategory(map.get("category"));
        postListDto.setCommentNum(map.get("comment_num"));
        postListDto.setCoverImg(map.get("cover_img"));
        postListDto.setTitle(map.get("title"));
        postListDto.setView_num(map.get("view_num"));
        postListDto.setLikes(map.get("likes"));
        postListDto.setTitle(map.get("title"));
        postListDto.setDegest(map.get("degest"));
        postListDto.setTagNames(Arrays.asList(map.get("tag_names").toString().split(",")));
        return postListDto;
    }

//    @Transactional
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


//    public Page<Post> listByTag(String tagName, Pageable pageable) {
//        int start = pageable.getPageNumber() * pageable.getPageSize();
//        int end = pageable.getPageNumber() + 1 * pageable.getPageSize();
//        List<Integer> postIds = dal.getPostIdsByTag(start, end, tagName);
//        var list = postIds.stream().map(n -> super.getById(n)).collect(Collectors.toList());
////        List<Post> list = dal.getListByids(postIds);
//        int total=dal.getPostCountByTag(tagName);
//        var page = new PageImpl<Post>(list, pageable, total);
//        return page;
//    }

    public Page<PostListDto> publicList(Pageable pageable) {
        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = pageable.getPageNumber() + 1 * pageable.getPageSize();
        List<Map<String, Object>> maps = dal.getPublicPost(start, end);
        List<PostListDto> posts = new ArrayList<>();
        maps.stream().forEach(n -> {
            PostListDto postListDto = new PostListDto();
            postListDto.setId(n.get("id"));
            postListDto.setCreatedAt(n.get("created_at"));
            postListDto.setCategory(n.get("category"));
            postListDto.setCommentNum(n.get("comment_num"));
            postListDto.setCoverImg(n.get("cover_img"));
            postListDto.setTitle(n.get("title"));
            postListDto.setView_num(n.get("view_num"));
            postListDto.setLikes(n.get("likes"));
            postListDto.setTitle(n.get("title"));
            postListDto.setDegest(n.get("degest"));
            postListDto.setTagNames(Arrays.asList(n.get("tag_names").toString().split(",")));
            posts.add(postListDto);

        });
        int total = dal.getPublicPostCount();
//        List<Post> list = dal.getListByids(postIds);
        var page = new PageImpl<PostListDto>(posts, pageable, total);
        return page;
    }

    public Page<PostListDto> publicListByCategory(Pageable pageable,String category) {
        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = pageable.getPageNumber() + 1 * pageable.getPageSize();
        List<Map<String, Object>> maps = dal.getPublicPostByCategory(start, end,category);
        List<PostListDto> posts = new ArrayList<>();
        maps.stream().forEach(n -> {
            PostListDto postListDto = new PostListDto();
            postListDto.setId(n.get("id"));
            postListDto.setCreatedAt(n.get("created_at"));
            postListDto.setCategory(n.get("category"));
            postListDto.setCommentNum(n.get("comment_num"));
            postListDto.setCoverImg(n.get("cover_img"));
            postListDto.setTitle(n.get("title"));
            postListDto.setView_num(n.get("view_num"));
            postListDto.setLikes(n.get("likes"));
            postListDto.setTitle(n.get("title"));
            postListDto.setDegest(n.get("degest"));
            postListDto.setTagNames(Arrays.asList(n.get("tag_names").toString().split(",")));
            posts.add(postListDto);

        });
        int total = dal.getPublicPostCount();
//        List<Post> list = dal.getListByids(postIds);
        var page = new PageImpl<PostListDto>(posts, pageable, total);
        return page;
    }


    public Page<PostListDto> publicListByTag(Pageable pageable,String tagName) {
        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = pageable.getPageNumber() + 1 * pageable.getPageSize();
        List<Map<String, Object>> maps = dal.getPublicPostByTag(start, end,tagName);
        List<PostListDto> posts = new ArrayList<>();
        maps.stream().forEach(n -> {
            PostListDto postListDto = new PostListDto();
            postListDto.setId(n.get("id"));
            postListDto.setCreatedAt(n.get("created_at"));
            postListDto.setCategory(n.get("category"));
            postListDto.setCommentNum(n.get("comment_num"));
            postListDto.setCoverImg(n.get("cover_img"));
            postListDto.setTitle(n.get("title"));
            postListDto.setView_num(n.get("view_num"));
            postListDto.setLikes(n.get("likes"));
            postListDto.setTitle(n.get("title"));
            postListDto.setDegest(n.get("degest"));
            postListDto.setTagNames(Arrays.asList(n.get("tag_names").toString().split(",")));
            posts.add(postListDto);

        });
        int total = dal.getPublicPostCount();
//        List<Post> list = dal.getListByids(postIds);
        var page = new PageImpl<PostListDto>(posts, pageable, total);
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

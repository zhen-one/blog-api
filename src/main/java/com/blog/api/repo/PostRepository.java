package com.blog.api.repo;

import com.blog.api.model.Api;
import com.blog.api.model.Post;
import com.blog.api.repo.base.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface PostRepository extends BaseRepository<Post, Integer> {

    @Query(value = "select * from Post p where p.id<:id  order by p.id desc  limit 1", nativeQuery = true)
    Post getPrev(@Param("id") int id);

    @Query(value = "select  * from Post p where p.id>:id order by p.id asc  limit 1", nativeQuery = true)
    Post getNext(@Param("id") int id);

    @Modifying
    @Query(value = "update Post  set likes=likes+1 where Post.id=:id ", nativeQuery = true)
    public void like(@Param("id") int id);


    @Query(value = "select p from Post p   " +
            "where p.id in:ids ")
    public List<Post> getListByids(@Param("ids") List<Integer> ids);

    @Query(value = "select post.id from post \n" +
            "join post_tag_rel on post.id=post_tag_rel.post_id\n" +
            "join tag on post_tag_rel.tag_id=tag.id\n" +
            "where post.publish_state='Published'\n" +
            "and tag.tag_name=:tagName  limit :start,:end", nativeQuery = true)
    public List<Integer> getPostIdsByTag(@Param("start") int start, @Param("end") int end, @Param("tagName") String tagName);


    @Query(value = "select id,title,cover_img,created_at from post where publish_state='Published' order by created_at desc ", nativeQuery = true)
    public List<Map<String, String>> getArchive();

    @Query(value = "select count(1) from post where publish_state='Published'  ", nativeQuery = true)
    public Integer getPostCount();
}



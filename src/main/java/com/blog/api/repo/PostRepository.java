package com.blog.api.repo;

import com.blog.api.dto.PostListDto;
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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


@Repository
public interface PostRepository extends BaseRepository<Post, Integer> {

    @Query(value = "select id,created_at,category,tag_names,comment_num,degest,likes,cover_img,title,view_num from Post p  where p.publish_state='Published' and  p.id<:id  order by p.id desc  limit 1", nativeQuery = true)
    Map<String,Object> getPrev(@Param("id") int id);

    @Query(value = "select id,created_at,category,tag_names,comment_num,degest,likes,cover_img,title,view_num from Post p  where p.publish_state='Published' and p.id>:id order by p.id asc  limit 1", nativeQuery = true)
    Map<String,Object> getNext(@Param("id") int id);

    @Transactional
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
    public List<Integer> getPostIds(@Param("start") int start, @Param("end") int end, @Param("tagName") String tagName);

    @Query(value = "select post.id from post \n" +
            "join post_tag on post.id=post_tag.post_id\n" +
            "join tag on post_tag.tag_id=tag.id\n" +
            "where post.publish_state='Published'\n" +
            "and tag.tag_name=:tagName  order by post.created_at desc  limit :start,:end ", nativeQuery = true)
    public List<Integer> getPostIdsByTag(@Param("start") int start, @Param("end") int end, @Param("tagName") String tagName);

    @Query(value = "select count(1) from post\n" +
            "join post_tag on post.id=post_tag.post_id \n" +
            "join tag on post_tag.tag_id=tag.id\n" +
            "where post.publish_state='Published' and tag.tag_name=:tagName ",
           nativeQuery = true)
    public Integer getPostCountByTag(@Param("tagName") String tagName);

    @Query(value = "select id,created_at,category,tag_names,comment_num,degest,likes,cover_img,title,view_num from post\n" +
            "where publish_state='Published' order by created_at desc   limit :start,:end", nativeQuery = true)
    public List<Map<String,Object>> getPublicPost(@Param("start") int start, @Param("end") int end);

    @Query(value = "select id,created_at,category,tag_names,comment_num,degest,likes,cover_img,title,view_num from post\n" +
            "where publish_state='Published' and category=:category order by created_at desc   limit :start,:end", nativeQuery = true)
    public List<Map<String,Object>> getPublicPostByCategory(@Param("start") int start, @Param("end") int end,@Param("category") String category);


    @Query(value = "select  id,created_at,category,tag_names,comment_num,degest,likes,cover_img,title,view_num from post where " +
            "post.id in (" +
            " select post.id from post \n" +
            "join post_tag on post.id=post_tag.post_id\n" +
            "join tag on post_tag.tag_id=tag.id\n" +
            "where post.publish_state='Published'\n" +
            "and tag.tag_name=:tagName  order by post.created_at desc ) limit :start,:end  ", nativeQuery = true)
    public List<Map<String,Object>> getPublicPostByTag(@Param("start") int start, @Param("end") int end, @Param("tagName") String tagName);


    @Query(value = "select count(1) from post\n" +
            "where publish_state='Published'  ",nativeQuery = true)
    public Integer getPublicPostCount();

//    @Query(value = "select id,created_at,category,comment_num,degest,likes,cover_img,title,view_num from post\n" +
//            "where publish_state='Published' and category=:category order by created_at desc   limit :start,:end", nativeQuery = true)
//    public List<Integer> getPostByCategory(@Param("start") int start, @Param("end") int end, @Param("category") String tagName);


    @Query(value = "select id,title,cover_img,created_at from post where publish_state='Published' order by created_at desc ", nativeQuery = true)
    public List<Map<String, String>> getArchive();

    @Query(value = "select count(1) from post where publish_state='Published'  ", nativeQuery = true)
    public Integer getPostCount();
}



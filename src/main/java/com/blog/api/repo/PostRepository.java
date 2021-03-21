package com.blog.api.repo;

import com.blog.api.model.Api;
import com.blog.api.model.Post;
import com.blog.api.repo.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends BaseRepository<Post, Integer> {

    @Query(value = "select * from Post p where p.id<:id  order by p.id desc  limit 1", nativeQuery = true)
    Post getPrev(@Param("id") int id);

    @Query(value = "select  * from Post p where p.id>:id order by p.id asc  limit 1", nativeQuery = true)
    Post getNext(@Param("id") int id);
}



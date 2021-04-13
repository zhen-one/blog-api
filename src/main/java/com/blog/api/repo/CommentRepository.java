package com.blog.api.repo;

import com.blog.api.model.Api;
import com.blog.api.model.Comment;
import com.blog.api.repo.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends BaseRepository<Comment, Integer> {

    @Query("select c from Comment c where c.parentId = :id and publishState = 'Published' order by createdAt asc ")
    public List<Comment> getSubList(@Param("id") int id);


    @Modifying
    @Query(value = "update Comment c set likes=likes+1 where c.id=:id ",nativeQuery = true)
    public void like(@Param("id") int id);
}

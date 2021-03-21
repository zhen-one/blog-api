package com.blog.api.repo;

import com.blog.api.model.Api;
import com.blog.api.model.Comment;
import com.blog.api.repo.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends BaseRepository<Comment, Integer> {


}

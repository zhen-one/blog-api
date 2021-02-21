package com.blog.api.repo;

import com.blog.api.model.Api;
import com.blog.api.model.Post;
import com.blog.api.repo.base.BaseRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends BaseRepository<Post, Integer> {
}

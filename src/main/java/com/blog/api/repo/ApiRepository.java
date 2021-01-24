package com.blog.api.repo;

import com.blog.api.model.Api;
import com.blog.api.model.Permission;
import com.blog.api.repo.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRepository extends BaseRepository<Api, Integer> {
}

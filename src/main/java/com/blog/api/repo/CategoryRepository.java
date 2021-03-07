package com.blog.api.repo;

import com.blog.api.model.Category;
import com.blog.api.model.Tag;
import com.blog.api.repo.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Integer> {


}

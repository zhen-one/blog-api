package com.blog.api.repo;

import com.blog.api.dto.CategoryDto;
import com.blog.api.model.Category;
import com.blog.api.model.Tag;
import com.blog.api.repo.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Integer> {


    @Query(value="select category.id,category.category_name, count(1) as post_count from category \n" +
            "inner join post on category.id=post.category_id\n" +
            "where post.publish_state='Published'\n" +
            "group by category.id,category.category_name\n",nativeQuery = true)
    List<Map<String,Object>>  getCategories();

}

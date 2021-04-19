package com.blog.api.repo;

import com.blog.api.model.Tag;
import com.blog.api.repo.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TagRepository extends BaseRepository<Tag, Integer> {

    @Query(value="select tag.id,tag.tag_name, count(1) as post_count from tag join post_tag_rel  on tag.id=post_tag_rel.tag_id\n" +
            "join post on post_tag_rel.post_id=post.id\n" +
            "where post.publish_state='Published'\n" +
            "group by tag.id,tag.tag_name\n" +
            "order by count(1) desc",nativeQuery = true)
    List<Map<String,Object>> getTags();

}

package com.blog.api.repo;

import com.blog.api.model.Tag;
import com.blog.api.repo.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface TagRepository extends BaseRepository<Tag, Integer> {

    @Query(value="select tag.id,tag.tag_name, count(1) as post_count from tag join post_tag  on tag.id=post_tag.tag_id\n" +
            "join post on post_tag.post_id=post.id\n" +
            "where post.publish_state='Published'\n" +
            "group by tag.id,tag.tag_name\n" +
            "order by count(1) desc",nativeQuery = true)
    List<Map<String,Object>> getTags();

    @Transactional
    @Modifying
    @Query(value="delete from  post_Tag where post_Id=:postId",nativeQuery = true)
    void clearPostTag(@Param("postId") int postId);

    @Transactional
    @Modifying
    @Query(value="insert into post_Tag(tag_id,post_id) values (:tagId,:postId)",nativeQuery = true)
    void bindPostTag(@Param("tagId") int tagId,@Param("postId") int postId);



}

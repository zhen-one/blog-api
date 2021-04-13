package com.blog.api.repo;

import com.blog.api.model.OauthUser;
import com.blog.api.model.SysUser;
import com.blog.api.repo.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface OauthUserRepository extends BaseRepository<OauthUser, Integer> {




    @Query(value = "select u from OauthUser u where uuid=?1 and source=?2")
    OauthUser getUser(String uuid,String source);

}

package com.blog.api.repo;

import com.blog.api.model.Api;
import com.blog.api.model.Permission;
import com.blog.api.repo.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiRepository extends BaseRepository<Api, Integer> {


    @Query("select a from Api a left join Permission p  on a.id =p.api where p.api is null ")
    List<Api> getAvailableList();
}

package com.blog.api.repo.base;

import com.blog.api.model.base.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
/**
 *
 * @author lvzhen
 * @date 2021/1/9
 */
@Repository
public interface BaseRepository<T extends BaseModel, ID extends Serializable> extends JpaRepository<T, ID>,
        JpaSpecificationExecutor<T> {


/*    Iterable<T> findByNameOrNameLikeOrderBySortOrderAsc(String keyword, String keyword1);

    Iterable<T> findAllByOrderByCreateTimeDesc();

    Iterable<T> findAllByOrderByCreateTimeAsc();*/

}

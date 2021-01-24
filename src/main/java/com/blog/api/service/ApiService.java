package com.blog.api.service;

import cn.hutool.core.util.StrUtil;
import com.blog.api.model.Api;
import com.blog.api.model.Permission;
import com.blog.api.model.Role;
import com.blog.api.model.UserRole;
import com.blog.api.model.base.BaseModel;
import com.blog.api.repo.ApiRepository;
import com.blog.api.repo.PermissionRepository;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Service
public class ApiService extends BaseService<Api, Integer> {

    private ApiRepository dal;

    @Autowired
    public ApiService(ApiRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }

    public List<Api> getByUrl(String url) {
        Specification<Api> specification = (Specification<Api>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况

            if (!StrUtil.isBlank(url))
                predicates.add(cb.like(root.get("apiName"), "%" + url + "%"));

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        };
        return super.getlistByWhere(specification);
    }


    @Override
    public Page<Api> getPageList(Api params, Pageable pageable) {
        Specification<Api> specification = (Specification<Api>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况

            if (!StrUtil.isBlank(params.getName()))
                predicates.add(cb.like(root.get("apiName"), "%" + params.getName() + "%"));

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        };
        return super.pageList(specification, pageable);
    }


}

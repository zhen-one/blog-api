package com.blog.api.service;

import cn.hutool.core.util.StrUtil;
import com.blog.api.common.exception.BizException;
import com.blog.api.model.Role;
import com.blog.api.model.SysUser;
import com.blog.api.repo.RoleRepository;
import com.blog.api.repo.SysUserRepository;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService extends BaseService<Role, Integer> {

    private RoleRepository dal;

    @Autowired
    public RoleService(RoleRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }
    @Override
    public void beforeAdd(Role entity) {

        Specification<Role> specification = (Specification<Role>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况

            predicates.add(cb.equal(root.get("roleName"), entity.getRoleName()));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        };
        var findRole = this.dal.findAll(specification);
        if (findRole != null&&!findRole.isEmpty())
            throw new BizException(String.format("角色[%s]已经存在!", entity.getRoleName())
            );
    }

    @Override
    public void beforeEdit(Role entity) {

        Specification<Role> specification = (Specification<Role>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况

            predicates.add(cb.notEqual(root.get("id"), entity.getId()));
            predicates.add(cb.equal(root.get("roleName"), entity.getRoleName()));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        };
        var findRole = this.dal.findAll(specification);
        if (findRole != null&&!findRole.isEmpty())
            throw new BizException(String.format("角色[%s]已经存在!", entity.getRoleName())
            );
    }

    @Override
    public Page<Role> getPageList(Role params, Pageable pageable) {
        Specification<Role> specification = (Specification<Role>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况

            if (!StrUtil.isBlank(params.getRoleName()))
                predicates.add(cb.like(root.get("roleName"), "%"+params.getRoleName()+"%"));

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        };
        return super.pageList(specification,pageable);
    }
}

package com.blog.api.service;

import cn.hutool.core.util.StrUtil;
import com.blog.api.common.exception.BizException;
import com.blog.api.model.Role;
import com.blog.api.model.UserRole;
import com.blog.api.repo.RoleRepository;
import com.blog.api.repo.UserRoleRepository;
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
public class UserRoleService extends BaseService<UserRole, Integer> {

    private UserRoleRepository dal;

    @Autowired
    public UserRoleService(UserRoleRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }
    @Override
    public void beforeAdd(UserRole entity) {

        Specification<UserRole> specification = (Specification<UserRole>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况

            predicates.add(cb.equal(root.get("roleId"), entity.getRoleId()));
            predicates.add(cb.equal(root.get("userId"), entity.getUserId()));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        };
        var findOne = this.dal.findAll(specification);
        if (findOne != null&&!findOne.isEmpty())
            throw new BizException(String.format("用户[%s]角色[%s]对应关系已经存在!", entity.getUserId(),entity.getRoleId())
            );
    }

    @Override
    public void beforeEdit(UserRole entity) {

        Specification<UserRole> specification = (Specification<UserRole>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况

            predicates.add(cb.equal(root.get("roleId"), entity.getRoleId()));
            predicates.add(cb.equal(root.get("userId"), entity.getUserId()));
            predicates.add(cb.notEqual(root.get("id"), entity.getId()));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        };
        var findOne = this.dal.findAll(specification);
        if (findOne != null&&!findOne.isEmpty())
            throw new BizException(String.format("用户[%s]角色[%s]对应关系已经存在!", entity.getUserId(),entity.getRoleId())
            );
    }

    @Override
    public Page<UserRole> getPageList(UserRole params, Pageable pageable) {
        Specification<UserRole> specification = (Specification<UserRole>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        };
        return super.pageList(specification,pageable);
    }


    public List<UserRole> getUserRoles(int userId){
        Specification<UserRole> specification = (Specification<UserRole>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况
            predicates.add(cb.equal(root.get("userId"), userId));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        };
        return super.getlistByWhere(specification);
    }
}

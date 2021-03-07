package com.blog.api.service;

import com.blog.api.common.exception.BizException;
import com.blog.api.dto.PermissionDto;
import com.blog.api.model.Api;
import com.blog.api.model.Permission;
import com.blog.api.model.Role;
import com.blog.api.model.base.BaseModel;
import com.blog.api.repo.PermissionRepository;
import com.blog.api.repo.RoleRepository;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class PermissionService extends BaseService<Permission, Integer> {

    private PermissionRepository dal;

    @Autowired
    public PermissionService(PermissionRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }



    public List<Permission> getDropdownData(int permission_id) {

        Specification<Permission> specification = (Specification<Permission>) (root, criteriaQuery, cb) -> {

            List<Predicate> predicts = new ArrayList<>();
//            if (permission_id > 0) {
//                predicts.add(cb.notEqual(root.get("id"), permission_id));
//                predicts.add(cb.notEqual(root.get("parentId"), permission_id));
//            }
//            predicts.add(cb.notEqual(root.get("type"), 3));
            return cb.and(predicts.toArray(new Predicate[predicts.size()]));

        };

        return super.getEnabledList(specification);

    }


    @Override
    protected void validated(Permission entity) {

        int parentType = 1;//导航
        Permission parentPermission = null;
        if (entity.getParentId() > 0) {
            parentPermission = super.getById(entity.getParentId());
            parentType = parentPermission.getType();
        }
        //权限类型---导航|菜单
        if (entity.getType() == 1 || entity.getType() == 2) {
            if (parentType != 1) {
                throw new BizException("导航|菜单的父级权限只能为导航类型");
            }
        }

        //权限类型---按钮
        if (entity.getType() == 3) {
            if (parentType != 2) {
                throw new BizException("按钮的父级权限只能为菜单类型");
            }
        }
    }



}

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




}

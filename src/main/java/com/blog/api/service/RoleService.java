package com.blog.api.service;

import com.blog.api.model.Role;
import com.blog.api.model.SysUser;
import com.blog.api.repo.RoleRepository;
import com.blog.api.repo.SysUserRepository;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService extends BaseService<Role, Integer> {

    private RoleRepository dal;

    @Autowired
    public RoleService(RoleRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }
}

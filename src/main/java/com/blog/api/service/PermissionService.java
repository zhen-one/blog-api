package com.blog.api.service;

import com.blog.api.model.Permission;
import com.blog.api.model.base.BaseModel;
import com.blog.api.repo.PermissionRepository;
import com.blog.api.repo.RoleRepository;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PermissionService extends BaseService {

    private PermissionRepository dal;

    @Autowired
    public PermissionService(PermissionRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }

    public List<Permission> getPermissionByUserid(int id){
        var a= new ArrayList<Permission>() ;
        var p=new Permission();
        p.setFunctionUrl("/api/user/getAll");
        a.add(p);
        return a;

    }

    public Permission getPermissionByurl(String url){
//        var p=new Permission();
//        p.setFunctionUrl("/api/user/getAll");
//        return p;
        return null;
    }

    @Override
    public Page getPageList(BaseModel params, Pageable pageable) {
        return null;
    }
}

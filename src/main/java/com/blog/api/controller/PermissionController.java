package com.blog.api.controller;

import com.blog.api.dto.ApiDto;
import com.blog.api.dto.PermissionDto;
import com.blog.api.model.Permission;
import com.blog.api.service.ApiService;
import com.blog.api.service.PermissionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "权限管理")
@RestController
@RequestMapping("/api/permission")
public class PermissionController extends BaseController<PermissionDto, Permission>{


    @Autowired
    private PermissionService permissionService;

    @Autowired
    public PermissionController(PermissionService service) {
        super.baseService=service;
    }

}

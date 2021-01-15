package com.blog.api.controller;

import com.blog.api.dto.RoleDto;
import com.blog.api.model.Role;
import com.blog.api.service.RoleService;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("permitAll()")
@RequestMapping("/api/role")
public class RoleController  extends BaseController<RoleDto, Role>{




    @Autowired
    public RoleController(RoleService service) {
        super.baseService=service;
    }
}

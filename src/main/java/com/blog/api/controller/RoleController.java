package com.blog.api.controller;

import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.dto.RoleDto;
import com.blog.api.dto.UserDto;
import com.blog.api.model.Permission;
import com.blog.api.model.Role;
import com.blog.api.model.SysUser;
import com.blog.api.service.RoleService;
import com.blog.api.service.UserRoleService;
import com.blog.api.service.base.BaseService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashSet;

@RestController
@Api(value = "角色管理")
@RequestMapping("/api/role")
public class RoleController  extends BaseController<RoleDto, Role>{


    @Autowired
    private RoleService roleService;

    @Autowired
    public RoleController(RoleService service) {
        super.baseService=service;
    }


    @PostMapping(value = "/assign")
    public ResponseResult<RoleDto> assign(@RequestBody  @NotNull  RoleDto roleDto){
        var newRole=(Role) super.dozerMapper.map(roleDto, Role.class);
        newRole.setPermissions(new HashSet<>());

        Arrays.stream(roleDto.getPermissionIds()).forEach(n -> {
            var permission=new Permission();
            permission.setId(n);
            newRole.getPermissions().add(permission);

        });
        return ResponseUtil.success(super.dozerMapper.map(roleService.edit(newRole), RoleDto.class));
    }


}

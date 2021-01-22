package com.blog.api.controller;

import com.blog.api.common.response.PageResult;
import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.dto.UserDto;
import com.blog.api.model.Role;
import com.blog.api.model.SysUser;
import com.blog.api.model.UserRole;
import com.blog.api.service.RoleService;
import com.blog.api.service.SysUserService;
import com.blog.api.service.UserRoleService;
import com.blog.api.service.base.BaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "用户接口")
@RestController
@RequestMapping("/api/user")
public class SysUserController extends BaseController<UserDto, SysUser> {

    @Autowired
    private SysUserService userService;

    @Autowired
    private UserRoleService userRoleService;


    @Autowired
    public SysUserController(SysUserService service) {
        super.baseService = service;
    }

    /* 方法注解 */
    @ApiOperation(value = "添加用户", notes = "")
    @Override
    @Transactional
    public ResponseResult<UserDto> add(@Validated @RequestBody UserDto userDto) {

        //先创建用户
//        var newUser = userService.add((SysUser) super.dozerMapper.map(userDto, SysUser.class));

        var newUser=(SysUser) super.dozerMapper.map(userDto, SysUser.class);
        newUser.setRoles(new HashSet<>());

        Arrays.stream(userDto.getRoleIds()).forEach(n -> {
            var role=new Role();
            role.setId(n);
            newUser.getRoles().add(role);
//            userRoleService.add(new UserRole(newUser.getId(), n));

        });
        return ResponseUtil.success(super.dozerMapper.map(userService.add(newUser), UserDto.class));
//        return ResponseUtil.success(super.dozerMapper.map(newUser, UserDto.class));
    }

    @Override
    @Transactional
    public ResponseResult<UserDto> edit(@Validated @RequestBody UserDto userDto) throws NotFoundException {
        //先删除所有角色
        var userRoles = userRoleService.getUserRoles(userDto.getId());
        userRoleService.deleteByIds(userRoles.stream().map(n -> n.getId()).collect(Collectors.toList()));

        //再添加角色
        Arrays.stream(userDto.getRoleIds()).forEach(n -> {
            userRoleService.add(new UserRole(userDto.getId(), n));

        });

        var editUser = userService.edit((SysUser) super.dozerMapper.map(userDto, SysUser.class));
        return ResponseUtil.success(super.dozerMapper.map(editUser, UserDto.class));
    }

}

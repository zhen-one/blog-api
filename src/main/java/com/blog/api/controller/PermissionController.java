package com.blog.api.controller;

import com.blog.api.common.response.PageResult;
import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.common.util.TreeUtil;
import com.blog.api.dto.ApiDto;
import com.blog.api.dto.MenuDto;
import com.blog.api.dto.PermissionDto;
import com.blog.api.model.Permission;
import com.blog.api.model.Role;
import com.blog.api.service.ApiService;
import com.blog.api.service.PermissionService;
import com.blog.api.service.RoleService;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.FieldsMappingOptions;
import com.github.dozermapper.core.loader.api.TypeMappingOptions;
import io.swagger.annotations.Api;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Api(value = "权限管理")
@RestController
@RequestMapping("/api/permission")
public class PermissionController extends BaseController<PermissionDto, Permission> {


    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ApiService apiService;

    @Autowired
    public PermissionController(PermissionService service) {
        super.baseService = service;
    }


    @PostMapping("/add")
    @Override
    public ResponseResult<PermissionDto> add(@RequestBody @NotNull PermissionDto dto) {
        if (dto.getApiId() != 0) {
            dto.setApi(apiService.getById(dto.getApiId()));
        }
        return super.add(dto);
    }

    @PostMapping("/edit")
    @Override
    public ResponseResult<PermissionDto> edit(@RequestBody @NotNull PermissionDto dto) throws NotFoundException {

        if (dto.getApiId() != 0) {
            dto.setApi(apiService.getById(dto.getApiId()));
        }
        return super.edit(dto);
    }


    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public ResponseResult<List<MenuDto>> dropdown(int id) {


        var permissions = permissionService.getDropdownData(id).stream().
                map(n -> (PermissionDto) (dozerMapper.map(n, PermissionDto.class))).collect(Collectors.toList());
        var treeList = TreeUtil.toTreeList(permissions, 0);

        var menus = MenuDto.toMenus(treeList,false);

        List list = new ArrayList<>();

        MenuDto menuDto = MenuDto.buildRoot();
        if (menus.size() > 0) {
            menuDto.setChildren(menus);
            menuDto.setLabel(menuDto.getTitle() + "(" + Integer.toString(menus.size()) + ")");
            list.add(menuDto);

        }
        return ResponseUtil.success(list);


    }


    @RequestMapping(value = "/rolePermissionData", method = RequestMethod.GET)
    public ResponseResult<Map<String, List>> rolePermissionData(int roleId) {


        //获取全部状态正常的权限列表
        var permissions = permissionService.getEnabledList().stream().
                map(n -> (PermissionDto) (dozerMapper.map(n, PermissionDto.class))).collect(Collectors.toList());
        var treeList = TreeUtil.toTreeList(permissions, 0);

        var menus = MenuDto.toMenus(treeList,true);
        Map<String, List> map = new HashMap<>();
        map.put("all", menus);
        if (roleId <= 0) {
            map.put("own", new ArrayList<>());
        } else {
            Role role = roleService.getById(roleId);
            var roleMenus = MenuDto.toMenus(role).stream().map(n->n.getKey()).collect(Collectors.toList());
            map.put("own", roleMenus);

        }
         map.put("allKeys", permissions.stream().map(n->n.getId()).collect(Collectors.toList()));
        return ResponseUtil.success(map);


    }


    @Override
    public ResponseResult<PageResult<PermissionDto>> getPagelist(Pageable pageRequest, PermissionDto dto) {
        var res = super.getPagelist(pageRequest, dto);
        var page = res.getData();
        var data = page.getList();
        var treeList = TreeUtil.toTreeList(data, 0);
        page.setList(treeList);
        return ResponseUtil.success(page);
    }
}

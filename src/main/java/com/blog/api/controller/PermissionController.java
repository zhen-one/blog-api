package com.blog.api.controller;

import com.blog.api.common.response.PageResult;
import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.common.util.TreeUtil;
import com.blog.api.dto.MenuTree;
import com.blog.api.dto.PermisisonTree;
import com.blog.api.dto.PermissionDto;
import com.blog.api.model.Permission;
import com.blog.api.model.Role;
import com.blog.api.security.CustomAuthority;
import com.blog.api.service.ApiService;
import com.blog.api.service.PermissionService;
import com.blog.api.service.RoleService;
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
    public ResponseResult<Map<String, List>> dropdown(int id) {


        var permissions = permissionService.getDropdownData(id).stream().
                map(n -> (PermissionDto) (dozerMapper.map(n, PermissionDto.class))).collect(Collectors.toList());
        var treeList = TreeUtil.toTreeList(permissions, 0);

        if (treeList == null) treeList = new ArrayList<PermissionDto>();

        var menus = PermisisonTree.toTrees(treeList, false, id);

        List list = new ArrayList<>();
        Map<String, List> map = new HashMap<>();
        PermisisonTree permisisonTree = PermisisonTree.buildRoot();
        permisisonTree.setChildren(menus);

        if (menus.size() > 0)
            permisisonTree.setLabel(permisisonTree.getTitle() + "(" + Integer.toString(menus.size()) + ")");
        list.add(permisisonTree);

        map.put("all", list);
        var expandkeys = new ArrayList<Integer>();
        //禁用的节点不展开
        if (id > 0) {

            expandkeys.add(permisisonTree.getKey());
            expandkeys.addAll(permissions.stream().filter(n -> n.getType() != 3 && n.getParentId() != id && n.getId() != id)
                    .map(n -> n.getId()).collect(Collectors.toList()));

        } else {
//            expandkeys.addAll(permissions.stream().filter(n -> n.getType() != 3)
//                    .map(n -> n.getId()).collect(Collectors.toList()));
        }
        map.put("expandKeys", expandkeys);

        return ResponseUtil.success(map);


    }


    @RequestMapping(value = "/rolePermissionData", method = RequestMethod.GET)
    public ResponseResult<Map<String, List>> rolePermissionData(int roleId) {


        //获取全部状态正常的权限列表
        var permissions = permissionService.getEnabledList().stream().
                map(n -> (PermissionDto) (dozerMapper.map(n, PermissionDto.class))).collect(Collectors.toList());
        var treeList = TreeUtil.toTreeList(permissions, 0);

        if (treeList == null) treeList = new ArrayList<PermissionDto>();
        var menus = PermisisonTree.toTrees(treeList, true, 0);
        Map<String, List> map = new HashMap<>();
        map.put("all", menus);
        if (roleId <= 0) {
            map.put("own", new ArrayList<>());
        } else {
            Role role = roleService.getById(roleId);
            var roleMenus = PermisisonTree.toTrees(role).stream().map(n -> n.getKey()).collect(Collectors.toList());
            map.put("own", roleMenus);

        }
        map.put("allKeys", permissions.stream().map(n -> n.getId()).collect(Collectors.toList()));
        return ResponseUtil.success(map);


    }


    @RequestMapping(value = "/menuData", method = RequestMethod.GET)
    public ResponseResult<Map<String, List>> menuData() {

        //获取用户权限
        List<PermissionDto> permissions = new ArrayList<>();
        permissions = getCurrentUser()
                .getAuthorities()
                .stream()
                .map(n -> (dozerMapper.map(((CustomAuthority) n).getPermission(), PermissionDto.class)))
                .collect(Collectors.toList());

        Map<String, List> map = new HashMap<>();
        //用户权限转为树形结构
        var menuList = permissions.stream().filter(n -> n.getType() != 3).collect(Collectors.toList());

        var treeList = TreeUtil.toTreeList(menuList, 0);

        if (treeList == null) treeList = new ArrayList<PermissionDto>();
        var menus = MenuTree.toTrees(treeList);
        map.put("menu", menus);
        var btnList = permissions.stream().filter(n -> n.getType() == 3).collect(Collectors.toList());
        map.put("btn", btnList);

        map.put("authorities", permissions.stream().map(n->n.getAuthority()).collect(Collectors.toList()));
        return ResponseUtil.success(map);


    }


    @Override
    public ResponseResult<PageResult<PermissionDto>> getPagelist(Pageable pageRequest, @RequestParam Map<String, Object> dto) {
        var res = super.getPagelist(pageRequest, dto);
        var page = res.getData();
        var data = page.getList();
        var treeList = TreeUtil.toTreeList(data, 0);
        page.setList(treeList);
        return ResponseUtil.success(page);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseResult<List<PermissionDto>> getPermissions(@RequestParam Map<String, Object> dto) {

        var data = baseService.getlistByWhere(dto);
        var dtos=data.stream().map(n->dozerMapper.map(n,PermissionDto.class)).collect(Collectors.toList());
        var treeList = TreeUtil.toTreeList(dtos, 0);
        return ResponseUtil.success(treeList);
    }
}

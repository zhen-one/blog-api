package com.blog.api.controller;

import com.blog.api.common.response.PageResult;
import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.common.util.TreeUtil;
import com.blog.api.dto.ApiDto;
import com.blog.api.dto.PermissionDto;
import com.blog.api.model.Permission;
import com.blog.api.service.ApiService;
import com.blog.api.service.PermissionService;
import io.swagger.annotations.Api;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Api(value = "权限管理")
@RestController
@RequestMapping("/api/permission")
public class PermissionController extends BaseController<PermissionDto, Permission> {


    @Autowired
    private PermissionService permissionService;

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
            dto.setApi(super.dozerMapper.map(apiService.getById(dto.getApiId()), ApiDto.class));
        }
        return super.add(dto);
    }

    @PostMapping("/edit")
    @Override
    public ResponseResult<PermissionDto> edit(@RequestBody @NotNull PermissionDto dto) throws NotFoundException {

        dto.setApi(super.dozerMapper.map(apiService.getById(dto.getApiId()), ApiDto.class));
        return super.edit(dto);
    }


    @RequestMapping(value = "/dropdown", method = RequestMethod.GET)
    public ResponseResult<List<PermissionDto>> dropdown(int id) {

        var permissions = permissionService.getDropdownData(id).stream().
                map(n -> (PermissionDto) (dozerMapper.map(n, PermissionDto.class))).collect(Collectors.toList())
                ;
        var treeList = TreeUtil.toTreeList(permissions, 0);
        return ResponseUtil.success(treeList);

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

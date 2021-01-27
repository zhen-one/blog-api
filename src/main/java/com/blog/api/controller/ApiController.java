package com.blog.api.controller;

import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.dto.ApiDto;
import com.blog.api.dto.RoleDto;
import com.blog.api.model.Api;
import com.blog.api.model.Permission;
import com.blog.api.model.Role;
import com.blog.api.service.ApiService;
import com.blog.api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.HashSet;

@io.swagger.annotations.Api(value = "接口管理")
@RestController
@RequestMapping("/api/manage")
public class ApiController extends BaseController<ApiDto, Api>{


    @Autowired
    private ApiService apiService;

    @Autowired
    public ApiController(ApiService service) {
        super.baseService=service;
    }

}
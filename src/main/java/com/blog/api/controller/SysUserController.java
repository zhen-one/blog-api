package com.blog.api.controller;

import com.blog.api.common.response.PageResult;
import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.dto.UserDto;
import com.blog.api.model.SysUser;
import com.blog.api.service.RoleService;
import com.blog.api.service.SysUserService;
import com.blog.api.service.base.BaseService;
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
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class SysUserController extends BaseController<UserDto,SysUser> {

    @Autowired
    private SysUserService userService;


    @Autowired
    public SysUserController(SysUserService service) {
        super.baseService=service;
    }


    /**
     * 分页列表
     *
     * @param pageRequest
     * @param account
     * @param nickName
     * @return
     */
    @RequestMapping("/list")
    public ResponseResult<PageResult<UserDto>> getUserList(
            @PageableDefault(page = 0, value = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC)
                    Pageable pageRequest,
            String account, String nickName) {
        System.out.println(pageRequest);

        var newPage =
                PageRequest.of(Math.max(pageRequest.getPageNumber() - 1, 0), pageRequest.getPageSize(), pageRequest.getSort());

        var page = userService.getUserList(account, nickName,
                newPage);
        System.out.println(page);
        var res = page.map(n -> dozerMapper.map(n, UserDto.class));

        var pageResult = PageResult.toPageResult(res);

        return ResponseUtil.success(pageResult);
    }



}
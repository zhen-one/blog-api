package com.blog.api.service;

import cn.hutool.core.util.StrUtil;
import com.blog.api.common.exception.BizException;
import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.dto.TokenDto;
import com.blog.api.dto.UserDto;
import com.blog.api.model.SysUser;
import com.blog.api.model.UserRole;
import com.blog.api.repo.SysUserRepository;
import com.blog.api.security.AuthExceptionHelper;
import com.blog.api.security.SecurityUser;

import com.blog.api.security.jwt.JwtTokenUtils;
import com.blog.api.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserService extends BaseService<SysUser, Integer> {

    SysUserRepository dal;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtils jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;



    @Autowired
    public SysUserService(SysUserRepository dal) {//这里必须要使用构造注入。
        super.dal = dal;
        this.dal = dal;

    }


    public SysUser getByName(String userName) {
        return this.dal.getUser(userName);
    }

    public TokenDto login(String userName, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(userName, password);
        // Perform the security

        try {
            final Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // Reload password post-security so we can generate token
            final UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            final TokenDto tokenDto = jwtTokenUtil.accessToken((SecurityUser) userDetails);
            return tokenDto;
        } catch (Exception ex) {
            throw ex;
        }
//        return null;

    }

    public TokenDto refreshToken(String token) {
        return jwtTokenUtil.refreshToken(token);
    }


    /**
     * 重写添加校验方法
     *
     * @param entity
     * @return
     */
    @Override
    public void beforeAdd(SysUser entity) {
        var findUser = this.dal.getUser(entity.getAccount());
        if (findUser != null) throw new BizException(String.format("用户[%s]已经存在!", entity.getAccount())
        );
    }

    /**
     * 重写修改校验方法
     *
     * @param entity
     * @return
     */
    @Override
    public void beforeEdit(SysUser entity) {

        Specification<SysUser> specification = (Specification<SysUser>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况

            predicates.add(cb.notEqual(root.get("id"), entity.getId()));
            predicates.add(cb.equal(root.get("account"), entity.getAccount()));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        };
        var findUser = this.dal.findAll(specification);
        if (findUser != null&&!findUser.isEmpty())
            throw new BizException(String.format("用户[%s]已经存在!", entity.getAccount())
        );
    }

    @Override
    public Page<SysUser> getPageList(SysUser params, Pageable pageable) {
        Specification<SysUser> specification = (Specification<SysUser>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况

            if (!StrUtil.isBlank(params.getAccount()))
                predicates.add(cb.like(root.get("account"), "%" + params.getAccount() + "%"));

            if (!StrUtil.isBlank(params.getNickName()))
                predicates.add(cb.like(root.get("nickName"), "%" + params.getNickName() + "%"));

            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        };
        return super.pageList(specification, pageable);
    }


}

package com.blog.api.controller;

import com.blog.api.model.SysUser;
import com.blog.api.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService userService;
    @ResponseBody
    
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @RequestMapping("/getAll")
    public Iterable<SysUser> getAll(){
       return userService.findAll();
    }

 /*   @RequestMapping("/count")
    public Iterable<SysUser> count(){
        Specification<SysUser> specification=new Specification(){

            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        }
        return userService.findAll();
    }*/

    @RequestMapping("/list")
    public Iterable<SysUser> list(){
        Specification<SysUser> specification=(Specification<SysUser>) (root, criteriaQuery, cb) -> {
            List<Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况
            predicates.add(cb.equal(root.get("nickName"),"213"));//对应SQL语句：select * from ### where username= code
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        };
        return userService.getlistByWhere(specification);
    }
}
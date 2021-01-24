package com.blog.api.service.base;

import com.blog.api.common.exception.BizException;
import com.blog.api.model.SysUser;
import com.blog.api.model.base.BaseModel;
import com.blog.api.repo.base.BaseRepository;
import com.blog.api.security.SecurityUser;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public abstract class BaseService<T extends BaseModel, ID extends Integer> {

    protected BaseRepository<T, ID> dal;

    protected SecurityUser getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;
        return (SecurityUser) auth.getPrincipal();
    }


    /**
     * 等待重写
     *
     * @param entity
     * @return
     */
//    protected  abstract T beforeSaveNew(T entity);
    public void beforeAdd(T entity) {

    }

    public void beforeEdit(T entity) {

    }

    public final T add(T entity) {
        this.beforeAdd(entity);
        entity.setCreatedAt(new Date());
        var user=getCurrentUser();
        if(user!=null){
            entity.setCreator(user.getUsername());
            entity.setCreatorId(user.getUserid());
        }
        return dal.save(entity);
    }

    ;

    public final T edit(T entity) {
        this.beforeEdit(entity);
        entity.setUpdatedAt(new Date());
        var user=getCurrentUser();
        if(user!=null){
            entity.setUpdateBy(user.getUsername());
            entity.setUpdateById(user.getUserid());
        }
        return dal.saveAndFlush(entity);
    }

    ;


    public Iterable<T> saveAll(Iterable<T> entities) {

        return dal.saveAll(entities);
    }


    public T update(T entity) {

        return dal.saveAndFlush(entity);
    }

    public void delete(ID id) throws NotFoundException {
        if (dal.existsById(id)) {
            throw new NotFoundException(String.format("id [%s] 的对象不存在", id));
        }
        dal.deleteById(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    public void deleteByIds(List<Integer> ids) {

        List<T> entities = new ArrayList<>();
        ids.stream().forEach(id -> dal.deleteById(id));
    }

    public T getById(ID id) {
        return dal.getOne(id);
    }


    public T existById(ID id) {
        return this.existById(id);
    }

    public T single(Specification<T> condition) throws RuntimeException, NotFoundException {
        var list = dal.findAll(condition);
        if (list.size() > 0) throw new RuntimeException("返回的结果集的数量超过1个");
        if (list.size() == 0) throw new NotFoundException("未找到对象");
        return list.get(0);
    }

    public T first(Specification<T> condition) {
        return dal.findAll(condition).get(0);
    }

    public Iterable<T> getlistByWhereOrderBy(Specification<T> specification, Sort sort) {
        return dal.findAll(specification, sort);
    }


    /**
     * 启用/未删除 列表
     * @return
     */
    public List<T> getEnabledList(){
        Specification<T> specification = (Specification<T>) (root, criteriaQuery, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况

            predicates.add(cb.equal(root.get("isDeleted"),false));
            predicates.add(cb.equal(root.get("isEnable"), true));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        };
        return dal.findAll(specification);
    }
    /**
     * 分页列表 抽象
     *
     * @param params
     * @param pageable
     * @return
     */
    public abstract Page<T> getPageList(T params, Pageable pageable);


    public List<T> getlistByWhere(Specification<T> specification) {
        return dal.findAll(specification);
    }

    public long count(Specification<T> specification) {
        return dal.count(specification);
    }

    /**
     * 全部数据
     * @return
     */
    public List<T> findAll() {

        return dal.findAll();
    }


    /**
     * 分页查询
     *
     * @param specification
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public Page<T> pageList(Specification<T> specification, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return dal.findAll(specification, pageable);
    }

    /**
     * 分页查询
     *
     * @param specification
     * @param pageable
     * @return
     */
    public Page<T> pageList(Specification<T> specification, Pageable pageable) {
        return dal.findAll(specification, pageable);
    }

}

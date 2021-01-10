package com.blog.api.service.base;

import com.blog.api.model.base.BaseModel;
import com.blog.api.repo.base.BaseRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Supplier;
@Service
public class BaseService<T extends BaseModel, ID extends Serializable>  {
    
    protected BaseRepository<T,ID> dal;

    //在子类构造函数中调用，指明具体的dao层
    public void BaseService(BaseRepository<T,ID> dal ){
        this.dal=dal;
    }
    public T beforeSave(T entity){
        if(entity.getCreateTime()==null){
            entity.setCreateTime(new Date());
        }
        return entity;
    }


    public  T save(T entity){
        this.beforeSave(entity);
        return dal.save(entity);
    }

    public Iterable<T> saveAll(Iterable<T> entities) {
        for (T e : entities) {
            e = beforeSave(e);
        }
        return dal.saveAll(entities);
    }


    public T update(T entity) {

        return dal.saveAndFlush(entity);
    }

    public void delete(ID id) throws NotFoundException {
        if (dal.existsById(id)) {
           throw new NotFoundException(String.format("id [%s] 的对象不存在",id));
        }
        dal.deleteById(id);
    }


    public Iterable<T> getlistByWhereOrderBy(Specification<T> specification, Sort sort){
        return  dal.findAll(specification,sort);
    }

    public Iterable<T> getlistByWhere(Specification<T> specification){
        return  dal.findAll(specification);
    }
    public long count(Specification<T> specification) {
        return dal.count(specification);
    }

    public Iterable<T> findAll() {
        return dal.findAll();
    }




}

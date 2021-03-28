package com.blog.api.service.base;

import com.blog.api.common.exception.BizException;
import com.blog.api.common.util.GenericUtil;
import com.blog.api.model.base.BaseModel;
import com.blog.api.repo.base.BaseRepository;
import com.blog.api.security.SecurityUser;
import javassist.NotFoundException;
import org.hibernate.query.criteria.internal.predicate.LikePredicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public abstract class BaseService<T extends BaseModel, ID extends Integer> {

    protected BaseRepository<T, ID> dal;

    protected SecurityUser getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return null;
        if(auth.getPrincipal().equals("anonymousUser"))return null;
        return (SecurityUser) auth.getPrincipal();
    }

    private Class<?> entityClass;

    public BaseService() {
        this.entityClass = GenericUtil.getActualTypeArgument(this, 0);
    }


    protected void validated(T entity) {

    }

    /**
     * 等待重写
     *
     * @param entity
     * @return
     */
//    protected  abstract T beforeSaveNew(T entity);
    private void uniqueCheck(T entity, boolean isEdit) {


        Field[] fields = entityClass.getDeclaredFields();

        Arrays.stream(fields).forEach(
                field -> {

                    if (field.isAnnotationPresent(com.blog.api.common.anotation.Field.class)) {
                        com.blog.api.common.anotation.Field fieldAnnonation =
                                field.getAnnotation(com.blog.api.common.anotation.Field.class);
                        field.setAccessible(true);
                        if (fieldAnnonation.unique()) {
                            try {
                                Object val = field.get(entity);
                                Specification<T> specification = (Specification<T>) (root, criteriaQuery, cb) -> {

                                    List<Predicate> predicates = new ArrayList<>();

                                    var predict = cb.equal(root.get(field.getName()), val);
                                    predicates.add(predict);
                                    if (isEdit) {
                                        if (entity.getId() <= 0) throw new BizException("id为空");
                                        predicates.add(cb.notEqual(root.get("id"), entity.getId()));
                                    }
                                    return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()


                                };
                                long resCount = this.dal.count(specification);
                                if (resCount > 0) {
                                    throw new BizException(String.format("%s[%s]已经存在!", fieldAnnonation.description(), val));
                                }
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
        );


    }

    public void beforeAdd(T entity) {

        validated(entity);
        uniqueCheck(entity, false);

    }

    public void beforeEdit(T entity) {
        validated(entity);
        uniqueCheck(entity, true);
        var oldOne = this.dal.getOne(entity.getId());
        entity.setCreator(oldOne.getCreator());
        entity.setCreatedAt(oldOne.getCreatedAt());
        entity.setCreatorId(oldOne.getCreatorId());

    }

    public final T add(T entity) {
        this.beforeAdd(entity);
        entity.setCreatedAt(new Date());
        var user = getCurrentUser();
        if (user != null) {
            entity.setCreator(user.getUsername());
            entity.setCreatorId(user.getUserid());
        }
        return dal.save(entity);
    }

    ;

    public final T edit(T entity) {
        this.beforeEdit(entity);
        entity.setUpdatedAt(new Date());
        var user = getCurrentUser();
        if (user != null) {
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
     *
     * @return
     */
    public List<T> getEnabledList() {

        return getEnabledList(null);
    }

    public List<T> getEnabledList(Specification<T> spec) {

        Specification<T> specification = (Specification<T>) (root, criteriaQuery, cb) -> {
            List<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();//使用集合可以应对多字段查询的情况

            predicates.add(cb.equal(root.get("isDeleted"), false));
            predicates.add(cb.equal(root.get("isEnable"), true));
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        };

        if (spec != null) {
            specification = specification.and(spec);
        }
        return dal.findAll(specification);

    }

    /**
     * 分页列表 抽象
     *
     * @param params
     * @param pageable
     * @return
     */
    public Page<T> getPageList(T params, Pageable pageable) {
        Field[] fields = entityClass.getDeclaredFields();

        List<Specification<T>> specifications = new ArrayList<>();

        Specification<T> specificationTotal = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        List<Field> aa = new ArrayList<>();

        for (Field field : fields) {
            if (field.isAnnotationPresent(com.blog.api.common.anotation.Field.class)) {
                com.blog.api.common.anotation.Field fieldAnnonation =
                        field.getAnnotation(com.blog.api.common.anotation.Field.class);
                if (fieldAnnonation.query()) {
                    try {
                        field.setAccessible(true);
                        Object val = field.get(params);

                        if (val == null) continue;

                        Specification<T> specification = (Specification<T>) (root, criteriaQuery, cb) -> {

                            Predicate predict = null;

                            switch (fieldAnnonation.queryOp()) {
                                case equal: {
                                    predict = cb.equal(root.get(field.getName()), val);
                                    break;
                                }
                                case like: {
                                    predict = cb.like(root.get(field.getName()), "%" + val + "%");
                                    break;
                                }
                                case greaterThan: {
                                    break;
                                }
                            }

                            return predict;
                        };
                        specificationTotal = specificationTotal.and(specification);
                        specifications.add(specification);

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }
            }
        }


//        Specification<T> specification = (Specification<T>) (root, criteriaQuery, cb) ->
//                cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        return this.pageList(specificationTotal, pageable);

    }


    /**
     * 分页列表 带查询参数
     *
     * @param params
     * @param pageable
     * @return
     */
    public Page<T> getPageList(Map<String, Object> params, Pageable pageable) {
        Field[] fields = entityClass.getDeclaredFields();
        var superClass = entityClass.getSuperclass();
        var superFields = superClass.getDeclaredFields();

        List<Field> fieldList = new ArrayList<>();
        fieldList.addAll(Arrays.asList(fields));
        fieldList.addAll(Arrays.asList(superFields));

        List<Specification<T>> specifications = new ArrayList<>();

        Specification<T> specificationTotal = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        List<Field> aa = new ArrayList<>();

        for (Field field : fieldList) {
            if (field.isAnnotationPresent(com.blog.api.common.anotation.Field.class)) {
                com.blog.api.common.anotation.Field fieldAnnonation =
                        field.getAnnotation(com.blog.api.common.anotation.Field.class);
                if (fieldAnnonation.query()) {

                    String fieldName = "";
                    if (!fieldAnnonation.queryName().isBlank()) fieldName = fieldAnnonation.queryName();
                    else fieldName = field.getName();

                    if (!params.containsKey(fieldName)) {
                        continue;
                    }
//                        field.setAccessible(true);
//                        Object val = field.get(params);

                    Object val = params.get(fieldName);

                    var fieldType = field.getType();
                    var b = boolean.class;

                    System.out.println(fieldType.getName());

                    if (fieldType.getName() == boolean.class.getName()) {
                        val = Boolean.parseBoolean(val.toString());
                    }
                    if (val == null) continue;

                    Object finalVal = val;
                    Specification<T> specification = (Specification<T>) (root, criteriaQuery, cb) -> {

                        Predicate predict = null;

                        switch (fieldAnnonation.queryOp()) {
                            case equal: {
                                predict = cb.equal(root.get(field.getName()), finalVal);
                                break;
                            }
                            case like: {
                                predict = cb.like(root.get(field.getName()), "%" + finalVal + "%");
                                break;
                            }
                            case greaterThan: {
                                break;
                            }
                        }

                        return predict;
                    };
                    specificationTotal = specificationTotal.and(specification);
                    specifications.add(specification);

                }
            }
        }


//        Specification<T> specification = (Specification<T>) (root, criteriaQuery, cb) ->
//                cb.and(predicates.toArray(new Predicate[predicates.size()]));//以and的形式拼接查询条件，也可以用.or()
        return this.pageList(specificationTotal, pageable);

    }

    /**
     * 全部列表 带查询参数
     *
     * @param params
     * @return
     */
    public List<T> getlistByWhere(Map<String, Object> params) {
        Field[] fields = entityClass.getDeclaredFields();
        var superClass = entityClass.getSuperclass();
        var superFields = superClass.getDeclaredFields();

        List<Field> fieldList = new ArrayList<>();
        fieldList.addAll(Arrays.asList(fields));
        fieldList.addAll(Arrays.asList(superFields));

        List<Specification<T>> specifications = new ArrayList<>();

        Specification<T> specificationTotal = new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return null;
            }
        };
        List<Field> aa = new ArrayList<>();

        for (Field field : fieldList) {
            if (field.isAnnotationPresent(com.blog.api.common.anotation.Field.class)) {
                com.blog.api.common.anotation.Field fieldAnnonation =
                        field.getAnnotation(com.blog.api.common.anotation.Field.class);
                if (fieldAnnonation.query()) {

                    String fieldName = "";
                    if (!fieldAnnonation.queryName().isBlank()) fieldName = fieldAnnonation.queryName();
                    else fieldName = field.getName();

                    if (!params.containsKey(fieldName)) {
                        continue;
                    }

                    Object val = params.get(fieldName);

                    var fieldType = field.getType();
                    var b = boolean.class;

                    System.out.println(fieldType.getName());

                    if (fieldType.getName() == boolean.class.getName()) {
                        val = Boolean.parseBoolean(val.toString());
                    }
                    if (val == null) continue;

                    Object finalVal = val;
                    Specification<T> specification = (Specification<T>) (root, criteriaQuery, cb) -> {

                        Predicate predict = null;

                        switch (fieldAnnonation.queryOp()) {
                            case equal: {
                                predict = cb.equal(root.get(field.getName()), finalVal);
                                break;
                            }
                            case like: {
                                predict = cb.like(root.get(field.getName()), "%" + finalVal + "%");
                                break;
                            }
                            case greaterThan: {
                                break;
                            }
                        }

                        return predict;
                    };
                    specificationTotal = specificationTotal.and(specification);
                    specifications.add(specification);

                }
            }
        }

        return this.getlistByWhere(specificationTotal);

    }

    public List<T> getlistByWhere(Specification<T> specification) {
        return dal.findAll(specification);
    }

    public long count(Specification<T> specification) {
        return dal.count(specification);
    }

    /**
     * 全部数据
     *
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

package com.blog.api.controller;

import com.blog.api.common.response.ResponseResult;
import com.blog.api.common.response.ResponseUtil;
import com.blog.api.dto.UserDto;
import com.blog.api.model.SysUser;
import com.blog.api.model.base.BaseModel;
import com.blog.api.service.SysUserService;
import com.blog.api.service.base.BaseService;
import javassist.NotFoundException;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Array;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public abstract class BaseController<Dto extends BaseModel, Entity extends BaseModel> {


    protected BaseService<Entity, Integer> baseService;


    @Autowired
    protected DozerBeanMapper dozerMapper;

    private Class<?> dtoClass;

    private Class<?> entityClass;


    // 获取第i个泛型的类型
    private Class<?> getActualTypeArgument(int i) {
        {
            Class<?> clazz = this.getClass();  // 由于本类是抽象类，所以this 一定是其子类的实例化对象
            Class<?> entitiClass = null;  // 定义返回的class
            Type genericSuperclass = clazz.getGenericSuperclass(); // 利用反射机制的获取其泛化的超类，实际上也就是带有具体的S 和A的类型的超类
            if (genericSuperclass instanceof ParameterizedType) { // 如果本类实现了参数化接口
                // 获取所有的参数化的类型
                Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
                if (actualTypeArguments != null && actualTypeArguments.length > i) {
                    entitiClass = (Class<?>) actualTypeArguments[i];
                }
            }
            return entitiClass;
        }
    }

    public BaseController() {
        dtoClass = getActualTypeArgument(0);
        entityClass = getActualTypeArgument(1);
    }


    /**
     * 通用添加
     *
     * @param
     * @return
     */
    @PostMapping("/add")
    public ResponseResult<Dto> add(@Validated @RequestBody Dto dto) {

        var entity = (Entity) dozerMapper.map(dto, entityClass);
        var user = baseService.save(entity);
        var dtoRes = (Dto) dozerMapper.map(user, dtoClass);
        return ResponseUtil.success(dtoRes);
    }

    @RequestMapping("/getAll")
    public List<Dto> getAll() {
        return baseService.findAll()
                .stream()
                .map(n -> (Dto) (dozerMapper.map(n, dtoClass)))
                .collect(Collectors.toList());
    }

    /**
     * 通用删除
     *
     * @param id
     * @return
     * @throws NotFoundException
     */
//    @RequestMapping("/delete")
    public ResponseResult<Boolean> deleteById(int id) throws NotFoundException {
        baseService.delete(id);
        return ResponseUtil.success(true);
    }

    /**
     * 批量删除
     *
     * @param
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value="/delete",method = RequestMethod.DELETE)
    public ResponseResult<Boolean> deleteByIds(@RequestBody List<Integer> ids) throws NotFoundException {
        baseService.deleteByIds(ids);
        return ResponseUtil.success(true);
    }


    /**
     * 通用修改
     *
     * @param dto
     * @return
     * @throws NotFoundException
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ResponseResult<Dto> edit(@Validated @RequestBody Dto dto) throws NotFoundException {
        var entity = (Entity) dozerMapper.map(dto, entityClass);
        return ResponseUtil.success((Dto) dozerMapper.map(baseService.save(entity), dtoClass));
    }


    /**
     * 获取详情
     * @param id
     * @return
     * @throws NotFoundException
     */
    @GetMapping("/get")
    public ResponseResult<Dto> edit(int id) throws NotFoundException {
        return ResponseUtil.success((Dto) dozerMapper.map(baseService.getById(id), dtoClass));
    }

}
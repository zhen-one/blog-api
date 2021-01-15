package com.blog.api.repo;

import com.blog.api.model.SysUser;
import com.blog.api.repo.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.stream.Stream;
@Repository
public interface SysUserRepository extends BaseRepository<SysUser, Integer> {

    //接口方法的名称，符合约定则无需实现即可访问
    @Query("select u from SysUser u ")
    Stream<SysUser> findUsers();



    @Query(value = "select u from SysUser u where account=?1")
    SysUser getUser(String account);



}

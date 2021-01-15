package com.blog.api.model;

import com.blog.api.enums.AuthPlateformType;
import com.blog.api.model.base.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.util.Date;

@Table
@Entity
@Data
public class UserAuth extends BaseModel {


    @Column
    private long userId;

    @Column
    private AuthPlateformType plateFormType;

    @Column
    private String uniqueId;

    @Column
    private String accessToken;

    @Column
    private String userName;

    @Column
    private String avator;

    @Column
    private Date lastLoginTime;

}

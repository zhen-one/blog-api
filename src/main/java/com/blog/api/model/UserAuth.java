package com.blog.api.model;

import com.blog.api.enums.AuthPlateformType;
import com.blog.api.model.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import java.util.Date;

@Table
@Entity
public class UserAuth extends BaseModel {

    public AuthPlateformType getPlateFormType() {
        return plateFormType;
    }

    public void setPlateFormType(AuthPlateformType plateFormType) {
        this.plateFormType = plateFormType;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    @Column
    private long sysUserId;

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

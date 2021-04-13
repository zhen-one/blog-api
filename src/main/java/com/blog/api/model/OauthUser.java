package com.blog.api.model;

import com.blog.api.common.anotation.Field;
import com.blog.api.model.base.BaseModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/*
 * 系统表
 * */
@Data
@Table
@Entity
@DynamicUpdate
@DynamicInsert
public class OauthUser extends BaseModel {




    private String sysUserId;

    private String uuid;

    private String avatar;

    private String username;

    private String nickname;

    private String gender;

    private String accessToken;

    private String source;

    private Boolean isMaster;












}

package com.blog.api.model.base;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@DynamicInsert
@DynamicUpdate
public class BaseModel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    //主键
    private int id;

    @Column
    private long creatorId;

    @Column
    private Date createdAt;

    @Column
    private String creator;

    @Column
    private Date updatedAt;

    @Column
    private long updateById;

    @Column
    private String updateBy;

    @Column
    private boolean isDeleted;


    @Column
    private boolean isEnable;


}

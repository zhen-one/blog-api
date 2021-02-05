package com.blog.api.model;


import com.blog.api.common.anotation.Field;
import com.blog.api.enums.PublishState;
import com.blog.api.model.base.BaseModel;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 角色
 */
@Data
@Table(uniqueConstraints =
        {@UniqueConstraint
                (columnNames =
                        {"roleName"}

                )
        }
)
@Entity
@DynamicUpdate
@DynamicInsert
public class Role extends BaseModel {


    /*
    角色名
    * */
    @Field(description = "角色",unique = true,query = true,queryOp = Field.Operator.like)
    private String roleName;

    /*备注*/
    private String description;

    /*排序*/
    private int sortOrder;

    @ManyToMany(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinTable(name="role_permission_rel",joinColumns = @JoinColumn(name ="role_id"),
            inverseJoinColumns = @JoinColumn(name="permission_id"))
    private Set<Permission> permissions=new HashSet<>();


    @Override
    public boolean equals(Object obj) {
       return super.equals(obj);
    }

    /**
     * 重写hashcode方法，返回的hashCode一样才再去比较每个属性的值
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }


}

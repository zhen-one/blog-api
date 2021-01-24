package com.blog.api.model;


import com.blog.api.enums.PublishState;
import com.blog.api.model.base.BaseModel;
import lombok.Data;

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
public class Role extends BaseModel {


    /*
    角色名
    * */
    private String roleName;

    /*备注*/
    private String description;

    /*排序*/
    private int sortOrder;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name="role_permission_rel",joinColumns = @JoinColumn(name ="role_id"),
            inverseJoinColumns = @JoinColumn(name="permission_id"))
    private Set<Permission> permissions=new HashSet<>();


}

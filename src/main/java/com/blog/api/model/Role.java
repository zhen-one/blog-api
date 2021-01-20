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

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if(obj instanceof Role){
           var newRole= (Role)obj;
           return newRole.getId()==super.getId();
        }
        return false;
    }

    /**
     * 重写hashcode方法，返回的hashCode一样才再去比较每个属性的值
     */
    @Override
    public int hashCode() {
        return Integer.valueOf(this.getId()).hashCode();
    }


}

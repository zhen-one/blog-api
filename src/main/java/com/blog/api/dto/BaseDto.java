package com.blog.api.dto;

import com.blog.api.model.Role;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class BaseDto {


    //主键
    private int id;

    private long creatorId;

    private Date createdAt;

    private String creator;

    private Date updatedAt;

    private long updateById;

    private String updateBy;

    private boolean isDeleted;

    private boolean isEnable;

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if(obj instanceof Role){
            var newRole= (Role)obj;
            return newRole.getId()==this.getId();
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

package com.blog.api.model.base;

import com.blog.api.common.anotation.Field;
import com.blog.api.model.Role;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;

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

    private String creator;

    @Column
    private Date updatedAt;

    @Column
    private long updateById;

    @Column
    private String updateBy;

    @Column
    private boolean isDeleted;

    @Field(description = "启用状态",query = true,queryOp = Field.Operator.equal)
    @Column
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

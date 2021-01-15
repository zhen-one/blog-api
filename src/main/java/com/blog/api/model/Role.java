package com.blog.api.model;


import com.blog.api.enums.PublishState;
import com.blog.api.model.base.BaseModel;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


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
    private int sort;
}

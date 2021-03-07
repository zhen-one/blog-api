package com.blog.api.dto;


import com.blog.api.common.anotation.Field;
import lombok.Data;

import javax.persistence.Column;

@Data
public class TagDto extends BaseDto{


    private String tagName;


    private String description;
}



package com.blog.api.dto;

import lombok.Data;

@Data
public class ApiDto extends BaseDto{


    private String name;

    private String url;

    private String code;

    private String description;
}

package com.blog.api.model;
import lombok.Data;

import javax.persistence.*;

@Data
@Table
@Entity
public class PostTag  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int tagId;

    private int postId;
}

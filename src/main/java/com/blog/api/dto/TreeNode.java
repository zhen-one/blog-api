package com.blog.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
public class TreeNode extends BaseDto {

    private Collection<TreeNode> children ;

    private int parentId;

    public void addChildren(TreeNode node){
        this.children.add(node);
    }


}

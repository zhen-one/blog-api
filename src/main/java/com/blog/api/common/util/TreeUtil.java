package com.blog.api.common.util;

import com.blog.api.common.response.ResponseResult;
import com.blog.api.dto.PermissionDto;
import com.blog.api.dto.TreeNode;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class TreeUtil {

    public static <T extends TreeNode> List<T> toTreeList(List<T> data, int pid) {

        if (data == null) return null;
        List<T> treeList = data
                .stream()
                .filter(item -> item.getParentId() == pid)
                .collect(Collectors.toList());


        for (T n : treeList) {
            n.setChildren(toTreeList((List<TreeNode>) data, n.getId()));
        }

        if (treeList.size() == 0) return null;
        return treeList;
    }


}

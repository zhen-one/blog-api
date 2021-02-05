package com.blog.api.dto;


import com.blog.api.common.util.MapperUtil;
import com.blog.api.model.Permission;
import com.blog.api.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class PermisisonTree {

    private int value;

    private int key;

    private String title;

    private String label;


    private boolean disabled;


    private boolean disableCheckbox;

    private boolean checkable;

    private Collection<PermisisonTree> children;


    public PermisisonTree() {

    }


    public static PermisisonTree buildRoot() {
        var menu = new PermisisonTree();
        menu.value = 0;
        menu.key = 0;
        menu.title = "根目录";
        return menu;
    }

    public static List<PermisisonTree> toTrees(Collection<PermissionDto> permissions, boolean checkable, int permission_id) {
        List<PermisisonTree> permisisonTreeList = new ArrayList<>();
        for (PermissionDto permissionDto : permissions) {
            permisisonTreeList.add(toTree(permissionDto,checkable,permission_id));
        }
        return permisisonTreeList;
    }


    public static List<PermisisonTree> toTrees(Role role) {

        Set<Permission> permissions = role.getPermissions();

        List<PermissionDto> list = new ArrayList<>();
        for (Permission permission : permissions) {
            list.add(MapperUtil.map(permission, PermissionDto.class));
        }
        return PermisisonTree.toTrees(list,true,0);

    }

    private static PermisisonTree toTree(PermissionDto permissionDto, boolean checkable, int permission_id) {
        var menu = new PermisisonTree();
        menu.value = permissionDto.getId();
        menu.key = permissionDto.getId();
        menu.title = permissionDto.getName();

        if(permission_id>0){
            boolean flag=permissionDto.getType()==3;
            menu.disabled=permissionDto.getId()==permission_id
                    ||permissionDto.getParentId()==permission_id
                    ||permissionDto.getType()==3;
        }
        menu.checkable=checkable;
        if (permissionDto.getChildren() != null) {
            menu.children = toTrees(
                    permissionDto
                            .getChildren()
                            .stream()
                            .map(n -> (PermissionDto) n)
                            .collect(Collectors.toList()),checkable,permission_id);
            if (menu.children != null && menu.children.size() > 0) {
                menu.label = menu.title + "(" + Integer.toString(menu.children.size()) + ")";
            }

        }
        return menu;
    }
}

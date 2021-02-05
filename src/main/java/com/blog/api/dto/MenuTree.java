package com.blog.api.dto;

import com.blog.api.common.util.MapperUtil;
import com.blog.api.model.Permission;
import com.blog.api.model.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class MenuTree {

    //权限名
    private String name;

    //前端路由
    private String path;

    //路由对应组件
    private String component;

    //图标
    private String icon;

    //子菜单
    private List<MenuTree> routes;



    public static List<MenuTree> toTrees(Collection<PermissionDto> permissions) {
        List<MenuTree> permisisonTreeList = new ArrayList<>();
        for (PermissionDto permissionDto : permissions) {
            permisisonTreeList.add(toTree(permissionDto));
        }
        return permisisonTreeList;
    }


    private static MenuTree toTree(PermissionDto permissionDto) {
        var menu = new MenuTree();
        menu.icon=permissionDto.getIcon();
        menu.name=permissionDto.getCode();
        menu.path=permissionDto.getPath();
        menu.component = permissionDto.getComponent();

        if (permissionDto.getChildren() != null) {
            menu.routes= toTrees(
                    permissionDto
                            .getChildren()
                            .stream()
                            .map(n -> (PermissionDto) n)
                            .collect(Collectors.toList()));


        }
        return menu;
    }

}

package com.blog.api.dto;


import com.blog.api.common.util.MapperUtil;
import com.blog.api.model.Permission;
import com.blog.api.model.Role;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MenuDto {

    private int value;

    private int key;

    private String title;

    private String label;


    private boolean disabled;


    private boolean disableCheckbox;

    private boolean checkable;

    private Collection<MenuDto> children;


    public MenuDto() {

    }


    public static MenuDto buildRoot() {
        var menu = new MenuDto();
        menu.value = 0;
        menu.key = 0;
        menu.title = "根目录";
        return menu;
    }

    public static List<MenuDto> toMenus(Collection<PermissionDto> permissions,boolean checkable) {
        List<MenuDto> menuDtoList = new ArrayList<>();
        for (PermissionDto permissionDto : permissions) {
            menuDtoList.add(toMenu(permissionDto,checkable));
        }
        return menuDtoList;
    }


    public static List<MenuDto> toMenus(Role role) {

        Set<Permission> permissions = role.getPermissions();

        List<PermissionDto> list = new ArrayList<>();
        for (Permission permission : permissions) {
            list.add(MapperUtil.map(permission, PermissionDto.class));
        }
        return MenuDto.toMenus(list,true);

    }

    private static MenuDto toMenu(PermissionDto permissionDto,boolean checkable) {
        var menu = new MenuDto();
        menu.value = permissionDto.getId();
        menu.key = permissionDto.getId();
        menu.title = permissionDto.getName();
        menu.checkable=checkable;
        if (permissionDto.getChildren() != null) {
            menu.children = toMenus(
                    permissionDto
                            .getChildren()
                            .stream()
                            .map(n -> (PermissionDto) n)
                            .collect(Collectors.toList()),checkable);
            if (menu.children != null && menu.children.size() > 0) {
                menu.label = menu.title + "(" + Integer.toString(menu.children.size()) + ")";
            }

        }
        return menu;
    }
}

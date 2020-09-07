package com.yxun8.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Menu {
    private Long id;

    private String text;

    private String url;

    private Menu parentId;

    /*对应的权限*/
    private Permission permission;

    /*加载树菜单*/
    private List<Menu> children = new ArrayList<>();


}
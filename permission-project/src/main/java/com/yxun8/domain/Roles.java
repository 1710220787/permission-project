package com.yxun8.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class Roles {
    private Long rid;

    private String rnum;

    private String rname;

    /*一个角色有多个权限*/
    private List<Permission> permissions = new ArrayList<>();



}
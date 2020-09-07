package com.yxun8.mapper;

import com.yxun8.domain.Permission;
import com.yxun8.domain.Roles;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long pid);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long pid);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    List<Permission> permissionList(Long rid);
}
package com.yxun8.service;

import com.yxun8.domain.Permission;
import com.yxun8.domain.Roles;

import java.util.List;

public interface PermissionService {
    List<Permission> permissionList();

    List<Permission> getPermissionById(Long rid);


}

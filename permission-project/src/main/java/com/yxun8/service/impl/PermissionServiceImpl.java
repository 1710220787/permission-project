package com.yxun8.service.impl;

import com.yxun8.domain.Permission;
import com.yxun8.domain.Roles;
import com.yxun8.mapper.PermissionMapper;
import com.yxun8.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> permissionList() {
        List<Permission> permissionList = permissionMapper.selectAll();
        return permissionList;
    }

    @Override
    public List<Permission> getPermissionById(Long rid) {
        return permissionMapper.permissionList(rid);
    }

}

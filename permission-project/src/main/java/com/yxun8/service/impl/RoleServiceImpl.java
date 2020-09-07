package com.yxun8.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yxun8.domain.PageListRes;
import com.yxun8.domain.Permission;
import com.yxun8.domain.QueryVo;
import com.yxun8.domain.Roles;
import com.yxun8.mapper.RolesMapper;
import com.yxun8.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RolesMapper rolesMapper;

    @Override
    public PageListRes roleList(QueryVo vo) {
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());
        List<Roles> roles = rolesMapper.selectAll();
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(roles);
        return pageListRes;
    }

    @Override
    public void saveRole(Roles roles) {
        /*保存角色*/
        rolesMapper.saveRole(roles);

        /*保存角色和权限的关系*/
        List<Permission> permissions = roles.getPermissions();
        for (Permission permission : permissions) {
            rolesMapper.saveRole_Permission(roles.getRid(),permission.getPid());
        }
    }

    @Override
    public void updateRole(Roles roles) {
        /*打破角色与权限之间的关系*/
        rolesMapper.deleteRoleAndPermission(roles.getRid());
        rolesMapper.updateRole(roles);
        /*保存角色和权限的关系*/
        List<Permission> permissions = roles.getPermissions();
        for (Permission permission : permissions) {
            rolesMapper.saveRole_Permission(roles.getRid(),permission.getPid());
        }

    }

    @Override
    public void deleteRole(Long rid) {
        /*先打破角色与权限之间的关系*/
        rolesMapper.deleteRoleAndPermission(rid);
        rolesMapper.deleteRole(rid);
    }

    @Override
    public List<Roles> getAllRole() {
        return rolesMapper.selectAll();
    }


}

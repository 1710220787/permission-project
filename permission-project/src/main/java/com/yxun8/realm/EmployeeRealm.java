package com.yxun8.realm;

import com.yxun8.domain.Employees;
import com.yxun8.domain.Permission;
import com.yxun8.domain.Roles;
import com.yxun8.service.EmployeesService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRealm extends AuthorizingRealm {

    /*注入业务层*/
    @Autowired
    private EmployeesService employeesService;

    /*认证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        Employees employee = employeesService.getEmployeeOne(username);
        if (employee == null){
            return null;
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(employee, employee.getPassword(), ByteSource.Util.bytes(username), this.getName());
        return info;
    }

    /*授权
    * 激活授权的两种方式：
    *1.在方法上面贴上注解
    * 2.在页面上面加上标签
    * */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        /*获得对象*/
        Employees employee = (Employees) principalCollection.getPrimaryPrincipal();
        List<String> roles = new ArrayList<>();
        List<String> permissions = new ArrayList<>();;

        /*如果是管理员则拥有全部权限*/
        if (employee.getAdmin()){
            /*拥有全部的权限*/
            permissions.add("*:*");
        }else {
            /*查询全部角色*/
            roles = employeesService.getEmpById(employee.getId());
            /*添加权限*/
            permissions = employeesService.getPermission(employee.getId());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }


}

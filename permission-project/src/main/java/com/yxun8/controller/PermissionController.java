package com.yxun8.controller;

import com.yxun8.domain.AjaxRes;
import com.yxun8.domain.Permission;
import com.yxun8.domain.Roles;
import com.yxun8.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("/permissionList")
    @ResponseBody
    public List<Permission> permissionList(){
        List<Permission> permissionList = permissionService.permissionList();
        return permissionList;
    }

    /*查询对应的权限*/
    @RequestMapping("/getPermissionById")
    @ResponseBody
    public List<Permission> getPermissionById(Long rid){
        List<Permission> permissionList = permissionService.getPermissionById(rid);
        return permissionList;
    }


}

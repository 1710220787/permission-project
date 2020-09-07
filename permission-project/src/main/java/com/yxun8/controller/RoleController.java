package com.yxun8.controller;

import com.yxun8.domain.AjaxRes;
import com.yxun8.domain.PageListRes;
import com.yxun8.domain.QueryVo;
import com.yxun8.domain.Roles;
import com.yxun8.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 角色管理
 */
@Controller
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/role")
    public String role(){
        return "role";
    }

    /*加载全部分页角色*/
    @RequestMapping("/roleList")
    @ResponseBody
    public PageListRes roleList(QueryVo vo){
        PageListRes pageListRes = roleService.roleList(vo);
        return pageListRes;
    }

    /*保存角色*/
    @RequestMapping("/saveRole")
    @ResponseBody
    public AjaxRes saveRole(Roles roles){
        try {
            roleService.saveRole(roles);
            return new AjaxRes(true,"保存成功");
        }catch (Exception e){
            return new AjaxRes(false,"保存失败");
        }
    }


    /*更新角色权限*/
    @RequestMapping("/updateRole")
    @ResponseBody
    public AjaxRes updateRole(Roles roles){
        roleService.updateRole(roles);
        try {
            return new AjaxRes(true,"更新成功");
        }catch (Exception e){
            return new AjaxRes(false,"更新失败");
        }
    }

    /*删除角色*/
    @RequestMapping("/deleteRole")
    @ResponseBody
    public AjaxRes deleteRole(Long rid){
        try {
            roleService.deleteRole(rid);
            return new AjaxRes(true,"删除成功");
        }catch (Exception e){
            return new AjaxRes(false,"删除失败");
        }
    }

    /*加载全部角色*/
    @RequestMapping("/getAllRole")
    @ResponseBody
    public List<Roles> getAllRole(){
        return roleService.getAllRole();
    }
}

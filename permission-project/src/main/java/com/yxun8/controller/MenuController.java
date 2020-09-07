package com.yxun8.controller;

import com.yxun8.domain.AjaxRes;
import com.yxun8.domain.Menu;
import com.yxun8.domain.PageListRes;
import com.yxun8.domain.QueryVo;
import com.yxun8.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 菜单管理
 */
@Controller
public class MenuController {
    @Autowired
    private MenuService menuService;


    @RequestMapping("/menu")
    public String menu(){
        return "menu";
    }

    @RequestMapping("/menuList")
    @ResponseBody
    public PageListRes menuList(QueryVo vo){
        PageListRes pageListRes = menuService.menuList(vo);
        return pageListRes;
    }

    /*加载父菜单*/
    @RequestMapping("/parentList")
    @ResponseBody
    public List<Menu> parentList(){
        return menuService.parentList();
    }

    /*保存菜单*/
    @RequestMapping("/saveMenu")
    @ResponseBody
    public AjaxRes saveMenu(Menu menu){
        try {
            menuService.saveMenu(menu);
            return new AjaxRes(true,"保存成功");
        }catch (Exception e){
            return new AjaxRes(false,"保存失败");
        }
    }
    /*更新菜单*/
    @RequestMapping("/updateMenu")
    @ResponseBody
    public AjaxRes updateMenu(Menu menu){
        try {
            menuService.updateMenu(menu);
            return new AjaxRes(true,"更新成功");
        }catch (Exception e){
            return new AjaxRes(false,"更新失败");
        }
    }

    /*删除菜单*/
    @RequestMapping("/deleteMenu")
    @ResponseBody
    public AjaxRes deleteMenu(Long id){
        try {
            menuService.deleteMenu(id);
            return new AjaxRes(true,"删除成功");
        }catch (Exception e){
            return new AjaxRes(false,"删除失败");
        }
    }

    /*加载树菜单*/
    @RequestMapping("/getTreeData")
    @ResponseBody
    public List<Menu> getTreeData(){
        List<Menu> treeData = menuService.getTreeData();
        return treeData;
    }
}

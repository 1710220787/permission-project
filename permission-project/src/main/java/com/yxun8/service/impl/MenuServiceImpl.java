package com.yxun8.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yxun8.domain.Employees;
import com.yxun8.domain.Menu;
import com.yxun8.domain.PageListRes;
import com.yxun8.domain.QueryVo;
import com.yxun8.mapper.MenuMapper;
import com.yxun8.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public PageListRes menuList(QueryVo vo) {
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());
        List<Menu> menus = menuMapper.selectAll();
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(menus);
        return pageListRes;
    }

    @Override
    public List<Menu> parentList() {
        return menuMapper.parentList();
    }

    @Override
    public void saveMenu(Menu menu) {
        menuMapper.saveMenu(menu);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.updateMenu(menu);
    }

    @Override
    public void deleteMenu(Long id) {
        menuMapper.deleteMenu(id);
    }

    @Override
    public List<Menu> getTreeData() {
        List<Menu> treeData = menuMapper.getTreeData();
        //获得主体
        Subject subject = SecurityUtils.getSubject();
        Employees employee = (Employees) subject.getPrincipal();
        /*如果不是管理员*/
        if (!employee.getAdmin()){
            checkPermission(treeData);
        }
        return treeData;
    }

    /*校验是否有权限*/
    public void checkPermission(List<Menu> menus){
        //获得主体
        Subject subject = SecurityUtils.getSubject();
        //边遍历边删除  使用迭代器
        Iterator<Menu> iterator = menus.iterator();   //获取迭代器
        while (iterator.hasNext()){
            Menu menu = iterator.next();
            if (menu.getPermission() != null){   //如果菜单的权限非等于空
                //判断menu是否有权限，如果没有就从集合移除
                if (!subject.isPermitted(menu.getPermission().getPresource())){  //如果没有权限
                    iterator.remove();  //移除
                    continue;   //跳过本次操作，继续执行下一次
                }
            }


            /**
             * 这里使用了递归
             */
            /*是否有子菜单，有就继续执行上面的操作*/
            if (menu.getChildren().size() > 0){
                //有子菜单
                checkPermission(menu.getChildren());
            }
        }

    }
}

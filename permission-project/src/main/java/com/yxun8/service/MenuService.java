package com.yxun8.service;

import com.yxun8.domain.Menu;
import com.yxun8.domain.PageListRes;
import com.yxun8.domain.QueryVo;

import java.util.List;

public interface MenuService {
    PageListRes menuList(QueryVo vo);

    List<Menu> parentList();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenu(Long id);

    List<Menu> getTreeData();



}

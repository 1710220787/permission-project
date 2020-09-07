package com.yxun8.mapper;

import com.yxun8.domain.Menu;
import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    Menu selectByPrimaryKey(Long id);

    List<Menu> selectAll();

    int updateByPrimaryKey(Menu record);

    List<Menu> parentList();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);

    void deleteMenu(Long id);

    List<Menu> getTreeData();


}
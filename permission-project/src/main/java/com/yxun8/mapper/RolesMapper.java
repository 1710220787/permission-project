package com.yxun8.mapper;

import com.yxun8.domain.Roles;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolesMapper {
    int deleteByPrimaryKey(Long rid);

    int insert(Roles record);

    Roles selectByPrimaryKey(Long rid);

    List<Roles> selectAll();

    int updateByPrimaryKey(Roles record);

    void saveRole(Roles roles);

    void saveRole_Permission(@Param("rid") Long rid, @Param("pid") Long pid);

    void deleteRoleAndPermission(Long rid);

    void updateRole(Roles roles);

    void deleteRole(Long rid);


}
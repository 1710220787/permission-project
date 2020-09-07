package com.yxun8.service;

import com.yxun8.domain.PageListRes;
import com.yxun8.domain.QueryVo;
import com.yxun8.domain.Roles;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleService {
    PageListRes roleList(QueryVo vo);

    void saveRole(Roles roles);

    void updateRole(Roles roles);

    void deleteRole(Long rid);

    List<Roles> getAllRole();

}

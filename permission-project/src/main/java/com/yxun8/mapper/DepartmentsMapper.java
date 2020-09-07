package com.yxun8.mapper;


import com.yxun8.domain.Departments;

import java.util.List;

public interface DepartmentsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Departments record);

    Departments selectByPrimaryKey(Long id);

    List<Departments> selectAll();

    int updateByPrimaryKey(Departments record);
}
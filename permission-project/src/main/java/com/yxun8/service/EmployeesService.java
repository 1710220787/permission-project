package com.yxun8.service;

import com.yxun8.domain.Employees;
import com.yxun8.domain.PageListRes;
import com.yxun8.domain.QueryVo;

import java.util.List;

public interface EmployeesService {
    int deleteByPrimaryKey(Long id);


    Employees selectByPrimaryKey(Long id);

    PageListRes selectAll(QueryVo queryVo);

    int updateByPrimaryKey(Employees record);

    void saveEmployee(Employees employees);

    void updateEmployee(Employees employees);

    void updateState(Long id);

    List<Long> getOneRole(Long id);

    Employees getEmployeeOne(String username);

    List<String> getEmpById(Long id);

    List<String> getPermission(Long id);

    PageListRes logList(QueryVo vo);
}
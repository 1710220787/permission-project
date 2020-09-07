package com.yxun8.mapper;

import com.yxun8.domain.Departments;
import com.yxun8.domain.Employees;
import com.yxun8.domain.QueryVo;
import com.yxun8.domain.Systemlog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeesMapper {
    List<Employees> selectAll(QueryVo queryVo);

    void saveEmployee(Employees employees);

    void updateEmployee(Employees employees);

    void updateState(Long id);

    void saveEmp_Role(@Param("rid") Long rid, @Param("id") Long id);

    List<Long> getOneRole(Long id);

    void deleteEmpAndRole(Long id);

    Employees getEmployeeOne(String username);

    List<String> getEmpById(Long id);

    List<String> getPermission(Long id);

    List<Systemlog> logList(QueryVo vo);

    void saveLog(Systemlog systemlog);

}
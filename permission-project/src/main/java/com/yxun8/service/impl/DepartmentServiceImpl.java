package com.yxun8.service.impl;

import com.yxun8.domain.Departments;
import com.yxun8.mapper.DepartmentsMapper;
import com.yxun8.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("DepartmentServiceImpl")
public class DepartmentServiceImpl implements DepartmentService {
    /*注入mapper*/
    @Autowired
    private DepartmentsMapper departmentsMapper;
    @Override
    public List<Departments> getAllDepartment() {
        /*调用mapper*/
        List<Departments> departmentsList = departmentsMapper.selectAll();
        return departmentsList;
    }
}

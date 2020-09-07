package com.yxun8.controller;

import com.yxun8.domain.Departments;
import com.yxun8.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


    /*查询员工的所有部门*/
    @RequestMapping("/departList")
    @ResponseBody
    public List<Departments> departList(){
        return departmentService.getAllDepartment();
    }
}

package com.yxun8.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yxun8.domain.*;
import com.yxun8.mapper.EmployeesMapper;
import com.yxun8.service.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class EmployeesServiceImpl implements EmployeesService {

    @Autowired
    private EmployeesMapper employeesMapper;

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }


    @Override
    public Employees selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public PageListRes selectAll(QueryVo queryVo) {
        Page<Object> page = PageHelper.startPage(queryVo.getPage(), queryVo.getRows());
        List<Employees> employees = employeesMapper.selectAll(queryVo);
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(employees);
        return pageListRes;
    }

    @Override
    public int updateByPrimaryKey(Employees record) {
        return 0;
    }

    @Override
    public void saveEmployee(Employees employees) {
        /*保存员工*/
        employeesMapper.saveEmployee(employees);
        /*保存员工与角色的关系*/
        List<Roles> roles = employees.getRoles();
        for (Roles role : roles) {
            employeesMapper.saveEmp_Role(role.getRid(),employees.getId());
        }
    }

    @Override
    public void updateEmployee(Employees employees) {
        /*打破员工和角色的关系*/
        employeesMapper.deleteEmpAndRole(employees.getId());
        /*更新员工*/
        employeesMapper.updateEmployee(employees);
        /*重新建立关系*/
        List<Roles> roles = employees.getRoles();
        for (Roles role : roles) {
            employeesMapper.saveEmp_Role(role.getRid(),employees.getId());
        }
    }

    @Override
    public void updateState(Long id) {
        employeesMapper.updateState(id);
    }

    @Override
    public List<Long> getOneRole(Long id) {
        return employeesMapper.getOneRole(id);
    }

    @Override
    public Employees getEmployeeOne(String username) {
        return employeesMapper.getEmployeeOne(username);
    }

    @Override
    public List<String> getEmpById(Long id) {
        return employeesMapper.getEmpById(id);
    }

    @Override
    public List<String> getPermission(Long id) {
        return employeesMapper.getPermission(id);
    }

    @Override
    public PageListRes logList(QueryVo vo) {
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());
        List<Systemlog> list = employeesMapper.logList(vo);
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(list);
        return pageListRes;
    }

}

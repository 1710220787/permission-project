package com.yxun8.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxun8.domain.*;
import com.yxun8.service.DepartmentService;
import com.yxun8.service.EmployeesService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.util.IOUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeesService employeesService;

    @RequestMapping("/employee")
    @RequiresPermissions("employee:index")
    public String employee(){
        return "employee";
    }

    /*日志*/
    @RequestMapping("/log")
    public String log(){
        return "log";
    }

    /*如果没有权限*/
    @RequestMapping("/unauthorMsg")
    public String unauthorMsg(){
        return "unauthorMsg";
    }

    /*查询全部员工*/
    @RequestMapping("/employeeList")
    @ResponseBody
    public PageListRes employeeList(QueryVo queryVo){
        PageListRes pageListRes = employeesService.selectAll(queryVo);
        return pageListRes;
    }

    /*添加员工*/
    @RequestMapping("/saveEmployee")
    @ResponseBody
    @RequiresPermissions("employee:add")
    public AjaxRes saveEmployee(Employees employees){
        /*md5加密*/
        Md5Hash md5Hash = new Md5Hash(employees.getPassword(), employees.getUsername(), 1024);
        employees.setPassword(md5Hash.toString());
        //默认为在职状态
        employees.setState(true);
        try {
            employeesService.saveEmployee(employees);
            return new AjaxRes(true,"保存成功");
        }catch (Exception e){
            return new AjaxRes(false,"保存失败");
        }
    }

    /*更新员工*/
    @RequestMapping("/updateEmployee")
    @RequiresPermissions("employee:edit")
    @ResponseBody
    public AjaxRes updateEmployee(Employees employees){
        try {
            employeesService.updateEmployee(employees);
            return new AjaxRes(true,"更新成功");
        }catch (Exception e){
            return new AjaxRes(false,"更新失败");
        }
    }

    /*设置为离职状态*/
    @RequestMapping("/updateState")
    @ResponseBody
    public AjaxRes updateState(Long id){
        try {
            employeesService.updateState(id);
            return new AjaxRes(true,"成功设置为离职");
        }catch (Exception e){
            return new AjaxRes(false,"设置失败");
        }
    }

    /*角色回显*/
    @RequestMapping("/getOneRole")
    @ResponseBody
    public List<Long> getOneRole(Long id){
        return employeesService.getOneRole(id);
    }


    /*如果没有权限*/
    @ExceptionHandler(AuthorizationException.class)
    public void authorizationException(HandlerMethod method, HttpServletResponse response) throws IOException {
        ResponseBody methodAnnotation = method.getMethodAnnotation(ResponseBody.class);
        if (methodAnnotation != null){
            //ajax
            AjaxRes ajaxRes = new AjaxRes(false, "没有权限进行操作..");
            String value = new ObjectMapper().writeValueAsString(ajaxRes);
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(value);
        }else {
            //原生
            response.sendRedirect("error.jsp");
        }
    }



    /*日志列表*/
    @RequestMapping("/logList")
    @ResponseBody
    public PageListRes logList(QueryVo vo){
        PageListRes pageListRes = employeesService.logList(vo);
        return pageListRes;
    }

    /*导出*/
    @RequestMapping("downloadExcel")
    @ResponseBody
    public void downloadExcel(HttpServletResponse response){
        try {
            QueryVo queryVo = new QueryVo();
            queryVo.setPage(1);
            queryVo.setRows(10);
            /*查询所有的数据*/
            PageListRes pageListRes = employeesService.selectAll(queryVo);
            List<Employees> list = (List<Employees>) pageListRes.getRows();
            /*创建excel，写到excel中*/
            //设置第一行
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("员工数据表");
            HSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("编号");
            row.createCell(1).setCellValue("用户名");
            row.createCell(2).setCellValue("入职日期");
            row.createCell(3).setCellValue("电话");
            row.createCell(4).setCellValue("邮件");
            //设置全部行（除了第一行）
            HSSFRow employeeRow = null;
            for (int i = 0; i < list.size(); i++) {
                Employees employee = list.get(i);
                employeeRow = sheet.createRow(i + 1);
                employeeRow.createCell(0).setCellValue(employee.getId());
                employeeRow.createCell(1).setCellValue(employee.getUsername());
                if (employee.getInputtime() != null){
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String time = format.format(employee.getInputtime());
                    employeeRow.createCell(2).setCellValue(time);
                }else {
                    employeeRow.createCell(2).setCellValue("");
                }
                employeeRow.createCell(3).setCellValue(employee.getTel());
                employeeRow.createCell(4).setCellValue(employee.getEmail());
            }
            /*响应给浏览器*/
            String filename = new String("员工数据.xls".getBytes("utf-8"), "iso8859-1");
            response.setHeader("Content-Disposition","attachment;filename="+filename);
            //下载
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*下载模板*/
    @RequestMapping("/downloadTemp")
    @ResponseBody
    public void downloadTemp(HttpServletRequest request,HttpServletResponse response){
        FileInputStream fileInputStream = null;
        try {
            /*响应给浏览器*/
            String filename = new String("员工数据.xls".getBytes("utf-8"), "iso8859-1");
            response.setHeader("Content-Disposition","attachment;filename="+filename);

            //获取项目路径
            String file = request.getServletContext().getRealPath("temp.xls");
            fileInputStream = new FileInputStream(file);

            //下载
            IOUtils.copy(fileInputStream,response.getOutputStream());


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /*导入*/
    @RequestMapping("/uploadExcelFile")
    @ResponseBody
    public AjaxRes uploadExcelFile(MultipartFile excel){
        AjaxRes ajaxRes = new AjaxRes();
        try {
            ajaxRes.setSuccess(true);
            ajaxRes.setMsg("导入成功");
            HSSFWorkbook wb = new HSSFWorkbook(excel.getInputStream());
            /*根据角标获取页*/
            HSSFSheet sheet = wb.getSheetAt(0);
            /*根据页获取最大的行号*/
            int lastRowNum = sheet.getLastRowNum();
            /*遍历*/
            HSSFRow row = null;
            Employees employee = new Employees();
            for (int i = 1; i <= lastRowNum; i++) {
                /*获取每行*/
                row = sheet.getRow(i);
/*                System.out.println(getCellValue(row.getCell(0)));
                System.out.println(getCellValue(row.getCell(1)));
                System.out.println(getCellValue(row.getCell(2)));
                System.out.println(getCellValue(row.getCell(3)));*/
                /*再获取每一列*/
                /*employee.setUsername((String) getCellValue(row.getCell(0)));*/
                employee.setUsername((String) getCellValue(row.getCell(1)));
                String time = (String) getCellValue(row.getCell(2));
                SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(time);
                employee.setInputtime(date);
                employee.setTel((String) getCellValue(row.getCell(3)));
                employee.setEmail((String) getCellValue(row.getCell(4)));
                employeesService.saveEmployee(employee);
            }

        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMsg("导入失败");
        }
        return ajaxRes;
    }

    private Object getCellValue(Cell cell){
        switch (cell.getCellType()) {
            case STRING:
                return cell.getRichStringCellValue().getString();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
        }
        return cell;

    }
}

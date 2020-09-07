package com.yxun8.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employees {
    private Long id;

    private String username;

    private String password;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8") /*格式化展示的时间格式*/
    @DateTimeFormat(pattern = "yyyy-MM-dd")  /*提交表单时候的日期格式*/
    private Date inputtime;

    private String tel;

    private String email;

    private Boolean state;

    private Boolean admin;

    private Departments department;

    /*对应多个角色*/
    private List<Roles> roles = new ArrayList<>();

}
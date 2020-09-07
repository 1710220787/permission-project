package com.yxun8.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yxun8.domain.AjaxRes;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        /*设置响应编码*/
        response.setCharacterEncoding("utf-8");
        AjaxRes ajaxRes = new AjaxRes(true,"登录成功");
        /*把对象转为json格式字符串*/
        String value = new ObjectMapper().writeValueAsString(ajaxRes);
        /*返回给浏览器*/
        response.getWriter().print(value);

        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        /*设置响应编码*/
        response.setCharacterEncoding("utf-8");
        AjaxRes ajaxRes = new AjaxRes(false,"账号或密码有误，请从新输入");
        /*try {
            *//*把对象转为json格式字符串*//*
            String value = new ObjectMapper().writeValueAsString(ajaxRes);
            *//*返回给浏览器*//*
            response.getWriter().print(value);
        } catch (Exception exception) {
            exception.printStackTrace();
        }*/

        /*获取异常名称*/
        if (e != null){
            /*获取异常名称*/
            String name = e.getClass().getName();
            /*判断是哪个异常*/
            if (name.equals(UnknownAccountException.class.getName())){
                /*账号不正确*/
                ajaxRes = new AjaxRes(false,"账号有误，请从新输入");
            }else if (name.equals(IncorrectCredentialsException.class.getName())){
                /*密码不正确*/
                ajaxRes = new AjaxRes(false,"密码有误，请从新输入");
            }else {
                /*未知异常*/
                ajaxRes = new AjaxRes(false,"未知异常，请联系管理员");
            }
        }
        String value = null;
        try {
            value = new ObjectMapper().writeValueAsString(ajaxRes);
            response.getWriter().print(value);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }


}

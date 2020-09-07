<%--
  Created by IntelliJ IDEA.
  User: yxun8
  Date: 2020/9/2
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色管理</title>
    <jsp:include page="${pageContext.request.contextPath}/common/common.jsp"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/role.js"></script>
</head>
<style>
    #dialog #myform .panel-header{
        height: 25px;
    }
    #dialog #myform .panel-title{
        color: black;
        margin-top: -5px;
    }
</style>
<body>
<!--数据表格-->
<table id="dg"></table>
<!--按钮-->
<div id="tb">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="add">添加</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="edit">编辑</a>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="delete">删除</a>
</div>
<%--对话框--%>
<div id="dialog">
    <form id="myform">
        <table align="center" style="border-spacing: 20px 30px">
            <input type="hidden" name="rid">
            <tr align="center">
                <td>角色编号: <input type="text" name="rname" class="easyui-validatebox" ></td>
                <td>角色名称: <input type="text" name="rnum" class="easyui-validatebox" ></td>
            </tr>
            <tr>
                <td><div id="role_data1"></div></td>
                <td><div id="role_data2"></div></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>

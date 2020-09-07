<%--
  Created by IntelliJ IDEA.
  User: yxun8
  Date: 2020/9/2
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib prefix="shir" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>员工管理</title>
    <jsp:include page="${pageContext.request.contextPath}/common/common.jsp"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/employee.js"></script>
</head>
<body>

<%--上传界面--%>
<div id="excelUpload">
    <form id="excelForm" method="post" enctype="multipart/form-data">
        <tabel>
            <tr>
                <td><input type="file" name="excel" style="width: 180px; margin-top: 20px; margin-left: 5px;"></td>
                <td><a href="javascript:void(0);" id="downloadTml">下载模板</a></td>
            </tr>
        </tabel>
    </form>
</div>

<!--按钮-->
<div id="tb">
    <shiro:hasPermission name="employee:add">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" id="add">添加</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="employee:edit">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" id="edit">编辑</a>
    </shiro:hasPermission>

    <shiro:hasPermission name="employee:delete">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" id="delete">离职</a>
    </shiro:hasPermission>
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" id="reload">刷新</a>
    <input type="text" name="keyword" style="width: 200px; height: 30px;padding-left: 5px;">
    <a class="easyui-linkbutton" iconCls="icon-search" id="searchbtn">查询</a>
    <a class="easyui-linkbutton" iconCls="icon-edit" id="excelinput">导入</a>
    <a class="easyui-linkbutton" iconCls="icon-edit" id="excelout">导出</a>
</div>

<!--数据表格-->
<table id="dg"></table>

<!--对话框-->
<div id="dd">
    <form id="form">
        <table align="center" style="border-spacing: 0px 10px">
            <!--隐藏id-->
            <input type="hidden" id="id" name="id">
            <tr>
                <td>姓名：</td>
                <td><input name="username" required="required" type="text"></td>
            </tr>
            <tr id="password">
                <td>密码：</td>
                <td><input name="password" type="text"></td>
            </tr>
            <tr>
                <td>电话：</td>
                <td><input name="tel" required="required" type="text"></td>
            </tr>
            <tr>
                <td>入职日期：</td>
                <td><input name="inputtime" required="required" class="easyui-datebox" required="required" type="text"></td>
            </tr>
            <tr>
                <td>邮箱：</td>
                <td><input name="email" required="required" type="text"></td>
            </tr>
            <tr>
                <td>部门:</td>
                <td><input id="department" required="required" placeholder="请输入部门" name="department.id"></input></td>
            </tr>
            <tr>
                <td>管理员:</td>
                <td><input id="admin" required="required" placeholder="是否管理员" name="admin"></input></td>
            </tr>
            <tr>
                <td>角色:</td>
                <td><input id="roles" required="required" placeholder="请选择角色" name="roles.rid"></input></td>
            </tr>
        </table>
    </form>
</div>

</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: yxun8
  Date: 2020/9/7
  Time: 14:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>日志管理</title>
    <jsp:include page="${pageContext.request.contextPath}/common/common.jsp"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/log.js"></script>
</head>
<body>
<!--按钮-->
<div id="tb">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:true" id="reload">刷新</a>
</div>
<!--数据表格-->
<table id="dg"></table>
</body>
</html>

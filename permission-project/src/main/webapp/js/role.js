$(function () {
    /*数据表格*/
    $('#dg').datagrid({
        url:'/roleList', /*加载数据地址*/
        columns:[[
            {field:'rname',title:'角色名称',width:100,align:'center'},
            {field:'rnum',title:'角色编号',width:100,align:'center'},
        ]],
        pagination:true,  /*分页*/
        fit:true,
        fitColumns:true,
        striped:true,  /*斑马线*/
        singleSelect:true,  /*只允许一行*/
        toolbar:"#tb",
    });

    /*添加编辑对话框*/
    $("#dialog").dialog({
        width:600,
        height:600,
        closed:true,
        buttons:[{
            text:'保存',
            iconCls:'icon-save',
            handler:function(){
                let rid = $("[name='rid']").val();
                let url;
                if (rid){
                    //编辑
                    url='updateRole'
                }else {
                    //保存
                    url='saveRole'
                }
                $("#myform").form("submit",{
                    url:url,
                    /*传递额外参数*/
                    onSubmit:function (param) {
                        /*获取已经选中的权限*/
                        let rows = $("#role_data2").datagrid("getRows");
                        for (let i = 0; i < rows.length; i++) {
                            /*取出每一个权限*/
                            let row = rows[i];
                            /*封装到集合中去，传递给后端*/
                            param["permissions["+i+"].pid"] = row.pid;
                        }
                    },
                    /*保存成功后回显*/
                    success:function (data) {
                        //转为json格式
                        data = $.parseJSON(data);
                        if (data.success){
                            $.messager.alert("温馨提示",data.msg)
                            //重新加载页面
                            $("#dialog").dialog("close")
                            /*清除所选的行*/
                            $("#dg").datagrid("clearSelections");
                            $("#dg").datagrid("load",{})

                        }else {
                            $.messager.alert("温馨提示",data.msg)
                        }
                    }
                })
            }
        },{
            text:'关闭',
            iconCls:'icon-cancel',
            handler:function(){
                $("#dialog").dialog("close")
            }
        }]
    })

    /*添加角色*/
    $("#add").click(function () {
        /*更新对话框的标题*/
        $("#dialog").dialog("setTitle","添加角色与权限")
        /*清空表单*/
        $("#myform").form("clear")
        /*清空已选权限*/
        $("#role_data2").datagrid("loadData",{rows:[]})
        /*打开对话框*/
        $("#dialog").dialog("open")
    })

    /*左边对话框*/
    $("#role_data1").datagrid({
        url: '/permissionList',
        title:'所有权限',
        width:250,
        height:400,
        fitColumns:true,
        singleSelect:true,  /*只允许一行*/
        columns:[[
            {field:'pname',title:'权限名称',width:100,align:'center'},
        ]],
        /*监听点击一行的事件*/
        onClickRow:function (rowIndex,rowData) {
            /*判断是否存在改权限*/
            let allRows = $("#role_data2").datagrid("getRows");
            /*取出每一个进行判断*/
            for (let i = 0; i < allRows.length; i++) {
                let row = allRows[i];
                /*如果已经存在改权限则成为选中状态*/
                if (rowData.pid == row.pid){
                    /*获取已经选中的角标*/
                    let index = $("#role_data2").datagrid("getRowIndex",row);
                    /*让改行成为选中状态*/
                    $("#role_data2").datagrid("selectRow",index)
                    return;
                }
            }


            /*向右边的追加数据*/
            $("#role_data2").datagrid("appendRow",rowData);
        }
    })


    /*右边对话框*/
    $("#role_data2").datagrid({
        title:'已有权限',
        width:250,
        height:400,
        fitColumns:true,
        singleSelect:true,  /*只允许一行*/
        columns:[[
            {field:'pname',title:'权限名称',width:100,align:'center'},
        ]],
        onClickRow:function (rowIndex,rowData) {
            /*删除行(是根据角标删除的)*/
            $("#role_data2").datagrid("deleteRow",rowIndex);
        }
    })

    /*编辑员工*/
    $("#edit").click(function () {
        let rowData = $("#dg").datagrid("getSelected");
        console.log(rowData);
        if (rowData == null){
            $.messager.alert("温馨提示","请选择一条数据进行编辑")
            return;
        }
        /*加载当前角色下的权限*/
        let options = $("#role_data2").datagrid("options");
        options.url = "/getPermissionById?rid="+rowData.rid;
        /*重新加载数据*/
        $("#role_data2").datagrid("load")
        /*更新对话框的标题*/
        $("#dialog").dialog("setTitle","更新员工")
        /*数据回显*/
        $("#myform").form("load",rowData)


        /*打开对话框*/
        $("#dialog").dialog("open")
    })

    /*删除*/
    $("#delete").click(function () {
        /*获取当前行数据*/
        let rowData = $("#dg").datagrid("getSelected");
        if (rowData == null){
            $.messager.alert("温馨提示","请选择一条数据进行操作")
            return;
        }
        $.messager.confirm("确认","是否做删除操作",function (res) {
            if (res){
                //确认
                $.get("/deleteRole?rid="+rowData.rid,function (data) {
                    if (data.success){
                        $.messager.alert("温馨提示",data.msg)
                        //重新加载页面
                        $("#dg").datagrid("reload")
                        $("#dialog").dialog("close")
                    }else {
                        $.messager.alert("温馨提示",data.msg)
                        //重新加载页面
                        $("#dg").datagrid("reload")
                        $("#dialog").dialog("close")
                    }
                })
            }
        })
    })
})
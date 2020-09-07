$(function () {

    /*加载父菜单*/
    $("#parentMenu").combobox({
        panelHeight:100,
        editable:false,
        url:'/parentList',
        textField:'text',
        valueField:'id',
        onLoadSuccess:function () { /*数据加载完毕之后回调*/
            $("#parentMenu").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        },
    });

    $("#menu_datagrid").datagrid({
        url:"/menuList",
        columns:[[
            {field:'text',title:'名称',width:1,align:'center'},
            {field:'url',title:'跳转地址',width:1,align:'center'},
            {field:'parentId',title:'父菜单',width:1,align:'center',formatter:function(value,row,index){
                    return value?value.text:'';
                }
            }
        ]],
        fit:true,
        rownumbers:true,
        singleSelect:true,
        striped:true,
        pagination:true,
        fitColumns:true,
        toolbar:'#menu_toolbar'
    });

    /*
     * 初始化新增/编辑对话框
     */
    $("#menu_dialog").dialog({
        width:300,
        height:300,
        closed:true,
        buttons:'#menu_dialog_bt'
    });

    $("#add").click(function () {
        /*清空对话框*/
        $("#menu_form").form("clear")
        $("#menu_dialog").dialog("open");
    });

    /*保存菜单*/
    $("#save").click(function () {

        let id = $("[name='id']").val();
        let url;
        if (id){
            //
            url = '/updateMenu'
        }else {
            //保存
            url = '/saveMenu'
        }

        /*提交表单*/
        $("#menu_form").form("submit", {
            url:url,
            success:function (data) {
                //转为json格式
                data = $.parseJSON(data);
                if (data.success){
                    $.messager.alert("温馨提示",data.msg)
                    $("#menu_dialog").dialog("close")
                    /*清除所选的行*/
                    $("#menu_datagrid").datagrid("clearSelections");
                    $("#menu_datagrid").datagrid("load",{})

                }else {
                    $.messager.alert("温馨提示",data.msg)
                }
            }
        })
    })

    /*关闭对话框*/
    $("#close").click(function () {
        $("#menu_dialog").dialog("close")
    })


    /*编辑菜单*/
    $("#edit").click(function () {
        let rowData = $("#menu_datagrid").datagrid("getSelected");
        if (rowData == null){
            $.messager.alert("温馨提示","请选择一条数据进行编辑")
            return;
        }
        /*更新对话框的标题*/
        $("#menu_dialog").dialog("setTitle","更新菜单")
        /*显示对话框*/
        $("#menu_dialog").dialog("open")
        /*自行添加department.id*/
        rowData["parentId.id"] = rowData["parentId"].id
        /*数据回显，同名匹配原则*/
        $("#menu_form").form("load",rowData)
    })

    /*删除菜单*/
    $("#del").click(function () {
        let rowData = $("#menu_datagrid").datagrid("getSelected");
        if (rowData == null){
            $.messager.alert("温馨提示","请选择一条数据进行编辑")
            return;
        }

        $.messager.confirm("确认","是否做删除操作",function (res) {
            if (res){
                //确认
                $.get("/deleteMenu?id="+rowData.id,function (data) {
                    if (data.success){
                        $.messager.alert("温馨提示",data.msg)
                        //重新加载页面
                        $("#menu_datagrid").datagrid("reload")
                        $("#menu_dialog").dialog("close")
                    }else {
                        $.messager.alert("温馨提示",data.msg)
                        //重新加载页面
                        $("#menu_datagrid").datagrid("reload")
                        $("#menu_dialog").dialog("close")
                    }
                })
            }
        })
    })



});
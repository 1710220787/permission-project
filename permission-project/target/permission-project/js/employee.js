$(function () {

    /*部门选择 下拉列表*/
    $("#department").combobox({
        panelHeight:'auto',
        editable:false,
        url:'/departList',
        textField:'name',
        valueField:'id',
        onLoadSuccess:function () { /*数据加载完毕之后回调*/
            $("#department").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        },
    });
    /*是否为管理员选择*/
    $("#admin").combobox({
        panelHeight:'auto',
        textField:'label',
        valueField:'value',
        editable:false,
        data:[{
            label:'是',
            value:'true'
        },{
            label:'否',
            value:'false'
        }],
        onLoadSuccess:function () { /*数据加载完毕之后回调*/
            $("#admin").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        }

    });
    /*角色*/
    $("#roles").combobox({
        panelHeight:65,
        limitToList:true,
        editable:false,
        hasDownArrow:true,
        multiple:true,  /*多选*/
        separator:",",  /*多选的使用哪种分隔符*/
        url:'/getAllRole',
        textField:'rname',
        valueField:'rid',
        onLoadSuccess:function () { /*数据加载完毕之后回调*/
            $("#roles").each(function(i){
                var span = $(this).siblings("span")[i];
                var targetInput = $(span).find("input:first");
                if(targetInput){
                    $(targetInput).attr("placeholder", $(this).attr("placeholder"));
                }
            });
        },
    });


    /*对话框*/
    $("#dd").dialog({
        cache: false,
        width:350,
        height:420,
        closed:true,
        buttons:[{
            text:'保存',
            iconCls:'icon-save',
            handler:function(){
                /*判断当前是添加 还是编辑*/
                let id = $("[name='id']").val();
                let url;
                if(id){
                    /*编辑*/
                    url = "updateEmployee";
                }else {
                    /*添加*/
                    url= "saveEmployee";
                }
                /*提交表单*/
                $("#form").form("submit", {
                    url:url,
                    /*传递额外参数*/
                    onSubmit:function (param) {
                        /*获取已经选中的角色*/
                        let values = $("#roles").combobox("getValues");
                        console.log(values);
                        for (let i = 0; i < values.length; i++) {
                            /*取出每一个value（每个角色的id）*/
                            let rid = values[i];
                            param["roles["+i+"].rid"] = rid;
                        }
                    },



                    success:function (data) {
                        //转为json格式
                        data = $.parseJSON(data);
                        if (data.success){
                            $.messager.alert("温馨提示",data.msg)
                            //重新加载页面
                            url = null;
                            $("#dd").dialog("close")
                            /*清除所选的行*/
                            $("#dg").datagrid("clearSelections");
                            $("#dg").datagrid("load",{})

                        }else {
                            url = null;
                            $.messager.alert("温馨提示",data.msg)
                        }
                    }
                })
            }
        },{
            text:'关闭',
            iconCls:'icon-cancel',
            handler:function(){
                $("#dd").dialog("close")
            }
        }]

    })

    /*刷新*/
    $("#reload").click(function () {
        /*清空搜索框*/
        let keyword = $("[name='keyword']").val('');
        //重新数据
        $("#dg").datagrid("load",{})
    })

    /*添加*/
    $("#add").click(function () {

        /*更新对话框的标题*/
        $("#dd").dialog("setTitle","添加员工")
        /*显示密码框*/
        $("#password").show();
        /*清空对话框中的数据*/
        $("#employeeForm").form("clear");
        /*清空表单*/
        $("#form").form("clear");
        $("#dd").dialog("open")
    })

    /*编辑*/
    $("#edit").click(function () {
        let rowData = $("#dg").datagrid("getSelected");
        if (rowData == null){
            $.messager.alert("温馨提示","请选择一条数据进行编辑")
            return;
        }
        console.log(rowData);
        /*更新对话框的标题*/
        $("#dd").dialog("setTitle","更新员工")
        /*隐藏密码框*/
        $("#password").hide();
        /*显示对话框*/
        $("#dd").dialog("open")
        /*自行添加department.id*/
        rowData["department.id"] = rowData["department"].id
        /*回显管理员*/
        rowData["admin"] = rowData["admin"]+"";
        /*根据当前用户的id查出对应的角色*/
        $.get("/getOneRole?id="+rowData.id,function (data) {
            /*设置下拉列表数据回显*/
            $("#roles").combobox("setValues",data)
        })
        /*数据回显，同名匹配原则*/
        $("#form").form("load",rowData)

    })

    /*数据表格*/
    $('#dg').datagrid({
        url:'/employeeList', /*加载数据地址*/
        columns:[[
            {field:'username',title:'姓名',width:100,align:'center'},
            {field:'inputtime',title:'入职日期',width:100,align:'center'},
            {field:'tel',title:'电话',width:100,align:'center'},
            {field:'email',title:'邮箱',width:100,align:'center'},
            {field:'department',title:'部门',width:100,align:'center',formatter: function(value,row,index){
                    if (row.department.name){
                        return row.department.name;
                    }
                }
            },
            {field:'state',title:'状态',width:100,align:'center',formatter: function(value,row,index){
                    if (row.state){
                        return "在职"
                    }else {
                        return "<p style='color: #ec4e00'>离职</p>"
                    }
                }
            },
            {field:'admin',title:'管理员',width:100,align:'center',formatter: function(value,row,index){
                    if (row.admin){
                        return "是"
                    }else {
                        return "否"
                    }
                }},
        ]],
        pagination:true,  /*分页*/
        fit:true,
        fitColumns:true,
        striped:true,  /*斑马线*/
        singleSelect:true,  /*只允许一行*/
        toolbar:"#tb",
    });

    /*离职*/
    $("#delete").click(function () {
        /*获取当行数据*/
        let rowData = $("#dg").datagrid("getSelected");
        if (rowData == null){
            $.messager.alert("温馨提示","请选择一条数据进行操作")
            return;
        }
        $.messager.confirm("确认","是否做离职操作",function (res) {
           if (res){
               //确认
               $.get("/updateState?id="+rowData.id,function (data) {
                   if (data.success){
                       $.messager.alert("温馨提示",data.msg)
                       //重新加载页面
                       $("#dg").datagrid("reload")
                       $("#dd").dialog("close")
                   }else {
                       $.messager.alert("温馨提示",data.msg)
                       //重新加载页面
                       $("#dg").datagrid("reload")
                       $("#dd").dialog("close")
                   }
               })
           }
        })
    })

    /*搜索*/
    $("#searchbtn").click(function () {
        let keyword = $("[name='keyword']").val();
        /*从新加载列表，把参数传过去*/
        $('#dg').datagrid("load", {keyword:keyword})
    })

    /*上传界面*/
    $("#excelUpload").dialog({
        width:260,
        height:180,
        title:"导入Excel",
        buttons:[{
            text:'保存',
            handler:function(){
                /*提交表单*/
                $("#excelForm").form("submit",{
                    url:'/uploadExcelFile',
                    success:function (data) {
                        //转为json格式
                        data = $.parseJSON(data);
                        if (data.success){
                            $.messager.alert("温馨提示",data.msg)
                            $("#excelUpload").dialog("close");
                            $("#dd").dialog("close")
                            $("#dg").datagrid("load")

                        }else {
                            $.messager.alert("温馨提示",data.msg)
                        }
                    }
                })
            }
        },{
            text:'关闭',
            handler:function(){
                $("#excelUpload").dialog("close");
            }
        }],
        closed:true
    })

    /*下载上传模板*/
    $("#downloadTml").click(function () {
        window.open("/downloadTemp")
    })

    /*导出*/
    $("#excelout").click(function () {
        window.open("/downloadExcel")
    })

    /*导入*/
    $("#excelinput").click(function () {
        $("#excelUpload").dialog("open")
    })

})

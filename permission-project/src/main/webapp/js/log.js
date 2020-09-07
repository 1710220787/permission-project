$(function () {

    /*刷新*/
    $("#reload").click(function () {
        /*清空搜索框*/
        let keyword = $("[name='keyword']").val('');
        //重新数据
        $("#dg").datagrid("load",{})
    })


    /*数据表格*/
    $('#dg').datagrid({
        url:'/logList', /*加载数据地址*/
        columns:[[
            {field:'username',title:'姓名',width:30,align:'center'},
            {field:'loptime',title:'操作时间',width:30,align:'center'},
            {field:'lip',title:'IP地址',width:30,align:'center'},
            {field:'lmethod',title:'操作方法',width:100,align:'center'},
            {field:'lparams',title:'操作参数',width:100,align:'center'},
        ]],
        pagination:true,  /*分页*/
        fit:true,
        pageSize:20,
        fitColumns:true,
        striped:true,  /*斑马线*/
        singleSelect:true,  /*只允许一行*/
        toolbar:"#tb",
    });



})

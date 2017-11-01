
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>


<div id='index_${uuid}' style="width: 100%; height: 100%"></div>


<script type="text/javascript">
app.common_Contact_${timeStr} = function() {

		
<jsp:include page="./_configs.jsp"/>

<jsp:include page="./_functions.jsp"/>

<jsp:include page="./_dialogs.jsp"/>

var ds = new Ext.data.JsonStore({
	url: basePath + '/jobGroup/queryJobGroup.do',
	id: 'id',
	root: 'records',
	totalProperty: 'rowCount',
	fields: ['id','appName','title','order','addressType','addressList']
	});
var pageConfig = {'pageSize':pageSize, 'ds':ds, 'record_start':0};

var sm = new Ext.grid.CheckboxSelectionModel({});

var cm = new Ext.grid.ColumnModel([
	 sm,
	 phenix.grid.createRowNumberer(pageConfig),
     {header:'执行器标题',dataIndex:'title'},
     {header:'执行器名称',dataIndex:'appName'},
     {header:'顺序',dataIndex:'order'},
     {header:'执行器地址类型',dataIndex:'addressType',
        renderer: function(value){
                    if(value){
                        return '手动注册';
                    }else{
                        return '自动注册';
                    }
          }},
     {header:'执行器地址列表',dataIndex:'addressList'},
     {header:'操作',dataIndex:'id',renderer:function(value,metadata,record){
     		var btn = ''; 
     		btn += '<button onclick="phenix.deleteJobGroup(\'' + record.data.id + '\')">删除</button>';
     		btn += '<button onclick="phenix.editJobGroup(\'' + record.data.id + '\')">编辑</button>';
     		return btn;
     	}
     }
]);

var pageConfig = {'pageSize':pageSize, 'ds':ds, 'record_start':0};

var buttons = ['-'
	   		    ,phenix.menu.createBtn('查询','bsearch',search),
	   	   		'-',
	   	   		phenix.menu.createBtn('清空条件','bapplication_delete',displayAll),
	   	   		'-',
	   	   		phenix.menu.createBtn('新增','badd',addJobGroup)
		];	
var pageBar = phenix.grid.createPagebar(pageConfig,buttons);

var topBar = [{xtype:'panel', id:'topBarId', 
				border:false, 
				width: Ext.get(mainTabPanel).getWidth(), 
				items:[{
						xtype:'toolbar', 
						items:[{
								xtype:'label',
								html:'&nbsp;任务名称：'
							   },{
								id:'appName_${timeStr}',
								xtype:'textfield',
								editable:false,
								width:200,
                          		anchor:'90%'
						 	}
						]},
					pageBar
				]}
			];


var grid = new Ext.grid.GridPanel({
	autoWidth:true,
	autoScroll:true, 
	stripeRows:true,
	 loadMask: true,
     ds: ds,
     cm: cm,
	 sm: sm,
	 height: Ext.get(tabDivId).getHeight(),
	 width: Ext.get(tabDivId).getWidth(),
	 viewConfig: {
		 forceFit: true
	 },
	 tbar:topBar
});

grid.render(tabDivId);
//进入页面即加载
//grid.getStore().load({params:{start:0, limit:pageSize}});
}

app.common_Contact_${timeStr}();
</script>
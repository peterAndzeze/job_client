
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>


<div id='index_${uuid}' style="width: 100%; height: 100%"></div>


<script type="text/javascript">
app.jobInfo_${timeStr} = function() {

		
<jsp:include page="./_configs.jsp"/>

<jsp:include page="./_functions.jsp"/>

<jsp:include page="./_dialogs.jsp"/>

var ds = new Ext.data.JsonStore({
	url: basePath + '/jobInfo/queryPageList.do',
	id: 'id',
	root: 'records',
	totalProperty: 'rowCount',
	fields: ['id','jobDesc','glueType','jobCron','author','state',"glueSource"]
	});
var pageConfig = {'pageSize':pageSize, 'ds':ds, 'record_start':0};

var sm = new Ext.grid.CheckboxSelectionModel({});

var cm = new Ext.grid.ColumnModel([
	 //sm,
	 phenix.grid.createRowNumberer(pageConfig),
     {header:'job_key',dataIndex:'id',width:50},
     {header:'描述',dataIndex:'jobDesc',width:50},
     {header:'运行模式',dataIndex:'glueType',width:50,renderer:function(value){
        return  phenix.data.transform(value,executorDS);
     }},
     {header:'Cron',dataIndex:'jobCron',width:50},
     {header:'负责人',dataIndex:'author',width:50},
     {header:'状态',dataIndex:'state',width:50,renderer:function(value){
        return phenix.data.transform(value,executorStateDS);
     }},
     {header:'操作',dataIndex:'id',renderer:function(value,metadata,record){
     		var btn = ''; 
     		btn += '<button onclick="phenix.executeTaskJob(\'' + record.data.id+ '\')">立即执行</button>';
     		if(record.data.state=='0'){
     		 btn += '<button onclick="phenix.updatePauseJob(\'' + record.data.id + '\',\'' + record.data.state + '\')">暂停</button>';
     		}else{
     		 btn += '<button onclick="phenix.updateJobState(\'' + record.data.id + '\',\'' + record.data.state + '\')">恢复</button>';
     		}
     		btn += '<button onclick="phenix.editJob(\'' + record.data.id + '\')">编辑</button>';
     		btn += '<button onclick="phenix.deleteJob(\'' + record.data.id + '\')">删除</button>';
     		if(record.data.glueType=='GLUE_GROOVY'){
     		     btn += '<button onclick="phenix.jobGlueSource(\'' + record.data.id + '\')">查看源代码</button>';
     		}
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
                phenix.menu.createBtn('新增job','badd',addJobInfo)
		];	
var pageBar = phenix.grid.createPagebar(pageConfig,buttons);

var topBar = [{xtype:'panel', id:'topBarId', 
				border:false, 
				width: Ext.get(mainTabPanel).getWidth(), 
				items:[{
						xtype:'toolbar', 
						items:[{
                                xtype:'label',
                                html:'&nbsp;执行器：'
                               },{
                                id:'search_jobGroup${timeStr}',
                                xtype:'combo',
                                store: jobGroupsDs,
                                mode:'local',
                                forceSelection : false,
                                triggerAction:'all',
                                displayField:'appName',
                                valueField:'id',
                                selectOnFocus: true,
                                anchor:'90%'
                            },{
								xtype:'label',
								html:'&nbsp;jobHandler：',
								style:'padding-left:10px;'
							   },{
								id:'search_executorHandler${timeStr}',
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
	// sm: sm,
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

app.jobInfo_${timeStr}();
</script>
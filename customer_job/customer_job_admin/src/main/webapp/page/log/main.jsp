
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>


<div id='index_${uuid}' style="width: 100%; height: 100%"></div>


<script type="text/javascript">
app.joblog_${timeStr} = function() {

		
<jsp:include page="./_configs.jsp"/>

<jsp:include page="./_functions.jsp"/>

<jsp:include page="./_dialogs.jsp"/>

var ds = new Ext.data.JsonStore({
	url: basePath + '/logInfo/logModels.do',
	id: 'id',
	root: 'records',
	totalProperty: 'rowCount',
	fields: ['id','handlerTime','triggerCode','triggerMsg','executorAddress','glueType','executorParam','triggerTime','triggerCode','triggerMsg']
	});
var pageConfig = {'pageSize':pageSize, 'ds':ds, 'record_start':0};

var sm = new Ext.grid.CheckboxSelectionModel({});

var cm = new Ext.grid.ColumnModel([
	 //sm,
	 phenix.grid.createRowNumberer(pageConfig),
	 {header:'编号',dataIndex:'id',hidden:true},
     {header:'执行时间',dataIndex:'handlerTime',width:50},
     {header:'调度结果',dataIndex:'triggerCode',width:50},
     {header:'调度备注',dataIndex:'triggerMsg',width:50},
     {header:'执行器地址',dataIndex:'executorAddress',width:50},
     {header:'运行模式',dataIndex:'glueType',width:50},
     {header:'任务参数',dataIndex:'executorParam',width:50},
     {header:'执行时间',dataIndex:'triggerTime'},
     {header:'执行结果',dataIndex:'triggerCode'},
     {header:'执行备注',dataIndex:'triggerMsg'}
]);

var pageConfig = {'pageSize':pageSize, 'ds':ds, 'record_start':0};

var buttons = ['-'
	   		    ,phenix.menu.createBtn('查询','bsearch',search),
	   	   		'-',
	   	   		phenix.menu.createBtn('清空条件','bapplication_delete',displayAll)
		];	
var pageBar = phenix.grid.createPagebar(pageConfig,buttons);

var topBar = [{xtype:'panel',
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
                                //forceSelection : false,
                                triggerAction:'all',
                                displayField:'appName',
                                valueField:'id',
                                selectOnFocus: true,
                                anchor:'90%'
                            },{
                                xtype:'label',
                                html:'&nbsp;job: ',
                                style:'padding-left:10px;'
                               },{
                                id:'search_jobId${timeStr}',
                                xtype:'combo',
                                store: jobInfoDs,
                                mode:'local',
                                forceSelection : false,
                                triggerAction:'all',
                                displayField:'jobDesc',
                                valueField:'id',
                                selectOnFocus: true,
                                anchor:'90%'
                            },{
								xtype:'label',
								html:'&nbsp;执行时间：',
								style:'padding-left:10px;'
							   },{
								id:'search_triggerTimeStart${timeStr}',
								xtype:'datefield',
								editable:false,
								width:200,
                          		anchor:'90%'
						 	},'至',{
						 	    id:'search_triggerTimeEnd${timeStr}',
                                xtype:'datefield',
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
     id:logGrid,
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

app.joblog_${timeStr}();
</script>
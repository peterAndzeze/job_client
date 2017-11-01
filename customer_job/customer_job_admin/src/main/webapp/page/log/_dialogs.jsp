<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
function createFormWin() {
	var fields = [
              {
            	  xtype:'panel',
            	  layout:'column',
            	  border:false,
            	  items:[
            	         {xtype:'panel',columnWidth:0.4,layout:'form',defaultType:'textfield',items:[
            	                   {
            	         				xtype:'combo',
            	         				id:'jobGroup',
		                                name:'jobGroup',
		                                store: jobGroupsDs,
		                                hiddenName:'jobGroup',
		                                mode:'remote',
		                                fieldLabel:'执行器名称',
		                                forceSelection : false,
		                                triggerAction:'all',
		                                displayField:'appName',
		                                valueField:'id',
                                        anchor : '95%',
                                        selectOnFocus: true
                                    },{
                                        xtype:'combo',
                                        id:'executorRouteStrategy',
                                        name:'executorRouteStrategy',
                                        hiddenName:'executorRouteStrategy',
                                        store: new Ext.data.SimpleStore({
                                            fields:['id','text'],
                                            data:[
                                                ['0','第一个'],
                                                ['1','最后一个'],
                                                ['2','轮询'],
                                                ['3','随机'],
                                                ['4','一致性hash'],
                                                ['5','最不经常使用'],
                                                ['5','最近最久未使用'],
                                                ['5','故障转移']
                                                ]
                                        }),
                                        mode:'local',
                                        fieldLabel:'路由策略',
                                        forceSelection : false,
                                        triggerAction:'all',
                                        displayField:'text',
                                        valueField:'id',
                                        anchor : '95%',
                                        selectOnFocus: true
                                    },{
                                        name:'glueType',
                                        hiddenName:'glueType',
                                        id:'glueType',
                                        xtype:'combo',
                                        store: executorDS,
                                        fieldLabel:'运行模式',
                                        forceSelection : false,
                                        triggerAction:'all',
                                        displayField:'displayName',
                                        valueField:'id',
                                        mode: 'local',
                                        anchor : '95%',
                                         selectOnFocus: true
                                    },{
                                        id:'executorParam',
                                        name:'executorParam',
                                        fieldLabel:'执行参数',
                                        anchor : '95%'
                                    },{
                                        name:'executorBlockStrategy',
                                        id:'executorBlockStrategy',
                                        hiddenName:'executorBlockStrategy',
                                        store: new Ext.data.SimpleStore({
                                            fields:['id','text'],
                                            data:[
                                                ['0','单机串行'],
                                                ['1','丢弃后续调度'],
                                                ['2','覆盖之前调度']
                                            ]
                                        }),
                                        mode:'local',
                                        xtype:'combo',
                                        fieldLabel:'阻塞处理策略',
                                        forceSelection : false,
                                        triggerAction:'all',
                                        displayField:'text',
                                        valueField:'id',
                                        allowBlank:false,
                                        anchor : '95%',
                                        selectOnFocus: true
                                        
                                    },{
                                        xtype:'textfield',
                                        allowBlank:false,
                                        fieldLabel:'负责人',
                                        id:'author',
                                        name:'author'
                                    }
                                    ]},{
                                        xtype:'panel',columnWidth:0.4,layout:'form',defaultType:'textfield',items:[
                                    {
                                        id:'jobDesc',
                                        fieldLabel:'任务描述',
                                        name:'jobDesc',
                                        allowBlank:false
                                    },{
                                        id:'jobCron',
                                        name:'jobCron',
                                        fieldLabel:'Cron',
                                        allowBlank:false
                                    },{
                                        id:'executorHandler',
                                        name:'executorHandler',
                                        fieldLabel:'JobHandler',
                                        allowBlank:false
                                    },{
                                        id:'childJobKey',
                                        name:'childJobKey',
                                        fieldLabel:'子任务',
                                        allowBlank:true,
                                        emptyText:'多个任务以","分割'
                                    },{
                                        name:'executorFailStrategy',
                                        id:'executorFailStrategy',
                                        fieldLabel:'失败处理策略',
                                        hiddenName:'executorFailStrategy',
                                        xtype:'combo',
                                        displayField : 'text',    // 显示字段
                                        valueField : 'id',    // 值，可选
                                        store:new Ext.data.SimpleStore({
                                            fields:['id','text'],
                                            data:[['0','失败告警'],['1','失败重试']]
                                        }),
                                        forceSelection : false,
                                        triggerAction:'all',
                                        mode: 'local',
                                        anchor : '95%',
                                        selectOnFocus: true
                                        
                                    },{
                                        id:'alarmEmail',
                                        name:'alarmEmail',
                                        fieldLabel:'失败发送邮件',
                                        emptyText:'多个以逗号分割'
                                    },{
                                        id:'id',
                                        name:'id',
                                        fieldLabel:'失败发送邮件',
                                        hidden:true
                                    }
                                    ]
                                    }
            	                                                                                     
            	         ]
              }];
var form = new Ext.form.FormPanel({
	id: formId,
	labelAlign:'right',
	buttonAlign:'center',
	fileUpload: true,
	frame : true,
	items: fields,
	buttons: [
	{
		text: '关闭',
		handler: function(){
			Ext.getCmp(formWinId).close();
		}
	},{
	   text:'保存',
	   handler:save
	}]
	
});

	var title = '新建';
	var formWin = phenix.util.openDialog(formWinId,form,'',700,400,title,'',true);
	return formWin;
}

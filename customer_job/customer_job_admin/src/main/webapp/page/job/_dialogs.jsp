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
                                        store: executorRouteStrategyDS,
                                        mode:'local',
                                        fieldLabel:'路由策略',
                                        forceSelection : false,
                                        triggerAction:'all',
                                        displayField:'display',
                                        valueField:'value',
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
                                        displayField:'display',
                                        valueField:'value',
                                        mode: 'local',
                                        anchor : '95%',
                                         selectOnFocus: true,
                                        listeners:{'change':function(record){
                                            if(record.value=="GLUE_GROOVY"){
                                            	 Ext.getCmp("executorHandler").allowBlank=true;
                                                Ext.getCmp("executorHandler").disable();
                                            }else{
                                            	Ext.getCmp("executorHandler").allowBlank=false;
                                                Ext.getCmp("executorHandler").enable();
                                            }
                                        }}
                                    },{
                                        id:'executorParam',
                                        name:'executorParam',
                                        fieldLabel:'执行参数',
                                        anchor : '95%'
                                    },{
                                        name:'executorBlockStrategy',
                                        id:'executorBlockStrategy',
                                        hiddenName:'executorBlockStrategy',
                                        store: executorBlockStrategyDS,
                                        mode:'local',
                                        xtype:'combo',
                                        fieldLabel:'阻塞处理策略',
                                        forceSelection : false,
                                        triggerAction:'all',
                                        displayField:'display',
                                        valueField:'value',
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
                                        emptyText:''
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
	   handler:saveJob
	}]
	
});

	var title = '新建';
	var formWin = phenix.util.openDialog(formWinId,form,'',700,400,title,'',true);
	return formWin;
}
function createGlueFormWin(id){
	var gluePanel=new Ext.Panel({
        renderTo: Ext.getBody(),
        border:false,
        width: 600,
        height: 300,
        frame: true,
        buttonAlign:'center',
        layout: 'fit',
        tbar : ['->','备注：',{xtype:'textfield'},'版本:',{
			fieldLabel : '回溯版本',
			hiddenName : 'jobId',
			xtype : 'combo',
			displayField : 'glueRemark', // 显示字段
			valueField : 'jobId', // 值，可选
			store : new Ext.data.JsonStore({
				        autoLoad:true,
						fields : ['jobId', 'glueRemark'],
						url:basePath + '/glue/glues.do?jobId='+id
					}),
			forceSelection : false,
			triggerAction : 'all',
			mode : 'local',
			anchor : '95%',
			selectOnFocus : true
		}],
        html:"<textarea id='glueSource' style='width:100%;height:100%'></textarea>",
        buttons: [
            {
                text: '关闭',
                handler: function(){
                    Ext.getCmp("glueSourceWin").close();
                }
            },{
               text:'保存',
               handler:function(){
                saveGlue(id);
               }
            }
          ]  
    });
    var title = 'job源代码信息';
    var formWin = phenix.util.openDialog("glueSourceWin",gluePanel,'',700,400,title,'',true);
    return formWin;
}



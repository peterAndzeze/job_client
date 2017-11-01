<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

function createFormWin(title) {
	var fields = [
              {
            	  xtype:'panel',
            	  layout:'column',
            	  border:false,
            	  items:[
            	         {xtype:'panel',columnWidth:1,layout:'form',defaultType:'textfield',items:[
            	         			{
                                   	 id:'id',
                                   	 fieldLabel:'主键',
                                   	 name:'id',
            	         		     xtype:'hidden'
                                    },{
                                        id:'title',
                                        name:'title',
                                        fieldLabel:'执行器标题',
                                        allowBlank:false,
                                        anchor:'90%',
                                        emptyText:'标题不能为空'
                                    },{
                                    	id:'appName',
                                    	fieldLabel:'执行器名称',
                                    	name:'appName',
                                    	allowBlank:false,
                                    	anchor:'90%',
                                    	maxLength:64,
                                    	minLength:4,
                                    	emptyText :'AppName长度限制为4~64'
                                    },{
                                    	id:'order',
                                    	name:'order',
                                    	fieldLabel:'执行器排序',
                                    	allowBlank:false,
                                    	anchor:'90%'
                                    },{
                                        xtype:'panel',
                                        fieldLabel:'注册类型',
                                        layout:'column',
                                        items:[
                                            {
                                                xtype:'radio',
                                                boxLabel:'自动注册',
                                                name:'addressType',
                                                id:'addressType_0',
                                                inputValue:'0',
                                                handler:function(obj){
                                                  if(obj.checked){
                                                        Ext.getCmp("addressList").disable();
                                                        Ext.getCmp("addressList").allowBlank=true;
                                                   }
                                                }
                                            },{
                                                xtype:'radio',
                                                boxLabel:'手动注册',
                                                name:'addressType',
                                                id:'addressType_1',
                                                inputValue:'1',
                                                handler:function(obj){
                                                  if(obj.checked){
                                                        Ext.getCmp("addressList").enable();
                                                        Ext.getCmp("addressList").allowBlank=false;
                                                   }
                                                }
                                            }
                                        ],
                                    	allowBlank:false,
                                    	anchor:'90%'
                                    },{
                                        id:'addressList',
                                        allowBlank:false,
                                        name:'addressList',
                                        fieldLabel:'执行器地址',
                                        anchor:'90%'
                                    }
                                    ]}
            	                                                                                     
            	         ]
              }];
var form = new Ext.form.FormPanel({
	id: formId,
	labelWidth: 100,
	labelAlign:'right',
	buttonAlign:'center',
	labelPad:10,
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
	   handler:savejobGroup
	}]
	
});

	var formWin = phenix.util.openDialog(formWinId,form,'',400,300,title,'',true);
	return formWin;
}

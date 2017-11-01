<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

/**
 * 创建编辑窗口
 */
function createFormWin(){
	var fields = [
		{
			id: 'key',
			fieldLabel: 'key',
			name: 'key',
			allowBlank:false,
			maxLength:255,
			anchor: '95%'
		}, {
			id: 'display',
			fieldLabel: '描述',
			name: 'display',
			allowBlank:false,
			maxLength:64,
			anchor: '95%'
		},{
            id: 'value',
            fieldLabel: '值',
            name: 'value',
            anchor: '95%'
        }, {
            id: 'value1',
            fieldLabel: '值1',
            name: 'value1',
            anchor: '95%'
        },{
			id: 'remark',
			fieldLabel: '说明',
			name: 'remark',
			xtype: 'textarea',
			height: 64,
			anchor: '95%'
		}, {
			xtype:'panel',
			fieldLabel: '状态',
			//isFormField:true,
			baseCls:'x-plain',
			layout:'column',
			items:[{
					id:'state_0',
					xtype:'radio',
					boxLabel:'启用　',
					name: 'state',
					inputValue:'0'
				}, {
					id:'state_1',
					xtype:'radio',
					boxLabel:'禁用　',
					name: 'state',
					inputValue:'1'
				}
			]
		}, {
			id: 'parentId',
			xtype: 'hidden',
			name: 'parentId'
		}, {
			id: 'id',
			xtype: 'hidden',
			name: 'id'
		}];

		var form = new Ext.form.FormPanel({
		id: formId,
		baseCls: 'x-plain',
		labelWidth: 100,
		labelAlign:'right',
		labelPad:10,
		fileUpload: false,
		defaultType: 'textfield',
		items: fields,
		buttonsAlgin:'center',
		buttons: [{
			text: '保存',
			handler: save
		}, {
			text: '关闭',
				handler: function(){
					Ext.getCmp(formWinId).close();
				}
			}]
		});
	var title = '新建'+baseName;
	var formWin = phenix.util.openDialog(formWinId,form,baseIcon,400,300,title);
	return formWin;
}



/**
 * 创建编辑窗口（暂时未使用，显示ID、PID）
 */
function createFormWinNoUse(){
	var fields = [
		{
			id: 'displayName',
			fieldLabel: '名称',
			name: 'displayName',
			allowBlank:false,
			maxLength:255,
			anchor: '95%'
		}, {
			id: 'dictKey',
			fieldLabel: '关键字',
			name: 'dictKey',
			allowBlank:false,
			maxLength:64,
			anchor: '95%'
		},  {
			id: 'value1',
			fieldLabel: '值1',
			anchor: '95%'
		},  {
			id: 'value2',
			fieldLabel: '值2',
			anchor: '95%'
		},  {
			id: 'value3',
			fieldLabel: '值3',
			anchor: '95%'
		},  {
			id: 'value4',
			fieldLabel: '值4',
			anchor: '95%'
		},  {
			id: 'value5',
			fieldLabel: '值5',
			anchor: '95%'
		}, {
			id: 'remark',
			fieldLabel: '说明',
			name: 'remark',
			xtype: 'textarea',
			height: 64,
			anchor: '95%'
		}, {
			id: 'path',
			fieldLabel: '全路径(只读)',
			name: 'path',
			readOnly: true,
			cls: 'x-item-disabled',
			anchor: '95%'
		}, {
			id: 'version',
			xtype: 'hidden',
			name: 'version',
			anchor: '95%'
		}, {
			id: 'iconCls',
			xtype:'hidden',
			name: 'iconCls',
			value:baseIcon,
			anchor: '95%'
		}, {
			id: 'sortIdx',
			fieldLabel: '序号',
			name: 'sortIdx',
			anchor: '95%'
		}, {
			id: 'pkid',
			fieldLabel: 'ID',
			name: 'pkid',
			readOnly: true,
			cls: 'x-item-disabled',
			anchor: '95%'
		}, {
			id: 'pid',
			fieldLabel: '父ID',
			name: 'pid',
			value: currentNode.id,
			readOnly: true,
			cls: 'x-item-disabled',
			anchor: '95%'
		}, {
			xtype:'panel',
			fieldLabel: '状态',
			//isFormField:true,
			baseCls:'x-plain',
			layout:'column',
			items:[{
				id:'status_0',
				xtype:'radio',
				boxLabel:'启用　',
				name: 'systemStatus',
				inputValue:'0'
			}, {
				id:'status_1',
				xtype:'radio',
				boxLabel:'禁用　',
				name: 'systemStatus',
				inputValue:'1'
			}]
		}];

		var form = new Ext.form.FormPanel({
		id: formId,
		baseCls: 'x-plain',
		labelWidth: 100,
		labelAlign:'right',
		labelPad:10,
		fileUpload: false,
		defaultType: 'textfield',
		items: fields,
		buttons: [{
			text: '保存',
			handler: save
		}, {
			text: '关闭',
				handler: function(){
					Ext.getCmp(formWinId).close();
				}
			}]
		});
	var title = '新建'+baseName;
	var formWin = phenix.util.openDialog(formWinId,form,baseIcon,600,500,title,'displayName');
	return formWin;
}
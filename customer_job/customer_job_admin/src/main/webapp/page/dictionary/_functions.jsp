<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

/**
 * 刷新节点
 */
function refreshNode(){
	var node = currentNode;
	phenix.tree.refreshNode(node);
}

/**
 * 弹出新建窗口
 */
function add(){
	var formWin = createFormWin(type);
	Ext.getCmp('parentId').setValue(currentNode.id);
	Ext.getCmp('state_0').setValue(true);//默认为启用
	formWin.show();
}

/**
 * 从sm取得选择的行，然后弹出编辑窗口
 */
function edit(){
	var node = currentNode;
	var id = node.id;
	if(root.id==id){
		phenix.Msg.warn('不能修改最顶层节点 !');
		return;
	}
	//打开编辑窗口
	var formWin = createFormWin();
	formWin.setTitle('修改'+baseName);
	phenix.ajax.request(menuInfoUrl,{'id': id},function(result){
		phenix.form.setValue(formId, result);
		var statusId = 'state_' + (result.state==0? 0 : 1);
		Ext.getCmp(statusId).setValue(true);		
		formWin.show();
		currentOperation = 'edit';
	},true);
}

/**
 * 保存
 */
function save(){
	phenix.ajax.submit(updateMenuUrl,formId,function(result){
		Ext.getCmp(formWinId).close();
		if(root.id==currentNode.id){
			phenix.tree.refreshNode(currentNode);
		}else if('edit'==currentOperation){
			phenix.tree.refreshNode(currentNode.parentNode);
		}else if(!currentNode.leaf){
			phenix.tree.refreshNode(currentNode);
		}else{
			phenix.tree.refreshNode(currentNode.parentNode);
		}
		grid.store.reload();
		currentOperation = null;
	});
}

/**
 * 删除
 */
function del(){
	var node = currentNode;
	if(root.id==node.id){
		phenix.Msg.warn('不能删除最顶层节点 !');
		return;
	}
	if(!node.leaf){
	  phenix.Msg.warn('存在子节点，不能删除');
	  return;
	}
	phenix.Msg.confirm('提示', '您确定要删除字典（项）：'+node.text+' 吗?', function(btn, text){
		if ('yes' != btn) return;
		phenix.ajax.request(delUrl,{'id': node.id,"parentId":node.parentNode.id},function(result){
			if(result.success){
				node.remove();
                phenix.tree.refreshNode(node.parentNode);
				phenix.Msg.info(result.msg);
			}else{
				phenix.Msg.info(result.msg);
			}
			//grid.store.reload();
		});
	});
}

/**
* 删除
*/
function dels(){
    var node = currentNode;
    if(root.id==node.id){
        phenix.Msg.warn('不能删除最顶层节点 !');
        return;
    }

    phenix.Msg.confirm('提示', '您确定要级联删除字典：'+node.text+' 吗?', function(btn, text){
	    if ('yes' != btn) return;
	    phenix.Msg.confirm('提示', '<span style="color:red;font-size:13px;font-weight:bold">请慎用级联删除功能 !</span><br><br>确定后将删除字典本身及子字典 !', function(btn, text){
	    if ('yes' != btn) return;
		    phenix.ajax.request(delUrl,{'pkid': node.id,'delType':'cascade'},function(result){
		        phenix.Msg.info(result.msg);
		        node.remove();
		    },true);
	    });
    });
}

/**
 * 复制
 */
var copyNode;
function copy(){
	copyNode = currentNode;
	if(root.id==copyNode.id){
		phenix.Msg.warn('不能复制最顶层节点 !');
		return;
	}
	phenix.Msg.info("复制成功 !");
}

/**
 * 粘贴
 */
function paste(){
	var pasteNode = currentNode;
	if(copyNode==null){
		phenix.Msg.warn('请选择要复制的节点 !');
		return;
	}
	if(pasteNode.id==copyNode.id){
		phenix.Msg.warn('不能粘贴到本节点 !');
		return;
	}
	if(pasteNode==copyNode.parentNode){
		phenix.Msg.warn('父节点下不能有关键字相同的子节点 !');
		return;
	}
	if(copyNode.contains(pasteNode)){
		phenix.Msg.warn('不允许将父节点粘贴到子节点下 !');
		return;
	}
	phenix.ajax.request(pasteUrl,{'pkid': pasteNode.id,'copyNode':copyNode.id},function(){
		phenix.tree.refreshNode(pasteNode);//刷新节点
	},true);
}
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

function clearSearch(){
	var cmp = Ext.getCmp("appName_${timeStr}");
	if(cmp) cmp.setValue('');
}

/**
 * 显示全部
 */
function displayAll(){
	clearSearch();
	search();
	/**var store = grid.getStore();
	store.removeAll();**/
}

search = function() {
	var searchKey = Ext.getCmp('appName_${timeStr}').getValue();
	var store = grid.getStore();
	store.baseParams={'appName':searchKey};
	store.load({params:{start:0, limit:pageSize}});
}
/**
 * 修改
 */
phenix.deleteJobGroup=function(id) {
	phenix.Msg.confirm("温馨提示","是否确认删除",function(btn, text){
	   if(btn!='yes'){
	       return 
	   }
	   phenix.ajax.request(basePath + '/jobGroup/delete.do',{'id':id},function(result){
            phenix.Msg.info(result.msg);
            search();
       });
	});
	
	
}

/**
 * 保存
 */
savejobGroup = function() {
	phenix.Msg.confirm("温馨提示","是否确认修改",function(btn, text){
       if(btn!='yes'){
           return 
       }
       if(!Ext.getCmp(formId).getForm().isValid()){
       	    phenix.Msg.warn("请正确填写");
       	    return;
       }
       var jobGroupModel=Ext.getCmp(formId).getForm().getValues();
       phenix.ajax.request(basePath + '/jobGroup/saveOrUpdate.do',jobGroupModel,function(result){
            phenix.Msg.info(result.msg);
            Ext.getCmp(formWinId).close();
            grid.getStore().load({params:{start:0, limit:pageSize}});
    });
    });
	
}

phenix.editJobGroup = function(id){
	var formWin = createFormWin();
    if(null==id){
    	formWin.setTitle('新增');
    	formWin.show();
    }else{
    	formWin.setTitle('修改');
    	phenix.ajax.request(basePath + '/jobGroup/queryJobGoupDetail.do',{'id':id},function(result){
    		phenix.form.setValue(formId, result);
    		Ext.getCmp("addressType_"+result.addressType).setValue(true);
    		formWin.show();
    	});
    }
}

addJobGroup=function(){
	phenix.editJobGroup(null);
}


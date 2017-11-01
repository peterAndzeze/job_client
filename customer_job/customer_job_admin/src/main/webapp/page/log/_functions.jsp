<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

function clearSearch(){
	for(var key in searchParams){
        var sk = 'search_'+key+'${timeStr}';
        var cmp = Ext.getCmp(sk);
        if(cmp) cmp.setValue('');
    }
}

/**
 * 显示全部
 */
function displayAll(){
	clearSearch();
	//search();
	var store = grid.getStore();
	store.removeAll();
}

search = function() {
	var baseParam =createSearchParam();
	var store = Ext.getCmp(logGrid).getStore();
    store.baseParams = baseParam;
    console.log(baseParam);
    store.load({params:{start:0, limit:pageSize}});
}

/**
 * 生成查询条件
 */
function createSearchParam(){
    var prefix = 'search_';
    var suffix = '${timeStr}';
    var params = searchParams;
    for(var key in params){
    	var value = Ext.getCmp(prefix+key+suffix).getValue();
        if(value && typeof(value)=='string') value = value.trim();
        params[key] = value;
    }
    if (params.triggerTimeStart) {
		params.triggerTimeStart = params.triggerTimeStart.format('Y-m-d')
				+ ' 00:00:00';
	}
	if (params.triggerTimeEnd) {
		params.triggerTimeEnd = params.triggerTimeEnd.format('Y-m-d')
				+ ' 23:59:59';
	}
    return params;
}


/**
 * 修改
 */
phenix.edit = function(id) {
	var formWin = createFormWin();
	formWin.setTitle('修改');
	phenix.ajax.request(basePath + '/jobInfo/queryJobById.do',{'id':id},function(result){
		phenix.form.setValue(formId, result);
		formWin.show();
	});
}
/**
 * 删除
 * @param {} id
 */
phenix.deleteJob=function(id){
    phenix.Msg.confirm("温馨提示","确认删除吗？",function(btn,text){
        if('yes'!=btn){
            return;
        }
        phenix.ajax.request(basePath + '/jobInfo/deleteJobById.do',{'id':id},function(result){
             phenix.Msg.info(result.msg);
             grid.getStore().load({params:{start:0, limit:pageSize}});
        });
    })
}
phenix.updateJobState=function(id,state){
    	if(state=='0'){
    	   state=1;
    	}else{
    	   state=0;  
    	}
      phenix.ajax.request(basePath + '/jobInfo/updateJobState.do',{'id':id,'state':state},function(result){
             phenix.Msg.info(result.msg);
             grid.getStore().load({params:{start:0, limit:pageSize}});
        });
}
/**
 * 保存
 */
save = function() {
	if(!Ext.getCmp(formId).getForm().isValid()){
		phenix.Msg.warn('请正确填写');
		return;
	}
	var jobInfo= Ext.getCmp(formId).getForm().getValues();
	phenix.ajax.request(basePath + '/jobInfo/saveOrUpdateJob.do',jobInfo,function(result){
		    phenix.Msg.info(result.msg);
            Ext.getCmp(formWinId).close();
            grid.getStore().load({params:{start:0, limit:pageSize}});
	});
}



/**
 * 新增
 */
function addJobInfo(){
    var formWin = createFormWin();
    formWin.setTitle('新增任务');
    formWin.show();
}
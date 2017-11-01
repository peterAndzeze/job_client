<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%-- <%@ page import="com.creditease.core.framework.util.PropertiesUtil"%> --%>
<%-- <%@ page import="com.creditease.core.security.SpringSecurityUtils" %> --%>
<%-- <%@ page import="com.creditease.app.common.PhenixConst" %> --%>
<script type="text/javascript">

	//以下链接menu已改成console,因不知道哪里用到这个URL,所以先不改确认下有木有地方用
	var menuUrl = '${ctxPath}/component/menu/Menu/findMenuByType.action';
	var ctxPath = '${ctxPath}';
	
	var mainTabPanelId = 'mainTabPanel';
	var desktoptabDivId = 'desktoptabDiv';
	var ownerValue = '0';
	
	 
	var jobGroupsUrl = ctxPath+'/jobGroup/jobGroupModels.do';
	
	var jobsUrl = ctxPath+'/jobInfo/queryJobDictionary.do';
	
	var systemDateStateUrl = ctxPath+'/dictionary/list.do?parentId=3';
	var executorDSUrl = ctxPath+'/dictionary/list.do?parentId=6';
	var executorStateDSUrl = ctxPath+'/dictionary/list.do?parentId=11';
	var executorRouteStrategyDSUrl = ctxPath+'/dictionary/list.do?parentId=14';
	var executorBlockStrategyDSDSUrl = ctxPath+'/dictionary/list.do?parentId=25';
	
	var id = 'id';
	/*----------------------------------- 公共相关   ----------------------------------------*/
	//执行器
	var jobGroupsDs = new Ext.data.JsonStore({
		url:jobGroupsUrl, id:id, fields:['id','appName'], autoLoad:true
	});
	
	//执行任务
    var jobInfoDs = new Ext.data.JsonStore({
        url:jobsUrl, id:id, fields:['id','jobDesc'], autoLoad:true
    });
	
  //系统数据状态
    var systemDateState = new Ext.data.JsonStore({
        url:systemDateStateUrl, id:id, fields:['value','display'], autoLoad:true
    });
	//运行模式
	var executorDS= new Ext.data.JsonStore({
        url:executorDSUrl, id:id, fields:['value','display'], autoLoad:true
    });
	
	//运行状态
    var executorStateDS= new Ext.data.JsonStore({
        url:executorStateDSUrl, id:id, fields:['value','display'], autoLoad:true
    });
	
    var executorRouteStrategyDS= new Ext.data.JsonStore({
        url:executorRouteStrategyDSUrl, id:id, fields:['value','display'], autoLoad:true
    });
    
    
    var executorBlockStrategyDS= new Ext.data.JsonStore({
        url:executorBlockStrategyDSDSUrl, id:id, fields:['value','display'], autoLoad:true
    });
	
</script>



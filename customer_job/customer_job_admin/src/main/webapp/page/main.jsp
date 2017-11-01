<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title>job自动化</title>
<%@ include file="/common/common.jsp"%>
</head>

<body onbeforeunload="return '请注意，点击“确定”将退出本系统！';" onunload="/*doLogout();*/">
<!-- 顶菜单 -->
<div id="toolbar"></div>

<!-- 左菜单 -->
<div id="myMenu" class="left_menu"></div>

<!-- 工作区 -->
<div id="mainTab"></div>

<!-- 状态栏 -->
<div id="taskBar" style="width:100%;"></div>
<script type="text/javascript">

/*============================================== 配置  ==============================================*/
<jsp:include page="./config.jsp"/>
/*============================================== 方法  ==============================================*/
<jsp:include page="./function.jsp"/>
var queryDate = "";
var sysID="";
Ext.onReady(function(){
    var mainTabPanel = new Ext.TabPanel({
        id:'mainTabPanel',
        region:'center',
        deferredRender:true,
        enableTabScroll:true,
        resizeTabs:false,
        tabWidth:120,
        activeTab:0,
        defaults: {autoScroll:true},
        plugins: new Ext.ux.TabCloseMenu(),
        items:[{
            title: '首页',
            tabTip:'<b>首页</b>',
            xtype:'portal',
            style:'font-weight:bold',
            region:'center',
            margins:'35 5 5 0'
         }]
		});

		function getWarnByCatalogId(catalogId) {
			//增加loadMsg 会把菜单遮住
			//Ext.getCmp('catalog'+catalogId).getEl().mask(loadMsg);
			Ext.getCmp('catalog' + catalogId).getEl().mask();
		}

		/*====================== 主面板 ========================*/
		var clock = new Ext.Toolbar.TextItem('');//右下角时钟
		var week = Date.dayNames[new Date().format('w')];
		var themeName = '${sessionScope.USER_CONFIG_THEME}';
		if ('' == themeName)
			themeName = 'default';
        
		var viewport = new Ext.Viewport({
			layout : 'border',
			items : [ new Ext.Panel({
				region : 'north',
				frame : false,
				border : true,
				height : 28,
				listeners:{render:function(obj){
				   loadMenus(obj);
				}},bbar:[]
			}), mainTabPanel ]
		});
	});
	//关闭主界面的遮罩
	if(null!=IndexMask){
	    IndexMask.hide();
	}
	if(userName=="oldoper"){
	    phenix.Msg.warn( '当前登录用户没有操作权限,请重新登录', function(){
	        doLogout();
	    });
	}
	
	function openForWarn(node, url, name, flag) {
		var tabId = 'tab-' + node;
		var tabs = Ext.getCmp(mainTabPanelId);
		var tab = tabs.getItem(tabId);
		if (undefined != tab) {
			phenix.util.closeFromMainTab(tabId);
		}
		if(undefined!=tab){
	        phenix.util.closeFromMainTab(tabId);
	    }
	    phenix.util.openTab(node,url,'','mainTabPanel',name);
	}
</script>
</body>
</html>
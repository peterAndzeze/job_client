<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String ctxPath = request.getContextPath();
	pageContext.setAttribute("ctxPath",ctxPath);
%>
<!-- 页面结构 -->
<div id='index_${uuid}' style="width:100%;height:100%"></div>
<div id='tree_${uuid}'></div>
<div id='content_${uuid}'></div>
<!-- JS脚本 -->
<script type="text/javascript">
comp_menu = function(){

/*============================================== 配置  ==============================================*/
	<jsp:include page="./_configs.jsp"/>
/*============================================= 对话框   =============================================*/
	<jsp:include page="./_dialogs.jsp"/>	
/*============================================== 方法  ==============================================*/
	<jsp:include page="./_functions.jsp"/>
/*============================================= 主界面   =============================================*/

	var ds = new Ext.data.JsonStore({
		url: listUrl,
		baseParams:{node:rootId},
		id: 'id',
		root:'records',
		fields:['id', 'menuName', 'state', 'display', 'path']
	});
	/* var buttons = [
	      			{xtype:'tbseparator'},
		       		phenix.menu.createBtn('刷新缓存','x-tbar-loading',refresh)
	      		]; */
	//var topBar = createBar(buttons);
	ds.load();//默认显示部门根下的用户
	var sm = new Ext.grid.CheckboxSelectionModel({/*handleMouseDown: Ext.emptyFn;*/});
	var cm = new Ext.grid.ColumnModel([
		//sm,
		phenix.grid.createRowNumbererNoPage(),
		//phenix.grid.createIconCol(),
		{
			header: '菜单名称',
			dataIndex: 'menuName'
		},{
			header: "全路径",
			dataIndex: "path"
		},{
			header: "描述",
			dataIndex: "display"
		},{
			header: "状态",
			dataIndex: "state"
		}
	]);
/*============================== 主界面 ==========================================*/
	//子字典列表
	var grid = new Ext.grid.GridPanel({
		cm: cm,
		sm: sm,
		ds: ds,
		//tbar:topBar,
//		enableDragDrop: true,
//		enableDrop: true,
//		ddGroup: 'sortGridDD',
		border: false,
		height: Ext.get(tabDivId).getHeight(),
		width: Ext.get(tabDivId).getWidth(),
		//title: baseName+'列表',
		autoScroll:true, 
		stripeRows:true,
        loadMask:{msg:loadMsg},
		//autoExpandColumn:'dictKey',
		viewConfig: {
			forceFit: true
		}
	});
    
	//字典树
	var dictTree = new Ext.tree.TreePanel({
        el:'tree_${uuid}',
		//enableDD:true,//数据字典拖拽改变父节点会有问题，因为还需要改变所有子节点的全路径
		//ddGroup: 'changeParentDD',
		//dropConfig: {appendOnly:true},
        useArrows:false,//为true时, lines属性无效
        autoScroll:true,
		//autoHeight:true,
		//height:1000,
        animate:true,
        containerScroll: true,
		border:false,
		lines:true,
        // auto create TreeLoader
		loader: new Ext.tree.TreeLoader({
			dataUrl: treeUrl,
			baseParams: {id:rootId},
			listeners:{'beforeload':function(loader,node){
			      this.baseParams.id=node.id
			}}
		}),
		rootVisible:true,
        root: new Ext.tree.AsyncTreeNode({
            //nodeType: 'async',
            text: root.text,
            draggable:false,
            id:rootId
        }),
		dropConfig: {
			ddGroup: 'changeParentDD',// 从Grid到Tree。如果是Tree内部节点拖动，使用'TreeDD' 
			dropAllowed: true,
			
			notifyDrop: function(source, e, data){
				var pNode = this.lastOverNode.node;
				var node = data.node;
				if(pNode.id!=node.id){//防止移到自己下
					var params = {'pid':pNode.id, id:node.id};
					phenix.ajax.request(changePositionUrl,params,function(result){
						//phenix.Msg.info(result.msg);
						dictTree.getNodeById(node.id).remove();
						dictTree.getNodeById(pNode.id).appendChild(node);
						if(pNode.isLeaf()) phenix.tree.refreshNode(pNode);
						ds.reload();
					});
				}
				return true;
			},
			onNodeOver: function(n, dd, e, data){
				currentNode = n;
				return "x-tree-drop-ok-append";
			}
		}
    });
	//定义右键菜单
	var dictTreeCtxMenu = new Ext.menu.Menu({
		id :'dictTreeCtxMenu',
		items : [
				phenix.menu.createBtn('新建字典（项）','badd',add),
				phenix.menu.createBtn('修改字典（项）','bpencil',edit),
				phenix.menu.createBtn('删除字典（项）','bdelete',del),
				phenix.menu.createBtn('刷新字典（项）','brefresh',refreshNode),
				phenix.menu.createBtn('复制字典（项）','bcopy',copy),
				phenix.menu.createBtn('粘贴字典（项）','bpaste',paste)
				,phenix.menu.createBtn('<span style="color:red;font-weight:bold">级联删除字典</span>','bfolder_delete',dels)
		]
	});
	
	//增加右键点击事件
	dictTree.on('contextmenu',function(node,event){//声明菜单类型
		event.preventDefault();//这行是必须的
		currentNode=node;
		node.select();
		dictTreeCtxMenu.showAt(event.getXY());//取得鼠标点击坐标，展示菜单
	});
	
	/* 
	 * 点击树节点时触发
	 * 显示该节点的直接下的子节点列表
	 */
	dictTree.on('beforeclick',function(node, e){
		ds.baseParams = {id:node.id};
		ds.load();
	   var path = phenix.tree.getTextPath(node,rootId);
		Ext.getCmp('content_${uuid}').setTitle(path+' 的子字典列表');
		currentNode=node;
	   node.select();
	});
	
	var panel = new Ext.Panel({
		renderTo:'index_${uuid}',
		height: Ext.get(tabDivId).getHeight(),
		width: Ext.get(tabDivId).getWidth(),
		border:false,
		//title:'字典管理',
		layout:'border',
   		bodyBorder: true,
		defaults: {
		    collapsible: true,		//定义字典树列表是否可以隐藏
	        split: true,
			animFloat: false,
			autoHide: false,
			useSplitTips: true,
			bodyStyle: 'padding:0px'
		},
		items: [
		{
		    title: '总体结构',
		    region:'west',
			border:false,
		    margins: '0 0 0 0',
		    cmargins: '5 5 0 0',
		    width: 300,
		    minSize: 200,
		    maxSize: 400,
			layout:'fit',
			items: dictTree
		},{
		    title: '当前显示的是：第一层字典（即root下的字典）。（<span style="color:red">提示</span>：请点击左侧字典，可显示其子字典）',
			id:'content_${uuid}',
		    collapsible: false,
			border:false,
			width: 400,
		    region:'center',
		    margins: '0 0 0 0',
			layout:'fit',
			items:grid
		}]
	});
	
	var TreeSorter = phenix.tree.sortBySortIdxAsc(dictTree);
	/*========================= 执行 ===============================*/
	 // render the tree
    dictTree.render();
	currentNode = dictTree.getRootNode();
    dictTree.getRootNode().expand();
    
    //尺寸自适应
	Ext.EventManager.onWindowResize(function(){
		var newWidth = document.body.clientWidth-widthFix;
		var newHeight = document.body.clientHeight-heightFix;
		panel.setWidth(newWidth); 
		panel.setHeight(newHeight);
		//Ext.getCmp(topBarId).setWidth(newWidth);
		//pageBar.setWidth(newWidth);
		//rtn.getView().refresh(true);
	});
}

comp_menu();
</script>
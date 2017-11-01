<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	String ctxPath = request.getContextPath();
	pageContext.setAttribute("ctxPath",ctxPath);
%>

/*------------------无须改动----------------------*/
	var baseName = '菜单信息';
	var baseIcon = 'bbook';
	var tabDivId = 'index_${uuid}';
	var formId = Ext.id();
	var formWinId = Ext.id();
	var gridId = Ext.id();
	var topBarId = Ext.id();
	
	var listUrl="${ctxPath}/menu/getPages.do";
	var treeUrl="${ctxPath}/menu/menuTree.do";
	var menuInfoUrl="${ctxPath}/menu/menuInfo.do";
	var updateMenuUrl="${ctxPath}/menu/updateMenu.do";
	var delUrl="${ctxPath}/menu/deleteMenu.do";
	
	
	var pageSize =20;
	
	var rootId = 10001;
	var root = {id: rootId, text: '菜单结构'}
	var currentNode = root;
	var currentOperation = null;
	
/*-----------------------------------------------*/
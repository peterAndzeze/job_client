<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

/*============================== 参数 ==========================================*/
	var basePath = '${ctxPath}';
	var baseName = '详细信息';
	var baseIcon = 'bnote';
	
var tabDivId = 'index_${uuid}';
var formId = Ext.id();
var formWinId = Ext.id();
var gridId = Ext.id();

var pageSize = 10;

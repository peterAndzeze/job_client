<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
/*============================== 参数
==========================================*/ var basePath =
'${ctxPath}'; 
var baseName = '详细信息'; 
var baseIcon = 'bnote'; 
var tabDivId = 'index_${uuid}';
var logGrid=Ext.id();
var formId = Ext.id();
var formWinId =Ext.id(); 
var gridId = Ext.id(); 
var pageSize = 1; 
var executorDS=new Ext.data.JsonStore({
     fields :['id','displayName'],
     data :[
        {id:'0',displayName:'BEAN模式'},
        {id:'1',displayName:'java'}, {id:'2',displayName:'shell'}, {id:'3',displayName:'python'} 
          ] 
     }) 
//查询条件 
var searchParams= { 
     jobGroup:null,
     jobId:null,
     triggerTimeEnd:null,
     triggerTimeStart:null
    };

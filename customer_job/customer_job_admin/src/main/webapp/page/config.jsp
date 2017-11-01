<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
    String ctxPath = request.getContextPath();
    pageContext.setAttribute("ctxPath",ctxPath);
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ctxPath+"/";  
    pageContext.setAttribute("basePath",basePath);
%>

var phenix_ajax_isStoped = false;//标识服务器是否已停止
var phenix_ajax_loginDialogId = Ext.id();
var userName="";
//全屏幕遮罩
var IndexMask = phenix.Msg.waitMask();
//窗口的实际宽度
var iWidth = window.screen.width - 10;
//窗口的实际高度
var iHeight = window.screen.height - 65;
//重设窗口大小时宽度修正
var widthFix = 2;
//重设窗口大小时高度修正
var heightFix = 80;//原120
//桌面面板
var desktoptabDivId = 'desktopTab';


//   msg 相关配置
var msg = {};
msg.isUnderRequest = false;//标识是否正在进行请求
msg.recvTimer = null;//消息接收定时器
msg.refreshTimer = null;//在线状态刷新定时器
msg.localRefreshTimer = null;//本地在线状态刷新定时器
msg.recvTime = 10*1000;//接收消息间隔时间
msg.refreshTime = 5*1000;//状态刷新间隔时间
msg.localRefreshTime = 50*1000;//状态刷新间隔时间
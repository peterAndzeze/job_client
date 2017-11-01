<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <!---  <%@ taglib prefix="s" uri="/struts-tags" %> --%>
<%
	if(!session.isNew()) session.invalidate();//使已存在的session失效
	String ctxPath = request.getContextPath();
	pageContext.setAttribute("ctxPath",ctxPath);
	double random = Math.random();
	pageContext.setAttribute("random",random);
	
	//从cookie中得用户名
	Cookie[] cks = request.getCookies();
	String userName = "";
	if(null!=cks){
// 		for(Cookie ck : cks){
// 	if(com.creditease.core.CoreConst.USER_CONFIG_USERNAME.equals(ck.getName())){
// 		userName += ck.getValue();
// 	}
// 		}
	}
	
	pageContext.setAttribute("userName",userName);
%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>自定义job自动化</title>

<link rel="shortcut icon" href="${ctxPath}/favicon.ico" type="image/x-icon" />
<!-- <link rel="icon" type="image/png" href="${ctxPath}/favicon.png"> -->
<link rel="Bookmark" href="${ctxPath}/favicon.ico" />

<!-- 核心 -->
<link rel='stylesheet' type='text/css' href='${ctxPath}/script/extjs/resources/css/ext-all.css' />
<script type='text/javascript' src='${ctxPath}/script/extjs/adapter/ext/ext-base.js'></script> 
<script type='text/javascript' src='${ctxPath}/script/extjs/ext-all.js'></script> 
<script type='text/javascript' src='${ctxPath}/script/extjs/locale/ext-lang-zh_CN.js'></script> 
<script type='text/javascript' src='${ctxPath}/script/extjs/lib/LocaleSort.js'></script> 


<!-- 第三方扩展 -->
<link rel='stylesheet' type='text/css' href='${ctxPath}/style/ext-font.css' />
<!-- IE6 PNG透明补丁 -->
<!--[if lt IE 7]>
	<script type="text/javascript" src="${ctxPath}/pub/lib/ie6pngfix/unitpngfix.js"></script>
<![endif]-->

<!-- 自定义扩展 -->
<link rel='stylesheet' type='text/css' href='${ctxPath}/style/default.resource.css' />
<script type="text/javascript" src="${ctxPath}/script/phenix.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/text.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/ajax.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/msg.js"></script>
<script type="text/javascript" src="${ctxPath}/script/phenix/util.js"></script>

<%-- <!-- 组件扩展 -->
<script type="text/javascript" src="${ctxPath}/pub/js/comp/comp.js"></script> --%>

<!-- 加密解密 -->
<script type="text/javascript" src="${ctxPath}/script/lib/crydecry/md5-min.js"></script>

<script language="JavaScript">
	Ext.BLANK_IMAGE_URL = '${ctxPath}/script/extjs/resources/images/default/s.gif';
	//Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	Ext.QuickTips.init();
	//Ext.form.Field.prototype.msgTarget = 'side';
</script>
<!--link rel="stylesheet" id="theme" type="text/css" href="${ctxPath}/pub/lib/Ext2/resources/css/xtheme-purple.css-->
</head>
<body  style="height:100%;width:100%;background-color:#93b0f0;">
<%-- <img id="codePic" src="${ctxPath}/mycode.jpg?id=${random}" style="display:none;"/> --%>
<div style="height:100%;width:100%;text-align:center;">

<div id="login" style="height:100%;width:100%;text-align:center;"> </div>
<%-- <img id="login_logo" src="${ctxPath}/pub/images/login_bg_dadao.png"/> --%>
<script type="text/javascript">
if (window.Event)
document.captureEvents(Event.MOUSEUP);
function nocontextmenu(){
event.cancelBubble = true
event.returnValue = false;
return false;
}
function norightclick(e){
if (window.Event){
   if (e.which == 2 || e.which == 3)
   return false;
}
else
   if (event.button == 2 || event.button == 3){
    event.cancelBubble = true
    event.returnValue = false;
    return false;
   }
}
document.oncontextmenu = nocontextmenu; // for IE5+
document.onmousedown = norightclick; // for all others

/*=======================================*/
//重新获取验证码
function getCheckCode(){
    var mycode = Ext.get('mycode');
    var url = '${ctxPath}/mycode.jpg?id='+Math.random();
    mycode.dom.src = null;
    mycode.dom.src = url;
}
//页面调整监听方法
comp.resize = function(){
    //在comp.login方法里重新设置方法体
}
comp.loginLogoWidth = 800;//登录标识（图片）的宽度
//定义界面
comp.login = function(){
    var loginForm = new Ext.form.FormPanel({
        id:'loginForm',
        //baseCls: 'x-plain',
        //bodyStyle: 'background:url(${ctxPath}/pub/images/login_form_b_extjs.png) no-repeat top;',
        frame:true,
        border:true,
        buttonAlign:'center',
        labelWidth: 60,
        labelAlign: 'right',
        //labelSeparator: '　',
        title:'<b>用户登录</b>',
        iconCls:'bkey',
        //url:'${ctxPath}/j_spring_security_check',
        url:'${ctxPath}/login.do',
        defaultType: 'textfield',
        items: [
            //{xtype:'label',html:'<div style="height:240px;"></div>'},
            {
                xtype:'panel',
                layout:'column',
                frame: false,
                
                border:false,
                //height:160,
                //plain:true,
                items:[
                    {xtype:'panel',columnWidth:0.05,border:false,items:[{xtype:'label',html:'&nbsp;'}]},
                    {xtype:'panel',columnWidth:0.50,layout:'form',defaultType:'textfield',
                        labelSeparator: ' ',border:false,items:[
                        {
                            id: 'userName',
                            fieldLabel: '用户名:',
                            //height:20,
                            name: 'j_username',
                            value: '${userName}',
                            allowBlank:false,
                            blankText:'请输入用户名',
                            vtype:'alphanum',
                            anchor:'90%'
                        },{
                            id: 'userPassword',
                            fieldLabel: '密　码:',
                            //height:20,
                            name: 'userPassword',
                            inputType: 'password',
                            allowBlank:false,
                            blankText:'请输入密码',
                            anchor: '90%',
                            listeners:{
                                focus:function(field){
                                    field.selectText();
                                },
                                specialkey:function(obj,evt){
                                    if(evt.getKey()==evt.ENTER){
                                        login();
                                    }
                                }
                            }
                        },{
                            id: 'cryptPassword',
                            xtype: 'hidden',
                            name: 'j_password'
                        }
                    ]}
                ]
            }
        ],
        buttons: [{
            text: '登录',
            tooltip:'登录',
            handler:login
        },{
            text:'取消',
            handler:reset
        }
        ]
    });
    //登录窗口
    var loginWinY = '298';
    var loginWin = new Ext.Window({
        //title: '用户登录',
        width: 520,
        height:130,
        //minWidth: 300,
        //minHeight: 150,
        y: loginWinY,
        layout: 'fit',
        plain:false,
        border:false,
        frame:false,
        bodyStyle:'padding:0px;',
        items: loginForm,
        modal: false,
        shadow: true,
        closable:false,
        resizable:false
        //,iconCls:'bkey'
    });
    loginWin.show();
    //自调整位置
    comp.resize = function(){
        var size = loginWin.getSize();
        var param = 0;
        if(Ext.isIE && !window.opener){//IE下，非提出窗口会出现一个像素的偏移
            param = 1;
        }
        loginWin.setPosition((document.body.clientWidth-size.width)/2+param, loginWinY);
    }
    
    
    function login(){
    	if(!loginForm.getForm().isValid()){
    		phenix.Msg.error("温馨提示，请正确填写登陆信息");
    	}
    	phenix.ajax.request("${ctxPath}/toLogin.do",{'userName':Ext.getCmp('userName').getValue(),'userPassword':Ext.getCmp('userPassword').getValue()},function(result){
    	    if(result.success){
    	    	 window.location="${ctxPath}/"+result.data+"?userName="+Ext.getCmp('userName').getValue();
    	    }else{
    		  phenix.Msg.info(result.msg);
    	    }
    	});
    }
    function reset(){
        loginForm.form.reset();
        //loginWin.close();
    }
}
Ext.onReady(function() {
    comp.login();
    comp.resize();
});

</script>
</div>
<%-- <jsp:include page="/pub/jsp/footer.jsp"></jsp:include> --%>
